package com.mjc.school.service.mappers;

import com.mjc.school.repository.model.Author;
import com.mjc.school.service.dto.AuthorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorToAuthorDtoMapper {
    AuthorToAuthorDtoMapper INSTANCE = Mappers.getMapper( AuthorToAuthorDtoMapper.class );

    @Mapping( source = "content", target = "AuthorContent" )
    AuthorDto authorToAuthorDto(Author author );
}
