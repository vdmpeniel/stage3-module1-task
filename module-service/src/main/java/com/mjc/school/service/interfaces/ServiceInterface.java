package com.mjc.school.service.interfaces;

import java.util.List;

public interface ServiceInterface<T> {
    T create(T modelDto) throws Exception;

    List<T> readAll() throws Exception;

    T readById(Long id) throws Exception;

    T updateById(T modelDto) throws Exception;

    Boolean deleteById(Long id) throws Exception;
}
