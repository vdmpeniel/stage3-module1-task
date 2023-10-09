package com.mjc.school.controller.modelcontroller;

import com.mjc.school.common.exceptions.IllegalFieldValueException;
import com.mjc.school.common.implementation.ModelValidator;
import com.mjc.school.controller.dto.RequestDto;
import com.mjc.school.controller.dto.ResponseDto;
import com.mjc.school.controller.interfaces.ModelControllerInterface;
import com.mjc.school.controller.dto.ErrorDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.factory.ServiceFactory;
import com.mjc.school.service.interfaces.ModelDtoInterface;
import com.mjc.school.service.interfaces.ServiceInterface;

import java.util.List;

public class NewsController implements ModelControllerInterface<RequestDto, ResponseDto> {

    private ServiceInterface<NewsDto> newsService;
    private ModelValidator validator;

    public NewsController(){
        try{
            newsService = ServiceFactory.getInstance().getNewsService();
            validator = ModelValidator.getInstance();
        } catch(Exception e){ buildErrorResponse(e); }
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
                            newsDto.getId().toString()
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
            validator.runValidation(requestDto);
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
            NewsDto newsDto = (NewsDto) requestDto.getInputData();
            newsDto.setId(Long.parseLong(requestDto.getLookupId()));

            newsService.updateById(newsDto);
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
            validator.runValidation(requestDto);
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
