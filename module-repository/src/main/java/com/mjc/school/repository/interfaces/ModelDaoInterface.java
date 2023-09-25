package com.mjc.school.repository.interfaces;

import java.util.List;

public interface ModelDaoInterface {
    public ModelInterface create(ModelInterface model)throws Exception;

    public List<ModelInterface> readAll() throws Exception;

    public ModelInterface readById(Long id) throws Exception;

    public ModelInterface update(Long id, ModelInterface model) throws Exception;

    public boolean delete(Long id) throws Exception;
}
