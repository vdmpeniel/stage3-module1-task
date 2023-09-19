package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.NewsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface NewsMapper {
    NewsMapper INSTANCE = Mappers.getMapper( NewsMapper.class );

    @Mapping( source = "newsContent", target = "content" )
    @Mapping(target = "id", constant = "-1L")
    @Mapping(target = "lastUpdateDate", expression = "java(newsDto.getLastUpdateDate())")
    @Mapping(target = "createDate", expression = "java(newsDto.getCreateDate())")
    News newsDtoToNews(NewsDto newsDto );

    @Mapping( source = "content", target = "newsContent" )
    @Mapping(target = "lastUpdateDate", expression = "java(news.getLastUpdateDate())")
    @Mapping(target = "createDate", expression = "java(news.getCreateDate())")
    NewsDto newsToNewsDto( News news );
}

