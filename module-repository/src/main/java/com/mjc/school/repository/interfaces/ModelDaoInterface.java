package com.mjc.school.repository.interfaces;

import java.util.List;

public interface ModelDaoInterface {
    public ModelInterface create(ModelInterface model)throws Exception;

    public List<ModelInterface> readAll() throws Exception;

    public ModelInterface readById(Long id) throws Exception;

    public ModelInterface update(ModelInterface model) throws Exception;

    public Boolean delete(Long id) throws Exception;
}
