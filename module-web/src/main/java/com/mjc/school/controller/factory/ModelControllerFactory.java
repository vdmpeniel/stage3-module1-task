package com.mjc.school.controller.factory;

import com.mjc.school.controller.implementation.AuthorController;
import com.mjc.school.controller.implementation.NewsController;
import com.mjc.school.controller.interfaces.ModelControllerInterface;
import com.mjc.school.service.dto.RequestDto;
import com.mjc.school.service.dto.ResponseDto;

import java.util.Objects;

public class ModelControllerFactory {
    private static volatile ModelControllerFactory instance;
    private final ModelControllerInterface<RequestDto, ResponseDto> newsService;
    private final ModelControllerInterface<RequestDto, ResponseDto> authorService;

    private ModelControllerFactory(){
        newsService = new NewsController();
        authorService = new AuthorController();
    }

    public static ModelControllerFactory getInstance(){
        synchronized(ModelControllerFactory.class) {
            if (Objects.isNull(instance)) {
                instance = new ModelControllerFactory();
            }
            return instance;
        }
    }
    public ModelControllerInterface<RequestDto, ResponseDto> getNewsService(){
        return newsService;
    }
    public ModelControllerInterface<RequestDto, ResponseDto> getAuthorService(){
        return authorService;
    }
}
