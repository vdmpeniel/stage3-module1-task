package com.mjc.school.repository.implementation;

import com.mjc.school.common.implementation.utils.JsonUtils;
import com.mjc.school.common.implementation.utils.PropertyLoader;
import com.mjc.school.repository.interfaces.DataManager;
import com.mjc.school.repository.interfaces.DataSourceInterface;
import com.mjc.school.repository.interfaces.ModelInterface;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class FileDataSource implements DataSourceInterface {
    private volatile static FileDataSource instance;
    DataManager dataManager = new ListDataFileManager();

    String authorFilePath;
    String newsFilePath;



    private FileDataSource(){
        try {
            PropertyLoader propertyLoader = PropertyLoader.getInstance();
            newsTable = dataManager.load(News.class);
            authorTable = dataManager.load(Author.class);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public static FileDataSource getInstance() throws Exception{
        synchronized (FileDataSource.class){
            if (Objects.isNull(instance)){
                instance = new FileDataSource();
            }
            return instance;
        }
    }

    private List<ModelInterface> newsTable;
    private List<ModelInterface> authorTable;


    private String getModelFilePath(Class<? extends ModelInterface> tableType) throws IOException {
        return ((Author.class.isAssignableFrom(tableType))? authorFilePath : newsFilePath);
    }


    private List<ModelInterface> setFromTable(Class<? extends ModelInterface> tableType){
        return (Author.class.isAssignableFrom(tableType))? authorTable : newsTable;
    }

    private void setModelTableValue(Class<? extends ModelInterface> clazz, List<ModelInterface> modelTable){
        if(Author.class.isAssignableFrom(clazz)){ authorTable = modelTable; }
        else { newsTable = modelTable; }
    }


    @Override
    public List<ModelInterface> executeSelectQuery(Class<? extends ModelInterface> clazz, Predicate<ModelInterface> predicate) throws Exception{
        List<ModelInterface> modelTable = setFromTable(clazz);
        return (Objects.isNull(predicate)) ?
            new ArrayList<>(modelTable)
            : modelTable.stream()
            .filter(predicate)
            .toList();
    }

    @Override
    public void executeDeleteQuery(Class<? extends ModelInterface> clazz, Predicate<ModelInterface> predicate) throws Exception {
        List<ModelInterface> modelTable = setFromTable(clazz);

        if (Objects.isNull(predicate)) {
            throw new Exception("Filter must not be null.");
        }

        List<ModelInterface> resultTable = modelTable.stream().filter(predicate.negate()).collect(Collectors.toList());
        if (resultTable.size() == modelTable.size()) {
            throw new Exception("No row was affected.");
        }
        setModelTableValue(clazz, resultTable);
    }

    public void executeInsertQuery(Class<? extends ModelInterface> clazz, ModelInterface model) throws Exception{
        List<ModelInterface> modelTable = setFromTable(clazz);
        model.generateId();
        if(model instanceof News){
            News news = ((News) model);
            if(Objects.isNull(news.getCreateDate())){
                news.setCreateDate(LocalDateTime.now());
                news.setLastUpdateDate(news.getCreateDate());
            }
        }
        modelTable.add((ModelInterface) model);
    }

    public void executeUpdateQuery(Class<? extends ModelInterface> clazz, ModelInterface model, Predicate<ModelInterface> predicate) throws Exception{
        List<ModelInterface> modelTable = setFromTable(clazz);

        if (modelTable.stream().filter(predicate).toList().size() == 0) {
            throw new Exception("Filter returned no results");
        }

        modelTable = modelTable.stream().map(original -> {
            try {
                Long id = original.getId();
                LocalDateTime createdDate = (clazz.isAssignableFrom(News.class))?
                    ((News) original).getCreateDate()
                    : null;

                if (predicate.test(original)) {
                     original = clazz.cast(JsonUtils.deserialize(JsonUtils.serialize(model), clazz));
                     original.setId(id);
                     if (clazz.isAssignableFrom(News.class)){
                         ((News) original).setCreateDate(createdDate);
                         ((News) original).setLastUpdateDate(LocalDateTime.now());
                     }
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return original;
        }).toList();
        setModelTableValue(clazz, modelTable);
    }


}
