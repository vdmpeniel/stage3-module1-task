package com.mjc.school.service.interfaces;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.implementation.NewsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface NewsMapperInterface {
    NewsMapperInterface INSTANCE = Mappers.getMapper( NewsMapperInterface.class );

    @Mapping( source = "newsContent", target = "content" )
    @Mapping(target = "id", constant = "-1L")
    @Mapping(target = "lastUpdateDate", expression = "java(newsDto.getLastUpdateDate())")
    @Mapping(target = "createDate", expression = "java(newsDto.getCreateDate())")
    NewsModel newsDtoToNews(NewsDto newsDto );

    @Mapping( source = "content", target = "newsContent" )
    @Mapping(target = "lastUpdateDate", expression = "java(newsModel.getLastUpdateDate())")
    @Mapping(target = "createDate", expression = "java(newsModel.getCreateDate())")
    NewsDto newsToNewsDto(NewsModel newsModel);
}

