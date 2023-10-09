package com.mjc.school.repository.implementation;

import com.mjc.school.common.utils.JsonUtils;
import com.mjc.school.repository.factory.DataManagerFactory;
import com.mjc.school.repository.interfaces.DataManagerInterface;
import com.mjc.school.repository.interfaces.DataSourceInterface;
import com.mjc.school.repository.interfaces.ModelInterface;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class DataSourceFileBased implements DataSourceInterface {
    DataManagerInterface dataManagerInterface = DataManagerFactory
            .getInstance()
            .getListDataManager();

    private static List<ModelInterface> newsTable;
    private static List<ModelInterface> authorTable;


    public DataSourceFileBased(){
        try {
            if(Objects.isNull(newsTable)) {
                newsTable = dataManagerInterface.load(NewsModel.class);
                authorTable = dataManagerInterface.load(AuthorModel.class);
            }

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

    private List<ModelInterface> setFromTable(Class<? extends ModelInterface> tableType){
        return (AuthorModel.class.isAssignableFrom(tableType))? authorTable : newsTable;
    }

    private void setModelTableValue(Class<? extends ModelInterface> clazz, List<ModelInterface> modelTable){
        if(AuthorModel.class.isAssignableFrom(clazz)){ authorTable = modelTable; }
        else { newsTable = modelTable; }
    }


    @Override
    public List<ModelInterface> executeSelectQuery(Class<? extends ModelInterface> clazz, Predicate<ModelInterface> predicate){
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

    public ModelInterface executeInsertQuery(Class<? extends ModelInterface> clazz, ModelInterface model){
        List<ModelInterface> modelTable = setFromTable(clazz);
        model.generateId();
        if(model instanceof NewsModel newsModel){
            if(Objects.isNull(newsModel.getCreateDate())){
                newsModel.setCreateDate(LocalDateTime.now());
                newsModel.setLastUpdateDate(newsModel.getCreateDate());
            }
        }
        modelTable.add(model);
        setModelTableValue(clazz, modelTable);
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
                LocalDateTime createdDate = (clazz.isAssignableFrom(NewsModel.class))?
                    ((NewsModel) original).getCreateDate()
                    : null;

                if (predicate.test(original)) {
                     original = clazz.cast(JsonUtils.deserialize(JsonUtils.serialize(model), clazz));
                     original.setId(id);
                     if (clazz.isAssignableFrom(NewsModel.class)){
                         ((NewsModel) original).setCreateDate(createdDate);
                         ((NewsModel) original).setLastUpdateDate(LocalDateTime.now());
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
