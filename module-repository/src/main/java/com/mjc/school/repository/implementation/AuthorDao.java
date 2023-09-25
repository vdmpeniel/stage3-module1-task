package com.mjc.school.repository.implementation;

import com.mjc.school.repository.interfaces.ModelDaoInterface;
import com.mjc.school.repository.interfaces.DataSourceInterface;
import com.mjc.school.repository.interfaces.ModelInterface;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class AuthorDao implements ModelDaoInterface {

    private final DataSourceInterface dataSource = DataSource.getInstance();
    public AuthorDao() throws Exception{}

    @Override
    public ModelInterface create(ModelInterface author)throws Exception{
        return dataSource.executeInsertQuery(Author.class, author);
    }

    @Override
    public List<ModelInterface> readAll() throws Exception{
        return Objects.requireNonNull(dataSource.executeSelectQuery(Author.class, null)).stream()
            .map(model -> (Author) model)
            .collect(Collectors.toList());
    }

    @Override
    public ModelInterface readById(Long id) throws Exception{
        Predicate<ModelInterface> AuthorById = model -> id.equals(model.getId());
        List<ModelInterface> resultSet = dataSource.executeSelectQuery(Author.class, AuthorById);
        return (Objects.nonNull(resultSet) && !resultSet.isEmpty())? (Author) resultSet.get(0) : null;
    }

    @Override
    public ModelInterface update(ModelInterface author) throws Exception{
        Predicate<ModelInterface> authorById = model -> author.getId().equals(model.getId());
        return dataSource.executeUpdateQuery(Author.class, author, authorById);
    }

    @Override
    public Boolean delete(Long id) throws Exception{
        Predicate<ModelInterface> authorById = model -> id.equals(model.getId());
        dataSource.executeDeleteQuery(Author.class, authorById);
        return true;
    }
}
