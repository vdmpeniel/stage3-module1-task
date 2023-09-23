package com.mjc.school.service;

import com.mjc.school.service.dto.RequestDto;
import com.mjc.school.service.dto.ResponseDto;

public interface ServiceInterface {
    public ResponseDto create(RequestDto requestDto);

    public ResponseDto getAll();

    public ResponseDto getById(RequestDto requestDto);

    public ResponseDto updateById(RequestDto requestDto);

    public ResponseDto removeById(RequestDto requestDto);

    public ResponseDto buildErrorResponse(Exception e);
}
