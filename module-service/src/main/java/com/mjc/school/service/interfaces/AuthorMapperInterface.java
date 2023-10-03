package com.mjc.school.service.interfaces;

import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AuthorMapperInterface {
    AuthorMapperInterface INSTANCE = Mappers.getMapper( AuthorMapperInterface.class );

    @Mapping( source = "fullName", target = "name" )
    @Mapping(target = "id", constant = "-1L")
    AuthorModel authorDtoToAuthor(AuthorDto authorDto);

    @Mapping( source = "name", target = "fullName" )
    AuthorDto authorToAuthorDto(AuthorModel authorModel);
}
