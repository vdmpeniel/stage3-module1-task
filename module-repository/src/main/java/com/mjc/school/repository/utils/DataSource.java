package com.mjc.school.repository.utils;

import static com.mjc.school.repository.model.data.AuthorData.getAuthorData;
import static com.mjc.school.repository.model.data.NewsData.getNewsData;

import java.util.List;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;

public class DataSource {

  private final List<NewsModel> news;

  private DataSource() {
    List<AuthorModel> authorList = getAuthorData().getAuthorList();
    news = getNewsData(authorList).getNewsList();
  }

  public static DataSource getInstance() {
    return LazyDataSource.INSTANCE;
  }

  public List<NewsModel> getNews() {
    return news;
  }

  private static class LazyDataSource {
    static final DataSource INSTANCE = new DataSource();
  }
}
