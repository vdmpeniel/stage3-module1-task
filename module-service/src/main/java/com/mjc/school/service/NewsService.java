package com.mjc.school.service;

import com.mjc.school.common.exceptions.IllegalFieldValueException;
import com.mjc.school.common.utils.PropertyLoader;
import com.mjc.school.common.utils.modelvalidatorutils.ModelValidatorUtils;
import com.mjc.school.repository.dao.ModelDaoInterface;
import com.mjc.school.repository.dao.NewsDao;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.*;
import com.mjc.school.service.mapper.NewsMapper;

import java.time.LocalDateTime;
import java.util.List;

public class NewsService implements ServiceInterface{
    PropertyLoader propertyLoader;
    ModelDaoInterface newsDao;

    public NewsService(){
        try {
            propertyLoader = PropertyLoader.getInstance();
            newsDao = new NewsDao();

        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }


    @Override
    public ResponseDto create(RequestDto requestDto){
        try {
            ModelValidatorUtils.runValidation(requestDto.getInputData());

            News news = NewsMapper.INSTANCE.newsDtoToNews((NewsDto) requestDto.getInputData());
            news.setCreateDate(LocalDateTime.now());
            news.setLastUpdateDate(news.getCreateDate());
            newsDao.create(news);

            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                    getById(
                        RequestDto.builder().lookupId(
                            news.getId().toString()
                        ).build()
                    ).getResultSet()
                )
                .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    @Override
    public ResponseDto getAll() {
        try{
            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                        newsDao.getAll()
                                .stream()
                                .map(model -> NewsMapper.INSTANCE.newsToNewsDto((News) model))
                                .map(model -> (ModelDtoInterface) model)
                                .toList())
                .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    @Override
    public ResponseDto getById(RequestDto requestDto) {
        try{
            ModelValidatorUtils.runValidation(requestDto);
            ResponseDto responseDto = ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                    List.of(NewsMapper.INSTANCE.newsToNewsDto(
                        (News) newsDao.findById(Long.parseLong(requestDto.getLookupId()))
                    ))
                )
                .build();
            return responseDto;

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    @Override
    public ResponseDto updateById(RequestDto requestDto) {
        try{
            ModelValidatorUtils.runValidation(requestDto);
            ModelValidatorUtils.runValidation(requestDto.getInputData());

            News news = NewsMapper.INSTANCE.newsDtoToNews((NewsDto) requestDto.getInputData());
            news.setLastUpdateDate(LocalDateTime.now());
            newsDao.update(Long.parseLong(requestDto.getLookupId()), news);
            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                    List.of(NewsMapper.INSTANCE.newsToNewsDto((News) newsDao.findById(Long.parseLong(requestDto.getLookupId()))))
                )
                .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    @Override
    public ResponseDto removeById(RequestDto requestDto ) {
        try{
            ModelValidatorUtils.runValidation(requestDto);
            newsDao.delete(Long.parseLong(requestDto.getLookupId()));
            return ResponseDto.builder()
                    .status("OK")
                    .resultSet(null)
                    .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }


    @Override
    public ResponseDto buildErrorResponse(Exception e) {
        if (e instanceof IllegalFieldValueException) {
            IllegalFieldValueException ifve = (IllegalFieldValueException) e;
            return ResponseDto.builder()
                .status("Failed")
                .error(
                        ErrorDto.builder().code(ifve.getErrorCode()).message(ifve.getMessage()).build()
                )
                .build();

        } else {
            return ResponseDto.builder()
                .status("Failed")
                .error(
                        ErrorDto.builder().code("0000123").message(e.getMessage()).build()
                )
                .build();
        }
    }
}
