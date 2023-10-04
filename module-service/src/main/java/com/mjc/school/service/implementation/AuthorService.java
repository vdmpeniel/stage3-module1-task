package com.mjc.school.service.implementation;

import com.mjc.school.common.implementation.utils.modelvalidatorutils.ModelValidatorUtils;
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

    public AuthorService(){
        authorRepository = RepositoryFactory.getInstance().getAuthorRepository();
    }

    public AuthorDto create(AuthorDto model) throws Exception{
        ModelValidatorUtils.runValidation(model);
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


    public AuthorDto updateById(Long id,  AuthorDto model) throws Exception{
        ModelValidatorUtils.runValidation(model);
        AuthorModel authorModel = AuthorMapperInterface.INSTANCE.authorDtoToAuthor(model);
        authorModel.setId(Long.parseLong(id.toString()));
        return (AuthorDto) authorRepository.update(authorModel);
    }


    public Boolean deleteById(Long id ) throws Exception{
        return authorRepository.delete(Long.parseLong(id.toString()));
    }

}
