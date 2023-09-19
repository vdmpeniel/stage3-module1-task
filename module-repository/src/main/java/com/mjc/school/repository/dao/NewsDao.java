package com.mjc.school.repository.dao;

import com.mjc.school.repository.datasource.FileDataSource;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.ModelInterface;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NewsDao {
    private final FileDataSource dataSource = FileDataSource.getInstance();
    public NewsDao() throws Exception{}

    public void create(News news)throws Exception{
        dataSource.executeInsertQuery(News.class, news);
    }

    public List<News> getAll() throws Exception{
        return Objects.requireNonNull(dataSource.executeSelectQuery(News.class, null)).stream()
            .map(model -> (News) model)
            .collect(Collectors.toList());
    }

    public News findById(Long id) throws Exception{
        Predicate<ModelInterface> newsById = model -> model.getId().equals(id);
        List<ModelInterface> resultSet = dataSource.executeSelectQuery(News.class, newsById);
        return (Objects.nonNull(resultSet) && !resultSet.isEmpty())? (News) resultSet.get(0) : new News();
    }

    public void update(Long id, News news) throws Exception{
        Predicate<ModelInterface> newsById = model -> model.getId().equals(id);
        dataSource.executeUpdateQuery(News.class, news, newsById);
    }

    public boolean deleteById(Long id) throws Exception{
        Predicate<ModelInterface> newsById = model -> model.getId().equals(id);
        dataSource.executeDeleteQuery(News.class, newsById);
        return true;
    }
}
