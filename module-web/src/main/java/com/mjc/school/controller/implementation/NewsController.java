package com.mjc.school.controller.implementation;

import com.mjc.school.common.implementation.exceptions.IllegalFieldValueException;
import com.mjc.school.common.implementation.utils.modelvalidatorutils.ModelValidatorUtils;
import com.mjc.school.controller.interfaces.ModelControllerInterface;
import com.mjc.school.service.dto.ErrorDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.RequestDto;
import com.mjc.school.service.dto.ResponseDto;
import com.mjc.school.service.implementation.NewsService;
import com.mjc.school.service.interfaces.ModelDtoInterface;

import java.util.List;

public class NewsController implements ModelControllerInterface<RequestDto, ResponseDto> {

    private final NewsService newsService;

    public NewsController(){
        newsService = new NewsService();
    }


    @Override
    public ResponseDto create(RequestDto requestDto){
        try {
            NewsDto newsDto = (NewsDto) requestDto.getInputData();
            newsDto = newsService.create(newsDto);
            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                    readById(
                        RequestDto.builder().lookupId(
                            newsDto.getId()
                        ).build()
                    ).getResultSet()
                )
                .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    @Override
    public ResponseDto readAll() {
        try{
            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                        newsService.readAll()
                                .stream()
                                .map(model -> (ModelDtoInterface) model)
                                .toList())
                .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    @Override
    public ResponseDto readById(RequestDto requestDto) {
        try{
            ModelValidatorUtils.runValidation(requestDto);
            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                    List.of( newsService.readById(Long.parseLong(requestDto.getLookupId())) )
                )
                .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    @Override
    public ResponseDto updateById(RequestDto requestDto) {
        try{

            newsService.updateById(
                Long.parseLong(requestDto.getLookupId()),
                (NewsDto) requestDto.getInputData()
            );

            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                    List.of(newsService.readById(Long.parseLong(requestDto.getLookupId())))
                )
                .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    @Override
    public ResponseDto deleteById(RequestDto requestDto ) {
        try{
            ModelValidatorUtils.runValidation(requestDto);
            newsService.deleteById(Long.parseLong(requestDto.getLookupId()));
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
        if (e instanceof IllegalFieldValueException ifve) {
            return ResponseDto.builder()
                .status("Failed")
                .error(ErrorDto.builder().code(ifve.getErrorCode()).message(ifve.getMessage()).build())
                .build();

        } else {
            return ResponseDto.builder()
                .status("Failed")
                .error(ErrorDto.builder().code("0000123").message(e.getMessage()).build())
                .build();
        }
    }
}
