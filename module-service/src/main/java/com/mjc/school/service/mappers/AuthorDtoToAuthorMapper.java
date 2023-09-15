package com.mjc.school.service.mappers;

import com.mjc.school.repository.model.Author;
import com.mjc.school.service.dto.AuthorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorDtoToAuthorMapper {
    AuthorDtoToAuthorMapper INSTANCE = Mappers.getMapper( AuthorDtoToAuthorMapper.class );

    @Mapping( source = "fullName", target = "name" )
    Author authorDtoToAuthor(AuthorDto authorDto);
}
