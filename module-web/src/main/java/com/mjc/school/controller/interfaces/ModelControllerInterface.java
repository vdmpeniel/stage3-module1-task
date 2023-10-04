package com.mjc.school.controller.interfaces;

public interface ModelControllerInterface<T, T1> {
    T1 create(T requestDto);

    T1 readAll();

    T1 readById(T requestDto);

    T1 updateById(T requestDto);

    T1 deleteById(T requestDto);

    T1 buildErrorResponse(Exception e);
}
