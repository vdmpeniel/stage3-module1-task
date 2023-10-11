package com.mjc.school.controller.factory;

import com.mjc.school.controller.dto.RequestDtoRequest;
import com.mjc.school.controller.implementation.NewsController;
import com.mjc.school.controller.interfaces.ModelControllerInterface;
import com.mjc.school.service.dto.NewsDtoResponse;

import java.util.Objects;

public class ModelControllerFactory {
    private static volatile ModelControllerFactory instance;
    private final ModelControllerInterface<RequestDtoRequest, NewsDtoResponse> newsController;


    private ModelControllerFactory(){
        newsController = new NewsController();

    }

    public static ModelControllerFactory getInstance(){
        synchronized(ModelControllerFactory.class) {
            if (Objects.isNull(instance)) {
                instance = new ModelControllerFactory();
            }
            return instance;
        }
    }
    public ModelControllerInterface<RequestDtoRequest, NewsDtoResponse> getNewsController(){
        return newsController;
    }

}
