package com.mjc.school.repository.implementation;

import com.mjc.school.repository.interfaces.ModelDaoInterface;
import com.mjc.school.repository.interfaces.DataSourceInterface;
import com.mjc.school.repository.interfaces.ModelInterface;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NewsDao implements ModelDaoInterface {
    private final DataSourceInterface dataSource = new DataSourceFileBased();
    public NewsDao(){}

    @Override
    public ModelInterface create(ModelInterface newsModel)throws Exception{
        return dataSource.executeInsertQuery(NewsModel.class, newsModel);
    }

    public List<ModelInterface> readAll() throws Exception{
        return Objects.requireNonNull(dataSource.executeSelectQuery(NewsModel.class, null)).stream()
            .map(model -> (NewsModel) model)
            .collect(Collectors.toList());
    }

    public ModelInterface readById(Long id) throws Exception{
        Predicate<ModelInterface> newsById = model -> id.equals(model.getId());
        List<ModelInterface> resultSet = dataSource.executeSelectQuery(NewsModel.class, newsById);
        return (Objects.nonNull(resultSet) && !resultSet.isEmpty())? (NewsModel) resultSet.get(0) : null;
    }

    @Override
    public ModelInterface update(ModelInterface newsModel) throws Exception {
        Predicate<ModelInterface> newsById = model -> newsModel.getId().equals(model.getId());
        return dataSource.executeUpdateQuery(NewsModel.class, newsModel, newsById);
    }

    public Boolean delete(Long id) throws Exception{
        Predicate<ModelInterface> newsById = model -> id.equals(model.getId());
        dataSource.executeDeleteQuery(NewsModel.class, newsById);
        return true;
    }
}
