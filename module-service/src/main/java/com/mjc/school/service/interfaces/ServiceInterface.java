package com.mjc.school.service.interfaces;

public interface ServiceInterface<T, T1> {
    T1 create(T requestDto);

    T1 getAll();

    T1 getById(T requestDto);

    T1 updateById(T requestDto);

    T1 removeById(T requestDto);

    T1 buildErrorResponse(Exception e);
}
