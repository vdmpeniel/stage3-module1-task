package com.mjc.school.controller.implementation;

import com.mjc.school.controller.dto.RequestDto;
import com.mjc.school.controller.interfaces.ModelControllerInterface;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.factory.ServiceFactory;
import com.mjc.school.service.interfaces.ServiceInterface;
import com.mjc.school.service.validator.ModelValidator;

import java.util.List;

public class NewsController implements ModelControllerInterface<RequestDto, NewsDto> {


    private final ServiceInterface<NewsDto> newsService;
    private final ModelValidator modelValidator;

    public NewsController(){
        newsService = ServiceFactory.getInstance().getNewsService();
        modelValidator = ModelValidator.getValidator();
    }



    public NewsDto create(RequestDto requestDto) throws Exception {
        return newsService.create((NewsDto) requestDto.getInputData());
    }


    public List<NewsDto> readAll() throws Exception{
        return newsService.readAll();
    }


    public NewsDto readById(Long id) throws Exception{
        RequestDto requestDto = RequestDto.builder().lookupId(id.toString()).build();
        modelValidator.runValidation(requestDto);
        return newsService.readById(Long.parseLong(requestDto.getLookupId()));
    }


    public NewsDto updateById(RequestDto requestDto) throws Exception{
        modelValidator.runValidation(requestDto);
        NewsDto newsDto = (NewsDto) requestDto.getInputData();
        newsDto.setId(Long.parseLong(requestDto.getLookupId()));

        return newsService.updateById(newsDto);
    }


    public Boolean deleteById(Long id) throws Exception{
        RequestDto requestDto = RequestDto.builder().lookupId(id.toString()).build();
        modelValidator.runValidation(requestDto);
        return newsService.deleteById(Long.parseLong(requestDto.getLookupId()));
    }




}
