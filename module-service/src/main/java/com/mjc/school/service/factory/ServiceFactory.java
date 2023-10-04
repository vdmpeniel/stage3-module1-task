package com.mjc.school.service.factory;

import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.implementation.AuthorService;
import com.mjc.school.service.implementation.NewsService;
import com.mjc.school.service.interfaces.ServiceInterface;

import java.util.Objects;

public class ServiceFactory {
    private static volatile ServiceFactory instance;
    private final ServiceInterface<AuthorDto> authorService;
    private final ServiceInterface<NewsDto> newsService;

    private ServiceFactory() {
        authorService = new AuthorService();
        newsService = new NewsService();
    }

    public static ServiceFactory getInstance(){
        synchronized(ServiceFactory.class) {
            if (Objects.isNull(instance)) {
                instance = new ServiceFactory();
            }
            return instance;
        }
    }

    public ServiceInterface<AuthorDto> getAuthorService(){
        return authorService;
    }

    public ServiceInterface<NewsDto> getNewsService(){
        return newsService;
    }
}
