package com.mjc.school.controller.implementation;

import com.mjc.school.controller.dto.RequestDto;
import com.mjc.school.controller.interfaces.ModelControllerInterface;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.factory.ServiceFactory;
import com.mjc.school.service.interfaces.ServiceInterface;
import com.mjc.school.service.validator.ModelValidator;

import java.util.List;

public class AuthorController implements ModelControllerInterface<RequestDto, AuthorDto> {

    private final ServiceInterface< AuthorDto>  authorService;
    private final ModelValidator modelValidator;

    public  AuthorController(){
        authorService = ServiceFactory.getInstance().getAuthorService();
        modelValidator = ModelValidator.getValidator();
    }



    public  AuthorDto create(RequestDto requestDto) throws Exception{
        return  authorService.create(( AuthorDto) requestDto.getInputData());
    }


    public List<AuthorDto> readAll() throws Exception{
        return  authorService.readAll();
    }


    public  AuthorDto readById(Long id) throws Exception{
        RequestDto requestDto = RequestDto.builder().lookupId(id.toString()).build();
        modelValidator.runValidation(requestDto);
        return  authorService.readById(Long.parseLong(requestDto.getLookupId()));
    }


    public  AuthorDto updateById(RequestDto requestDto) throws Exception{
        modelValidator.runValidation(requestDto);
         AuthorDto  authorDto = ( AuthorDto) requestDto.getInputData();
         authorDto.setId(Long.parseLong(requestDto.getLookupId()));

        return  authorService.updateById( authorDto);
    }


    public Boolean deleteById(Long id) throws Exception{
        RequestDto requestDto = RequestDto.builder().lookupId(id.toString()).build();
        modelValidator.runValidation(requestDto);
        return  authorService.deleteById(Long.parseLong(requestDto.getLookupId()));
    }
}
