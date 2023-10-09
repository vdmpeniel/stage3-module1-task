package com.mjc.school.service.implementation;

import com.mjc.school.repository.factory.RepositoryFactory;
import com.mjc.school.repository.interfaces.ModelInterface;
import com.mjc.school.repository.interfaces.RepositoryInterface;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.interfaces.NewsMapperInterface;
import com.mjc.school.service.interfaces.ServiceInterface;
import com.mjc.school.service.validator.ModelValidator;

import java.time.LocalDateTime;
import java.util.List;

public class NewsService implements ServiceInterface<NewsDto> {
    private final RepositoryInterface<ModelInterface> newsRepository;
    private final ModelValidator validator;

    public NewsService() throws Exception{
        newsRepository = RepositoryFactory.getInstance().getNewsRepository();
        validator = ModelValidator.getValidator();
    }

    public NewsDto create(NewsDto modelDto) throws Exception{
        validator.runValidation(modelDto);
        NewsModel newsModel = NewsMapperInterface.INSTANCE.newsDtoToNews(modelDto);
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

    public NewsDto updateById(NewsDto modelDto) throws Exception{
        validator.runValidation(modelDto);
        NewsModel newsModel = NewsMapperInterface.INSTANCE.newsDtoToNews(modelDto);
        newsModel.setLastUpdateDate(LocalDateTime.now());
        newsModel.setId(modelDto.getId());
        return NewsMapperInterface.INSTANCE.newsToNewsDto( (NewsModel) newsRepository.update(newsModel) );
    }

    public Boolean deleteById(Long id) throws Exception{
        return newsRepository.delete(Long.parseLong(id.toString()));
    }

}
