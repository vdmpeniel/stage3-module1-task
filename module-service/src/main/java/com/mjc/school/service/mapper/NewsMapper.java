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
    News newsDtoToNews(NewsDto newsDto );

    @Mapping( source = "content", target = "newsContent" )
    NewsDto newsToNewsDto( News news );
}

