package com.mjc.school.service.implementation;

import com.mjc.school.common.implementation.exceptions.IllegalFieldValueException;
import com.mjc.school.common.implementation.utils.PropertyLoader;
import com.mjc.school.common.implementation.utils.modelvalidatorutils.ModelValidatorUtils;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.interfaces.RepositoryInterface;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.ErrorDto;
import com.mjc.school.service.dto.RequestDto;
import com.mjc.school.service.dto.ResponseDto;
import com.mjc.school.service.interfaces.AuthorMapperInterface;
import com.mjc.school.service.interfaces.ModelDtoInterface;
import com.mjc.school.service.interfaces.ServiceInterface;

import java.util.List;

public class AuthorService implements ServiceInterface<RequestDto, ResponseDto> {
    PropertyLoader propertyLoader;
    RepositoryInterface authorDao;

    public AuthorService(){
        try {
            propertyLoader = PropertyLoader.getInstance();
            authorDao = new AuthorRepository();

        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }


    @Override
    public ResponseDto create(RequestDto requestDto){
        try {
            ModelValidatorUtils.runValidation(requestDto.getInputData());

            AuthorModel authorModel = AuthorMapperInterface.INSTANCE.authorDtoToAuthor((AuthorDto) requestDto.getInputData());
            authorDao.create(authorModel);

            return ResponseDto
                    .builder()
                    .status("OK")
                    .resultSet(
                            getById(
                                    RequestDto.builder().lookupId(
                                            authorModel.getId().toString()
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
                            authorDao.readAll()
                                    .stream()
                                    .map(model -> AuthorMapperInterface.INSTANCE.authorToAuthorDto((AuthorModel) model))
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
                            List.of(AuthorMapperInterface.INSTANCE.authorToAuthorDto(
                                    (AuthorModel) authorDao.readById(Long.parseLong(requestDto.getLookupId()))
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

            AuthorModel authorModel = AuthorMapperInterface.INSTANCE.authorDtoToAuthor((AuthorDto) requestDto.getInputData());
            authorModel.setId(Long.parseLong(requestDto.getLookupId()));
            authorDao.update(authorModel);

            return ResponseDto
                    .builder()
                    .status("OK")
                    .resultSet(
                            List.of(AuthorMapperInterface.INSTANCE.authorToAuthorDto((AuthorModel) authorDao.readById(Long.parseLong(requestDto.getLookupId()))))
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
            authorDao.delete(Long.parseLong(requestDto.getLookupId()));
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
