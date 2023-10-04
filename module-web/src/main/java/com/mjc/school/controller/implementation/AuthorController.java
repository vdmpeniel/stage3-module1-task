package com.mjc.school.controller.implementation;

import com.mjc.school.common.implementation.exceptions.IllegalFieldValueException;
import com.mjc.school.common.implementation.utils.modelvalidatorutils.ModelValidatorUtils;
import com.mjc.school.controller.interfaces.ModelControllerInterface;
import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.interfaces.ModelInterface;
import com.mjc.school.repository.interfaces.RepositoryInterface;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.ErrorDto;
import com.mjc.school.service.dto.RequestDto;
import com.mjc.school.service.dto.ResponseDto;
import com.mjc.school.service.interfaces.AuthorMapperInterface;
import com.mjc.school.service.interfaces.ModelDtoInterface;

import java.util.List;

public class AuthorController implements ModelControllerInterface<RequestDto, ResponseDto> {

    private final RepositoryInterface<ModelInterface> authorRepository;

    public AuthorController(){
        authorRepository = new AuthorRepository();
    }


    @Override
    public ResponseDto create(RequestDto requestDto){
        try {
            ModelValidatorUtils.runValidation(requestDto.getInputData());

            AuthorModel authorModel = AuthorMapperInterface.INSTANCE.authorDtoToAuthor((AuthorDto) requestDto.getInputData());
            authorRepository.create(authorModel);

            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                    readById(
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
    public ResponseDto readAll() {
        try{
            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                    authorRepository.readAll()
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
    public ResponseDto readById(RequestDto requestDto) {
        try{
            ModelValidatorUtils.runValidation(requestDto);
            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                        List.of(AuthorMapperInterface.INSTANCE.authorToAuthorDto(
                                (AuthorModel) authorRepository.readById(Long.parseLong(requestDto.getLookupId()))
                        ))
                )
                .build();

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
            authorRepository.update(authorModel);

            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                        List.of(AuthorMapperInterface.INSTANCE.authorToAuthorDto((AuthorModel) authorRepository.readById(Long.parseLong(requestDto.getLookupId()))))
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
            authorRepository.delete(Long.parseLong(requestDto.getLookupId()));
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
                .error(
                        ErrorDto.builder().code("0000123").message(e.getMessage()).build()
                )
                .build();
        }
    }
}
