package com.mjc.school.repository.dao;

import com.mjc.school.repository.datasource.FileDataSource;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.modelinterface.ModelInterface;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AuthorDao {
    private final FileDataSource dataSource = FileDataSource.getInstance();
    public AuthorDao() throws Exception{}

    public void create(Author author)throws Exception{
        dataSource.executeInsertQuery(Author.class, author);
    }

    public List<Author> getAll() throws Exception{
        return Objects.requireNonNull(dataSource.executeSelectQuery(Author.class, null)).stream()
            .map(model -> (Author) model)
            .collect(Collectors.toList());
    }

    public Author findById(Long id) throws Exception{
        Predicate<ModelInterface> AuthorById = model -> model.getId().equals(id);
        List<ModelInterface> resultSet = dataSource.executeSelectQuery(Author.class, AuthorById);
        return (Objects.nonNull(resultSet) && !resultSet.isEmpty())? (Author) resultSet : null;
    }

    public void update(Long id, Author author) throws Exception{
        Predicate<ModelInterface> authorById = model -> model.getId().equals(author.getId());
        dataSource.executeUpdateQuery(Author.class, author, authorById);
    }

    public boolean deleteById(Long id) throws Exception{
        Predicate<ModelInterface> authorById = model -> model.getId().equals(id);
        return dataSource.executeDeleteQuery(Author.class, authorById);
    }
}
