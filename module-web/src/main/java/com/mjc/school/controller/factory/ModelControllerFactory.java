package com.mjc.school.controller.factory;

import com.mjc.school.controller.implementation.NewsController;

import java.util.Objects;

public class ModelControllerFactory {
    private static volatile ModelControllerFactory instance;
    private final NewsController newsController;


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
    public NewsController getNewsController(){
        return newsController;
    }

}
