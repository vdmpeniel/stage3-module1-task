package com.mjc.school.repository.datasource;

import com.mjc.school.repository.model.ModelInterface;

import java.util.List;
import java.util.function.Predicate;

public interface DataSourceInterface {

    public void persistModelData(Class<? extends ModelInterface> clazz, List<ModelInterface> modelTable) throws Exception;

    public List<ModelInterface> executeSelectQuery(Class<? extends ModelInterface> clazz, Predicate<ModelInterface> predicate) throws Exception;

    public void executeDeleteQuery(Class<? extends ModelInterface> clazz, Predicate<ModelInterface> predicate) throws Exception;

    public void executeInsertQuery(Class<? extends ModelInterface> clazz, ModelInterface model) throws Exception;

    public void executeUpdateQuery(Class<? extends ModelInterface> clazz, ModelInterface model, Predicate<ModelInterface> predicate) throws Exception;

}
