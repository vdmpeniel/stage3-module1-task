package com.mjc.school.service.implementation;

import com.mjc.school.common.implementation.utils.modelvalidatorutils.ModelValidatorUtils;
import com.mjc.school.repository.factory.RepositoryFactory;
import com.mjc.school.repository.interfaces.ModelInterface;
import com.mjc.school.repository.interfaces.RepositoryInterface;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.interfaces.NewsMapperInterface;
import com.mjc.school.service.interfaces.ServiceInterface;

import java.time.LocalDateTime;
import java.util.List;

public class NewsService implements ServiceInterface<NewsDto> {
    private final RepositoryInterface<ModelInterface> newsRepository;

    public NewsService(){
        newsRepository = RepositoryFactory.getInstance().getNewsRepository();
    }

    public NewsDto create(NewsDto newsDto) throws Exception{
        ModelValidatorUtils.runValidation(newsDto);
        NewsModel newsModel = NewsMapperInterface.INSTANCE.newsDtoToNews(newsDto);
        newsModel.setCreateDate(LocalDateTime.now());
        newsModel.setLastUpdateDate(newsModel.getCreateDate());
        return NewsMapperInterface.INSTANCE.newsToNewsDto((NewsModel) this.newsRepository.create(newsModel));
    }


    public List<NewsDto> readAll() throws Exception{
        return newsRepository.readAll()
            .stream()
            .map(model -> NewsMapperInterface.INSTANCE.newsToNewsDto((NewsModel) model))
            .toList();
    }


    public NewsDto readById(Long id) throws Exception{
        return NewsMapperInterface.INSTANCE.newsToNewsDto(
                (NewsModel) newsRepository.readById(Long.parseLong(id.toString()))
        );
    }


    public NewsDto updateById(Long id,  NewsDto model) throws Exception{
        ModelValidatorUtils.runValidation(model);
        NewsModel newsModel = NewsMapperInterface.INSTANCE.newsDtoToNews(model);
        newsModel.setLastUpdateDate(LocalDateTime.now());
        newsModel.setId(Long.parseLong(id.toString()));
        return NewsMapperInterface.INSTANCE.newsToNewsDto( (NewsModel) newsRepository.update(newsModel) );
    }


    public Boolean deleteById(Long id ) throws Exception{
        return newsRepository.delete(Long.parseLong(id.toString()));
    }

}
