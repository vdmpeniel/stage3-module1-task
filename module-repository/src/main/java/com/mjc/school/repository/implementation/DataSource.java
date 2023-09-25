package com.mjc.school.repository.implementation;

import com.mjc.school.common.implementation.utils.JsonUtils;
import com.mjc.school.common.implementation.utils.PropertyLoader;
import com.mjc.school.repository.interfaces.DataManagerInterface;
import com.mjc.school.repository.interfaces.DataSourceInterface;
import com.mjc.school.repository.interfaces.ModelInterface;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class DataSource implements DataSourceInterface {
    private volatile static DataSource instance;
    DataManagerInterface dataManagerInterface = new ListDataManager();

    String authorFilePath;
    String newsFilePath;



    private DataSource(){
        try {
            PropertyLoader propertyLoader = PropertyLoader.getInstance();
            newsTable = dataManagerInterface.load(News.class);
            authorTable = dataManagerInterface.load(Author.class);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


    public static DataSource getInstance() throws Exception{
        synchronized (DataSource.class){
            if (Objects.isNull(instance)){
                instance = new DataSource();
            }
            return instance;
        }
    }

    private List<ModelInterface> newsTable;
    private List<ModelInterface> authorTable;


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

    public ModelInterface executeInsertQuery(Class<? extends ModelInterface> clazz, ModelInterface model) throws Exception{
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
        return model;
    }

    public ModelInterface executeUpdateQuery(Class<? extends ModelInterface> clazz, ModelInterface model, Predicate<ModelInterface> predicate) throws Exception{
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
        return model;
    }


}
