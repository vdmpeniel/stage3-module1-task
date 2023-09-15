package com.mjc.school.service;

import com.mjc.school.common.utils.ModelValidatorUtils;
import com.mjc.school.repository.dao.NewsDao;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.mappers.NewsDtoToNewsMapper;
import com.mjc.school.service.mappers.NewsToNewsDtoMapper;

import java.time.LocalDateTime;
import java.util.List;

public class NewsService {
    NewsDao newsDao = new NewsDao();

    public NewsService() throws Exception{}

    public NewsDto createNews(NewsDto newsDto) throws Exception {
        ModelValidatorUtils.validateAndThrow(newsDto);

        News news = NewsDtoToNewsMapper.INSTANCE.newsDtoToNews(newsDto);
        news.setCreateDate(LocalDateTime.now());
        news.setLastUpdateDate(news.getCreateDate());
        newsDao.create(news);
        return getNewsById(news.getId());
    }

    public List<NewsDto> getAllNews() throws Exception {
        return newsDao.getAll().stream().map(NewsToNewsDtoMapper.INSTANCE::newsToNewsDto).toList();
    }

    public NewsDto getNewsById(Long id) throws Exception {
        return NewsToNewsDtoMapper.INSTANCE.newsToNewsDto(newsDao.findById(id));
    }

    public NewsDto updateNewsById(Long id, NewsDto newsDto) throws Exception {
        ModelValidatorUtils.validateAndThrow(newsDto);

        News news = NewsDtoToNewsMapper.INSTANCE.newsDtoToNews(newsDto);
        newsDao.update(id, news);
        return NewsToNewsDtoMapper.INSTANCE.newsToNewsDto(newsDao.findById(id));
    }
    public boolean removeNewsById(Long id) throws Exception {
        return newsDao.deleteById(id);
    }
}
