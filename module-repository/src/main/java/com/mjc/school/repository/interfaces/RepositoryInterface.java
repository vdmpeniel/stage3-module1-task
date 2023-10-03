package com.mjc.school.repository.interfaces;

import java.util.List;

public interface RepositoryInterface<T> {
    T create(T model)throws Exception;

    List<T> readAll() throws Exception;

    T readById(Long id) throws Exception;

    T update(T model) throws Exception;

    Boolean delete(Long id) throws Exception;
}
