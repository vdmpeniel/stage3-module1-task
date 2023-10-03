package com.mjc.school.service.implementation;

import com.mjc.school.common.implementation.exceptions.IllegalFieldValueException;
import com.mjc.school.common.implementation.utils.PropertyLoader;
import com.mjc.school.common.implementation.utils.modelvalidatorutils.ModelValidatorUtils;
import com.mjc.school.repository.interfaces.RepositoryInterface;
import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.ErrorDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.RequestDto;
import com.mjc.school.service.dto.ResponseDto;
import com.mjc.school.service.interfaces.ModelDtoInterface;
import com.mjc.school.service.interfaces.NewsMapperInterface;
import com.mjc.school.service.interfaces.ServiceInterface;

import java.time.LocalDateTime;
import java.util.List;

public class NewsService implements ServiceInterface<RequestDto, ResponseDto> {
    PropertyLoader propertyLoader;
    RepositoryInterface newsDao;

    public NewsService(){
        try {
            propertyLoader = PropertyLoader.getInstance();
            newsDao = new NewsRepository();

        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }


    @Override
    public ResponseDto create(RequestDto requestDto){
        try {
            ModelValidatorUtils.runValidation(requestDto.getInputData());

            NewsModel newsModel = NewsMapperInterface.INSTANCE.newsDtoToNews((NewsDto) requestDto.getInputData());
            newsModel.setCreateDate(LocalDateTime.now());
            newsModel.setLastUpdateDate(newsModel.getCreateDate());
            newsDao.create(newsModel);

            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                    getById(
                        RequestDto.builder().lookupId(
                            newsModel.getId().toString()
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
                        newsDao.readAll()
                                .stream()
                                .map(model -> NewsMapperInterface.INSTANCE.newsToNewsDto((NewsModel) model))
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
                    List.of(NewsMapperInterface.INSTANCE.newsToNewsDto(
                        (NewsModel) newsDao.readById(Long.parseLong(requestDto.getLookupId()))
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

            NewsModel newsModel = NewsMapperInterface.INSTANCE.newsDtoToNews((NewsDto) requestDto.getInputData());
            newsModel.setLastUpdateDate(LocalDateTime.now());
            newsModel.setId(Long.parseLong(requestDto.getLookupId()));
            newsDao.update(newsModel);

            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                    List.of(NewsMapperInterface.INSTANCE.newsToNewsDto((NewsModel) newsDao.readById(Long.parseLong(requestDto.getLookupId()))))
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
