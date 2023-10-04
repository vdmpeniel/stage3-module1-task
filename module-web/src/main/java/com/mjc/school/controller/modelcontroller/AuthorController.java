package com.mjc.school.controller.modelcontroller;

import com.mjc.school.common.implementation.exceptions.IllegalFieldValueException;
import com.mjc.school.common.implementation.utils.modelvalidatorutils.ModelValidatorUtils;
import com.mjc.school.controller.dto.ErrorDto;
import com.mjc.school.controller.dto.RequestDto;
import com.mjc.school.controller.dto.ResponseDto;
import com.mjc.school.controller.interfaces.ModelControllerInterface;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.factory.ServiceFactory;
import com.mjc.school.service.interfaces.ModelDtoInterface;
import com.mjc.school.service.interfaces.ServiceInterface;

import java.util.List;

public class AuthorController implements ModelControllerInterface<RequestDto, ResponseDto> {
    private final ServiceInterface<AuthorDto>  authorService;

    public  AuthorController(){
         authorService = ServiceFactory.getInstance().getAuthorService();
    }


    @Override
    public ResponseDto create(RequestDto requestDto){
        try {
             AuthorDto  authorDto = ( AuthorDto) requestDto.getInputData();
             authorDto =  authorService.create( authorDto);
            return ResponseDto
                    .builder()
                    .status("OK")
                    .resultSet(
                            readById(
                                    RequestDto.builder().lookupId(
                                             authorDto.getId().toString()
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
                             authorService.readAll()
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
                            List.of(  authorService.readById(Long.parseLong(requestDto.getLookupId())) )
                    )
                    .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    @Override
    public ResponseDto updateById(RequestDto requestDto) {
        try{
             AuthorDto authorDto = (AuthorDto) requestDto.getInputData();
             authorDto.setId(Long.parseLong(requestDto.getLookupId()));

             authorService.updateById(authorDto);
            return ResponseDto
                    .builder()
                    .status("OK")
                    .resultSet(
                            List.of(authorService.readById(Long.parseLong(requestDto.getLookupId())))
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
             authorService.deleteById(Long.parseLong(requestDto.getLookupId()));
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
