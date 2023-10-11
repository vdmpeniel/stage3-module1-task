package com.mjc.school.service.interfaces;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;

@Mapper
public interface ModelMapper {

  List<NewsDtoResponse> modelListToDtoList(List<NewsModel> newsModelList);

  NewsDtoResponse modelToDto(NewsModel newsModel);

  @Mappings({
    @Mapping(target = "createDate", ignore = true),
    @Mapping(target = "lastUpdatedDate", ignore = true)
  })
  NewsModel dtoToModel(NewsDtoRequest newsModelRequest);
}
