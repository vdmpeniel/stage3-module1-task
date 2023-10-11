package com.mjc.school.service.implementation;

import com.mjc.school.repository.factory.RepositoryFactory;
import com.mjc.school.repository.interfaces.ModelInterface;
import com.mjc.school.repository.interfaces.RepositoryInterface;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.interfaces.NewsMapperInterface;
import com.mjc.school.service.interfaces.ServiceInterface;
import com.mjc.school.service.validator.ModelValidator;

import java.time.LocalDateTime;
import java.util.List;

public class NewsService implements ServiceInterface<NewsDtoResponse> {
    private final RepositoryInterface<ModelInterface> newsRepository;
    private final ModelValidator modelValidator;

    public NewsService(){
        newsRepository = RepositoryFactory.getInstance().getNewsRepository();
        modelValidator = ModelValidator.getValidator();
    }

    public NewsDtoResponse create(NewsDtoResponse modelDto) throws Exception{
        modelValidator.runValidation(modelDto);
        NewsModel newsModel = NewsMapperInterface.INSTANCE.newsDtoToNews(modelDto);
        newsModel.setCreateDate(LocalDateTime.now());
        newsModel.setLastUpdateDate(newsModel.getCreateDate());
        return NewsMapperInterface.INSTANCE.newsToNewsDto((NewsModel) this.newsRepository.create(newsModel));
    }

    public List<NewsDtoResponse> readAll() throws Exception{
        return newsRepository.readAll()
            .stream()
            .map(model -> NewsMapperInterface.INSTANCE.newsToNewsDto((NewsModel) model))
            .toList();
    }

    public NewsDtoResponse readById(Long id) throws Exception{
        return NewsMapperInterface.INSTANCE.newsToNewsDto(
            (NewsModel) newsRepository.readById(Long.parseLong(id.toString()))
        );
    }

    public NewsDtoResponse updateById(NewsDtoResponse modelDto) throws Exception{
        modelValidator.runValidation(modelDto);
        NewsModel newsModel = NewsMapperInterface.INSTANCE.newsDtoToNews(modelDto);
        newsModel.setLastUpdateDate(LocalDateTime.now());
        newsModel.setId(modelDto.getId());
        return NewsMapperInterface.INSTANCE.newsToNewsDto( (NewsModel) newsRepository.update(newsModel) );
    }

    public Boolean deleteById(Long id) throws Exception{
        return newsRepository.delete(Long.parseLong(id.toString()));
    }

}
