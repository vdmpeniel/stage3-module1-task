package com.mjc.school.service.interfaces;

import com.mjc.school.service.implementation.RequestDto;
import com.mjc.school.service.implementation.ResponseDto;

public interface ServiceInterface {
    public ResponseDto create(RequestDto requestDto);

    public ResponseDto getAll();

    public ResponseDto getById(RequestDto requestDto);

    public ResponseDto updateById(RequestDto requestDto);

    public ResponseDto removeById(RequestDto requestDto);

    public ResponseDto buildErrorResponse(Exception e);
}
