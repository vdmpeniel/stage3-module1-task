package com.mjc.school.repository.factory;

import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.interfaces.Repository;
import com.mjc.school.repository.model.NewsModel;

public class RepositoryFactory {
  private static RepositoryFactory instance;
  private final Repository<NewsModel> newsRepository;

  private RepositoryFactory() {
    newsRepository = new NewsRepository();
  }

  public static RepositoryFactory getInstance() {
    if (instance == null) {
      instance = new RepositoryFactory();
    }
    return instance;
  }

  public Repository<NewsModel> getNewsRepository() {
    return newsRepository;
  }
}
