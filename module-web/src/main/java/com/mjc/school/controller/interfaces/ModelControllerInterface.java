package com.mjc.school.controller.interfaces;

import java.util.List;

public interface ModelControllerInterface<T, T1> {
    T1 create(T requestDto) throws Exception;

    List<T1> readAll() throws Exception;

    T1 readById(Long id) throws Exception;

    T1 updateById(T requestDto) throws Exception;

    Boolean deleteById(Long id) throws Exception;
}
