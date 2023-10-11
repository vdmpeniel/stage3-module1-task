package com.mjc.school.repository.implementation;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.mjc.school.repository.interfaces.Repository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.utils.DataSource;

public class NewsRepository implements Repository<NewsModel> {

  private final DataSource dataSource;

  public NewsRepository() {
    this.dataSource = DataSource.getInstance();
  }

  @Override
  public List<NewsModel> readAll() {
    return dataSource.getNews();
  }

  @Override
  public NewsModel readById(Long newsId) {
    return dataSource.getNews().stream()
        .filter(news -> newsId.equals(news.getId()))
        .findFirst()
        .get();
  }

  @Override
  public NewsModel create(NewsModel model) {
    List<NewsModel> newsModel = dataSource.getNews();
    newsModel.sort(Comparator.comparing(NewsModel::getId));
    if (!newsModel.isEmpty()) {
      model.setId(newsModel.get(newsModel.size() - 1).getId() + 1);
    } else {
      model.setId(1L);
    }
    newsModel.add(model);
    return model;
  }

  @Override
  public NewsModel update(NewsModel model) {
    NewsModel newsModel = readById(model.getId());
    newsModel.setTitle(model.getTitle());
    newsModel.setContent(model.getContent());
    newsModel.setLastUpdatedDate(model.getLastUpdatedDate());
    newsModel.setAuthorId(model.getAuthorId());
    return newsModel;
  }

  @Override
  public Boolean deleteById(Long newsId) {
    List<NewsModel> deleteList = new ArrayList<>();
    deleteList.add(this.readById(newsId));
    return dataSource.getNews().removeAll(deleteList);
  }

  @Override
  public Boolean isExistById(Long newsId) {
    return dataSource.getNews().stream().anyMatch(news -> newsId.equals(news.getId()));
  }
}
