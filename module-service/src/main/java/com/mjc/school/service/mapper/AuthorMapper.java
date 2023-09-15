package com.mjc.school.service.mapper;

import com.mjc.school.repository.model.Author;
import com.mjc.school.service.dto.AuthorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper( AuthorMapper.class );

    @Mapping( source = "fullName", target = "name" )
    Author authorDtoToAuthor( AuthorDto authorDto );

    @Mapping( source = "name", target = "fullName" )
    AuthorDto authorToAuthorDto( Author author );
}
