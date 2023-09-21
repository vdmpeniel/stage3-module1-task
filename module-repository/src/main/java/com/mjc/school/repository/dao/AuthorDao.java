package com.mjc.school.repository.dao;

import com.mjc.school.repository.datasource.DataSourceInterface;
import com.mjc.school.repository.datasource.FileDataSource;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.ModelInterface;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AuthorDao implements ModelDaoInterface{

    private final DataSourceInterface dataSource = FileDataSource.getInstance();
    public AuthorDao() throws Exception{}

    @Override
    public void create(ModelInterface author)throws Exception{
        dataSource.executeInsertQuery(Author.class, author);
    }

    @Override
    public List<ModelInterface> getAll() throws Exception{
        return Objects.requireNonNull(dataSource.executeSelectQuery(Author.class, null)).stream()
            .map(model -> (Author) model)
            .collect(Collectors.toList());
    }

    @Override
    public Author findById(Long id) throws Exception{
        Predicate<ModelInterface> AuthorById = model -> model.getId().equals(id);
        List<ModelInterface> resultSet = dataSource.executeSelectQuery(Author.class, AuthorById);
        return (Objects.nonNull(resultSet) && !resultSet.isEmpty())? (Author) resultSet.get(0) : null;
    }

    @Override
    public void update(Long id, ModelInterface author) throws Exception{
        Predicate<ModelInterface> authorById = model -> model.getId().equals(author.getId());
        dataSource.executeUpdateQuery(Author.class, author, authorById);
    }

    @Override
    public boolean delete(Long id) throws Exception{
        Predicate<ModelInterface> authorById = model -> model.getId().equals(id);
        dataSource.executeDeleteQuery(Author.class, authorById);
        return true;
    }
}
