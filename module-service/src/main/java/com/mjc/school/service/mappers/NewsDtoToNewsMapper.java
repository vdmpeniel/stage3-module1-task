package com.mjc.school.service.mappers;

import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.NewsDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NewsDtoToNewsMapper {
    NewsDtoToNewsMapper INSTANCE = Mappers.getMapper( NewsDtoToNewsMapper.class );

    @Mapping( source = "newsContent", target = "content" )
    News newsDtoToNews( NewsDto newsDto );
}


