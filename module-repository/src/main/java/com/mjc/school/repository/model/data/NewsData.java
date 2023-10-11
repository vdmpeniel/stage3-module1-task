package com.mjc.school.repository.model.data;

import static com.mjc.school.repository.utils.Utils.getRandomContentByFilePath;
import static com.mjc.school.repository.utils.Utils.getRandomDate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;

public class NewsData {
  private static final String CONTENT_FILE_NAME = "content";
  private static final String NEWS_FILE_NAME = "news";
  private static NewsData newsData;
  private List<NewsModel> newsList;

  private NewsData(List<AuthorModel> authorModelList) {
    init(authorModelList);
  }

  public static NewsData getNewsData(List<AuthorModel> authorModelList) {
    if (newsData == null) {
      newsData = new NewsData(authorModelList);
    }
    return newsData;
  }

  private void init(List<AuthorModel> authorModelList) {
    newsList = new ArrayList<>();
    Random random = new Random();
    for (long i = 1; i <= 20; i++) {
      LocalDateTime date = getRandomDate();
      newsList.add(
          new NewsModel(
              i,
              getRandomContentByFilePath(NEWS_FILE_NAME),
              getRandomContentByFilePath(CONTENT_FILE_NAME),
              date,
              date,
              authorModelList.get(random.nextInt(authorModelList.size())).getId()));
    }
  }

  public List<NewsModel> getNewsList() {
    return newsList;
  }
}
