package com.mjc.school.service.implementation;

import static com.mjc.school.service.exceptions.ServiceErrorCode.NEWS_ID_DOES_NOT_EXIST;
import static com.mjc.school.service.validator.NewsValidator.getNewsValidator;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.mapstruct.factory.Mappers;

import com.mjc.school.repository.factory.RepositoryFactory;
import com.mjc.school.repository.interfaces.Repository;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.exceptions.NotFoundException;
import com.mjc.school.service.interfaces.ModelMapper;
import com.mjc.school.service.interfaces.Service;
import com.mjc.school.service.validator.NewsValidator;

public class NewsService implements Service<NewsDtoRequest, NewsDtoResponse> {

  private final Repository<NewsModel> newsRepository;
  private final NewsValidator newsValidator;
  private ModelMapper mapper = Mappers.getMapper(ModelMapper.class);

  public NewsService() {
    this.newsRepository = RepositoryFactory.getInstance().getNewsRepository();
    newsValidator = getNewsValidator();
  }

  @Override
  public List<NewsDtoResponse> readAll() {
    return mapper.modelListToDtoList(newsRepository.readAll());
  }

  @Override
  public NewsDtoResponse readById(Long newsId) {
    newsValidator.validateNewsId(newsId);
    if (newsRepository.isExistById(newsId)) {
      NewsModel newsModel = newsRepository.readById(newsId);
      return mapper.modelToDto(newsModel);
    } else {
      throw new NotFoundException(
          String.format(String.valueOf(NEWS_ID_DOES_NOT_EXIST.getMessage()), newsId));
    }
  }

  @Override
  public NewsDtoResponse create(NewsDtoRequest dtoRequest) {
    newsValidator.validateNewsDto(dtoRequest);
    NewsModel model = mapper.dtoToModel(dtoRequest);
    LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
    model.setCreateDate(date);
    model.setLastUpdatedDate(date);
    NewsModel newsModel = newsRepository.create(model);
    return mapper.modelToDto(newsModel);
  }

  @Override
  public NewsDtoResponse update(NewsDtoRequest dtoRequest) {
    newsValidator.validateNewsId(dtoRequest.id());
    newsValidator.validateNewsDto(dtoRequest);
    if (newsRepository.isExistById(dtoRequest.id())) {
      NewsModel model = mapper.dtoToModel(dtoRequest);
      LocalDateTime date = LocalDateTime.now().truncatedTo(ChronoUnit.SECONDS);
      model.setLastUpdatedDate(date);
      NewsModel newsModel = newsRepository.update(model);
      return mapper.modelToDto(newsModel);
    } else {
      throw new NotFoundException(
          String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), dtoRequest.id()));
    }
  }

  @Override
  public Boolean deleteById(Long newsId) {
    newsValidator.validateNewsId(newsId);
    if (newsRepository.isExistById(newsId)) {
      return newsRepository.deleteById(newsId);
    } else {
      throw new NotFoundException(String.format(NEWS_ID_DOES_NOT_EXIST.getMessage(), newsId));
    }
  }
}
