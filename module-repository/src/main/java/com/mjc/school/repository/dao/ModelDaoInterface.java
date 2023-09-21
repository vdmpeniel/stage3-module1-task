package com.mjc.school.repository.dao;

import com.mjc.school.repository.model.ModelInterface;

import java.util.List;

public interface ModelDaoInterface {
    public void create(ModelInterface model)throws Exception;

    public List<ModelInterface> getAll() throws Exception;

    public ModelInterface findById(Long id) throws Exception;

    public void update(Long id, ModelInterface model) throws Exception;

    public boolean delete(Long id) throws Exception;
}
