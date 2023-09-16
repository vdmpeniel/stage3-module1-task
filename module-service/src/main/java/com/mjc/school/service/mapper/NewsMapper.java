package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.NewsDto;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


public interface NewsMapper {
    NewsMapper INSTANCE = Mappers.getMapper( NewsMapper.class );

    @Mapping( source = "newsContent", target = "content" )
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "idGenerator", ignore = true)
    News mapToNews(NewsDto newsDto );

    @Mapping( source = "content", target = "newsContent" )
    NewsDto mapToNewsDto(News news );
}

