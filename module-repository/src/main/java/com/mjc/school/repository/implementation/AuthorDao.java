package com.mjc.school.repository.implementation;

import com.mjc.school.repository.interfaces.ModelDaoInterface;
import com.mjc.school.repository.interfaces.DataSourceInterface;
import com.mjc.school.repository.interfaces.ModelInterface;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AuthorDao implements ModelDaoInterface {

    private final DataSourceInterface dataSource = new DataSourceFileBased();
    public AuthorDao(){}

    @Override
    public ModelInterface create(ModelInterface authorModel)throws Exception{
        return dataSource.executeInsertQuery(AuthorModel.class, authorModel);
    }

    @Override
    public List<ModelInterface> readAll() throws Exception{
        return Objects.requireNonNull(dataSource.executeSelectQuery(AuthorModel.class, null)).stream()
            .map(model -> (AuthorModel) model)
            .collect(Collectors.toList());
    }

    @Override
    public ModelInterface readById(Long id) throws Exception{
        Predicate<ModelInterface> AuthorById = model -> id.equals(model.getId());
        List<ModelInterface> resultSet = dataSource.executeSelectQuery(AuthorModel.class, AuthorById);
        return (Objects.nonNull(resultSet) && !resultSet.isEmpty())? (AuthorModel) resultSet.get(0) : null;
    }

    @Override
    public ModelInterface update(ModelInterface authorModel) throws Exception{
        Predicate<ModelInterface> authorById = model -> authorModel.getId().equals(model.getId());
        return dataSource.executeUpdateQuery(AuthorModel.class, authorModel, authorById);
    }

    @Override
    public Boolean delete(Long id) throws Exception{
        Predicate<ModelInterface> authorById = model -> id.equals(model.getId());
        dataSource.executeDeleteQuery(AuthorModel.class, authorById);
        return true;
    }
}
