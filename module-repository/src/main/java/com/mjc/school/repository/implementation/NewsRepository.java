package com.mjc.school.repository.implementation;

import com.mjc.school.repository.interfaces.RepositoryInterface;
import com.mjc.school.repository.interfaces.DataSourceInterface;
import com.mjc.school.repository.interfaces.ModelInterface;
import com.mjc.school.repository.model.NewsModel;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NewsRepository implements RepositoryInterface<ModelInterface> {
    private final DataSourceInterface dataSource = new DataSourceFileBased();
    public NewsRepository(){}

    @Override
    public ModelInterface create(ModelInterface model)throws Exception{
        return dataSource.executeInsertQuery(NewsModel.class, model);
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
    public ModelInterface update(ModelInterface model) throws Exception {
        Predicate<ModelInterface> newsById = newsModel -> model.getId().equals(newsModel.getId());
        return dataSource.executeUpdateQuery(NewsModel.class, model, newsById);
    }

    public Boolean delete(Long id) throws Exception{
        Predicate<ModelInterface> newsById = model -> id.equals(model.getId());
        dataSource.executeDeleteQuery(NewsModel.class, newsById);
        return true;
    }
}
