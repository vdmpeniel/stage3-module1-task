package com.mjc.school.controller.implementation;

import com.mjc.school.controller.dto.RequestDtoRequest;
import com.mjc.school.controller.interfaces.ModelControllerInterface;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.factory.ServiceFactory;
import com.mjc.school.service.interfaces.ServiceInterface;
import com.mjc.school.service.validator.ModelValidator;

import java.util.List;

public class NewsController implements ModelControllerInterface<RequestDtoRequest, NewsDtoResponse> {


    private final ServiceInterface<NewsDtoResponse> newsService;
    private final ModelValidator modelValidator;

    public NewsController(){
        newsService = ServiceFactory.getInstance().getNewsService();
        modelValidator = ModelValidator.getValidator();
    }



    public NewsDtoResponse create(RequestDtoRequest requestDtoRequest) throws Exception {
        return newsService.create((NewsDtoResponse) requestDtoRequest.getInputData());
    }


    public List<NewsDtoResponse> readAll() throws Exception{
        return newsService.readAll();
    }


    public NewsDtoResponse readById(Long id) throws Exception{
        RequestDtoRequest requestDtoRequest = RequestDtoRequest.builder().lookupId(id.toString()).build();
        modelValidator.runValidation(requestDtoRequest);
        return newsService.readById(Long.parseLong(requestDtoRequest.getLookupId()));
    }


    public NewsDtoResponse updateById(RequestDtoRequest requestDtoRequest) throws Exception{
        modelValidator.runValidation(requestDtoRequest);
        NewsDtoResponse newsDtoResponse = (NewsDtoResponse) requestDtoRequest.getInputData();
        newsDtoResponse.setId(Long.parseLong(requestDtoRequest.getLookupId()));
        return newsService.updateById(newsDtoResponse);
    }


    public Boolean deleteById(Long id) throws Exception{
        RequestDtoRequest requestDtoRequest = RequestDtoRequest.builder().lookupId(id.toString()).build();
        modelValidator.runValidation(requestDtoRequest);
        return newsService.deleteById(Long.parseLong(requestDtoRequest.getLookupId()));
    }
}
