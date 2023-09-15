package com.mjc.school.service.mappers;

import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.NewsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewsToNewsDtoMapper {
    NewsToNewsDtoMapper INSTANCE = Mappers.getMapper( NewsToNewsDtoMapper.class );

    @Mapping( source = "content", target = "newsContent" )
    NewsDto newsToNewsDto( News news );
}

