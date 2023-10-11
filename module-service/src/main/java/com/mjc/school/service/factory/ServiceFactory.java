package com.mjc.school.service.factory;

import com.mjc.school.service.implementation.NewsService;

public class ServiceFactory {
  private static ServiceFactory instance;
  private final NewsService newsService;

  private ServiceFactory() {
    newsService = new NewsService();
  }

  public static ServiceFactory getInstance() {
    if (instance == null) {
      instance = new ServiceFactory();
    }
    return instance;
  }

  public NewsService getNewsService() {
    return newsService;
  }
}
