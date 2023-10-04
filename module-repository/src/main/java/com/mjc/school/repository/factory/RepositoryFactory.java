package com.mjc.school.repository.factory;

import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.interfaces.ModelInterface;
import com.mjc.school.repository.interfaces.RepositoryInterface;

import java.util.Objects;

public class RepositoryFactory {

    private static volatile RepositoryFactory instance;
    private final RepositoryInterface<ModelInterface> authorRepository;
    private final RepositoryInterface<ModelInterface> newsRepository;

    private RepositoryFactory() {
        authorRepository = new AuthorRepository();
        newsRepository = new NewsRepository();
    }

    public static RepositoryFactory getInstance(){
        synchronized(RepositoryFactory.class) {
            if (Objects.isNull(instance)) {
                instance = new RepositoryFactory();
            }
            return instance;
        }
    }
    public RepositoryInterface<ModelInterface> getNewsRepository(){
        return newsRepository;
    }
    public RepositoryInterface<ModelInterface> getAuthorRepository(){
        return newsRepository;
    }
}
