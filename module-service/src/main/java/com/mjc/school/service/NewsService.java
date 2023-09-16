package com.mjc.school.service;

import com.mjc.school.common.utils.ModelValidatorUtils;
import com.mjc.school.repository.dao.NewsDao;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.mapper.NewsMapper;

import java.time.LocalDateTime;
import java.util.List;

public class NewsService {
    NewsDao newsDao = new NewsDao();

    public NewsService() throws Exception{}

    public NewsDto createNews(NewsDto newsDto){
        try {
            ModelValidatorUtils.validateAndThrow(newsDto);

            News news = NewsMapper.INSTANCE.mapToNews(newsDto);
            news.setCreateDate(LocalDateTime.now());
            news.setLastUpdateDate(news.getCreateDate());
            newsDao.create(news);
            return getNewsById(news.getId());

        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public List<NewsDto> getAllNews() {
        try{
            return newsDao.getAll().stream().map(NewsMapper.INSTANCE::mapToNewsDto).toList();

        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public NewsDto getNewsById(Long id) {
        try{
            return NewsMapper.INSTANCE.mapToNewsDto(newsDao.findById(id));

        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public NewsDto updateNewsById(Long id, NewsDto newsDto) {
        try{
            //ModelValidatorUtils.validateAndThrow(newsDto);

            News news = NewsMapper.INSTANCE.mapToNews(newsDto);
            newsDao.update(id, news);
            return NewsMapper.INSTANCE.mapToNewsDto(newsDao.findById(id));

        } catch(Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }
    public boolean removeNewsById(Long id) {
        try{
            return newsDao.deleteById(id);

        } catch(Exception e){
            System.out.println(e.getMessage());
            return false;
        }
    }
}
