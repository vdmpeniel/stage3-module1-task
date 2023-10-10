package com.mjc.school.controller.factory;

import com.mjc.school.controller.dto.RequestDto;
import com.mjc.school.controller.implementation.AuthorController;
import com.mjc.school.controller.implementation.NewsController;
import com.mjc.school.controller.interfaces.ModelControllerInterface;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.NewsDto;

import java.util.Objects;

public class ModelControllerFactory {
    private static volatile ModelControllerFactory instance;
    private final ModelControllerInterface<RequestDto, NewsDto> newsController;
    private final ModelControllerInterface<RequestDto, AuthorDto> authorController;

    private ModelControllerFactory(){
        newsController = new NewsController();
        authorController = new AuthorController();
    }

    public static ModelControllerFactory getInstance(){
        synchronized(ModelControllerFactory.class) {
            if (Objects.isNull(instance)) {
                instance = new ModelControllerFactory();
            }
            return instance;
        }
    }
    public ModelControllerInterface<RequestDto, NewsDto> getNewsController(){
        return newsController;
    }
    public ModelControllerInterface<RequestDto, AuthorDto> getAuthorService(){
        return authorController;
    }
}
