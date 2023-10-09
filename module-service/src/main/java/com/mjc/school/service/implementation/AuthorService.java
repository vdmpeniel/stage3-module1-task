package com.mjc.school.service.implementation;

import com.mjc.school.common.implementation.ModelValidator;
import com.mjc.school.repository.factory.RepositoryFactory;
import com.mjc.school.repository.interfaces.ModelInterface;
import com.mjc.school.repository.interfaces.RepositoryInterface;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.interfaces.AuthorMapperInterface;
import com.mjc.school.service.interfaces.ServiceInterface;

import java.util.List;

public class AuthorService implements ServiceInterface<AuthorDto> {
    private final RepositoryInterface<ModelInterface> authorRepository;
    private final ModelValidator validator;

    public AuthorService() throws Exception{
        authorRepository = RepositoryFactory.getInstance().getAuthorRepository();
        validator = ModelValidator.getInstance();
    }

    public AuthorDto create(AuthorDto model) throws Exception{
        validator.runValidation(model);
        return (AuthorDto) authorRepository.create(
                AuthorMapperInterface.INSTANCE.authorDtoToAuthor(model)
        );
    }


    public List<AuthorDto> readAll() throws Exception{
        return authorRepository.readAll()
                .stream()
                .map(model -> AuthorMapperInterface.INSTANCE.authorToAuthorDto((AuthorModel) model))
                .toList();
    }


    public AuthorDto readById(Long id) throws Exception{
        return AuthorMapperInterface.INSTANCE.authorToAuthorDto(
                (AuthorModel) authorRepository.readById(Long.parseLong(id.toString()))
        );
    }


    public AuthorDto updateById(AuthorDto authorDto) throws Exception{
        validator.runValidation(authorDto);
        AuthorModel authorModel = AuthorMapperInterface.INSTANCE.authorDtoToAuthor(authorDto);
        authorModel.setId(authorDto.getId());
        return (AuthorDto) authorRepository.update(authorModel);
    }


    public Boolean deleteById(Long id ) throws Exception{
        return authorRepository.delete(Long.parseLong(id.toString()));
    }

}
