package com.mjc.school.repository.implementation;

import com.mjc.school.repository.interfaces.ModelDaoInterface;
import com.mjc.school.repository.interfaces.DataSourceInterface;
import com.mjc.school.repository.interfaces.ModelInterface;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NewsDao implements ModelDaoInterface {
    private final DataSourceInterface dataSource = FileDataSource.getInstance();
    public NewsDao() throws Exception{}

    @Override
    public void create(ModelInterface news)throws Exception{
        dataSource.executeInsertQuery(News.class, news);
    }

    public List<ModelInterface> getAll() throws Exception{
        return Objects.requireNonNull(dataSource.executeSelectQuery(News.class, null)).stream()
            .map(model -> (News) model)
            .collect(Collectors.toList());
    }

    public ModelInterface findById(Long id) throws Exception{
        Predicate<ModelInterface> newsById = model -> id.equals(model.getId());
        List<ModelInterface> resultSet = dataSource.executeSelectQuery(News.class, newsById);
        return (Objects.nonNull(resultSet) && !resultSet.isEmpty())? (News) resultSet.get(0) : null;
    }

    @Override
    public void update(Long id, ModelInterface news) throws Exception {
        Predicate<ModelInterface> newsById = model -> id.equals(model.getId());
        dataSource.executeUpdateQuery(News.class, news, newsById);
    }

    public boolean delete(Long id) throws Exception{
        Predicate<ModelInterface> newsById = model -> id.equals(model.getId());
        dataSource.executeDeleteQuery(News.class, newsById);
        return true;
    }
}
