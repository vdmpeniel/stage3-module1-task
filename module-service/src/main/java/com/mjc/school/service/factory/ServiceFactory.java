package com.mjc.school.service.factory;

import com.mjc.school.service.dto.RequestDto;
import com.mjc.school.service.dto.ResponseDto;
import com.mjc.school.service.implementation.AuthorService;
import com.mjc.school.service.implementation.NewsService;
import com.mjc.school.service.interfaces.ServiceInterface;

import java.util.Objects;

public class ServiceFactory{
    private static volatile ServiceFactory instance;
    private final ServiceInterface<RequestDto, ResponseDto> newsService;
    private final ServiceInterface<RequestDto, ResponseDto> authorService;

    private ServiceFactory(){
        newsService = new NewsService();
        authorService = new AuthorService();
    }

    public static ServiceFactory getInstance(){
        synchronized(ServiceFactory.class) {
            if (Objects.isNull(instance)) {
                instance = new ServiceFactory();
            }
            return instance;
        }
    }
    public ServiceInterface<RequestDto, ResponseDto> getNewsService(){
        return newsService;
    }
    public ServiceInterface<RequestDto, ResponseDto> getAuthorService(){
        return authorService;
    }
}
