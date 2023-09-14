package com.mjc.school.repository.dao;

import com.mjc.school.repository.datasource.FileDataSource;
import com.mjc.school.repository.model.ModelInterface;
import com.mjc.school.repository.model.News;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class NewsDao {
    private FileDataSource dataSource;
    public NewsDao() throws Exception{
        dataSource = FileDataSource.getInstance();
    }

    public List<News> getAll() throws Exception{
        return Objects.requireNonNull(dataSource.executeSelectQuery(News.class, null)).stream()
            .map(model -> (News) model)
            .collect(Collectors.toList());
    }

    public News findById(Long id) throws Exception{
        Predicate<ModelInterface> newsById = model -> model.getId().equals(5L);
        List<ModelInterface> resultSet = dataSource.executeSelectQuery(News.class, newsById);
        return (Objects.nonNull(resultSet) && !resultSet.isEmpty())? (News) resultSet : null;

    }

    public static void main(String[] args) {
        try {
            NewsDao newsDao = new NewsDao();
            System.out.println(newsDao.getAll());

            News news = newsDao.findById(1L);
            String toString = Objects.nonNull(news) ? news.toString() : "null";
            System.out.println(toString);

        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }


}
