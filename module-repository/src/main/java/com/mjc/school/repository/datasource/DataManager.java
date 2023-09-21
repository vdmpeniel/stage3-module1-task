package com.mjc.school.repository.datasource;

import com.mjc.school.repository.model.ModelInterface;

import java.util.List;

public interface DataManager {
    List<ModelInterface> load(Class<? extends ModelInterface> clazz) throws Exception;

    void persist(Class<? extends ModelInterface> clazz, List<ModelInterface> modelTable) throws Exception;
}
