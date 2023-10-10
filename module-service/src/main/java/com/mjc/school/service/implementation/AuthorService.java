package com.mjc.school.service.implementation;

import com.mjc.school.repository.factory.RepositoryFactory;
import com.mjc.school.repository.interfaces.ModelInterface;
import com.mjc.school.repository.interfaces.RepositoryInterface;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.interfaces.AuthorMapperInterface;
import com.mjc.school.service.interfaces.ServiceInterface;
import com.mjc.school.service.validator.ModelValidator;

import java.util.List;

public class AuthorService implements ServiceInterface<AuthorDto> {
    private final RepositoryInterface<ModelInterface> authorRepository;
    private final ModelValidator modelValidator;

    public AuthorService(){
        authorRepository = RepositoryFactory.getInstance().getAuthorRepository();
        modelValidator = ModelValidator.getValidator();
    }

    public AuthorDto create(AuthorDto modelDto) throws Exception{
        modelValidator.runValidation(modelDto);
        return (AuthorDto) authorRepository.create(
                AuthorMapperInterface.INSTANCE.authorDtoToAuthor(modelDto)
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


    public AuthorDto updateById(AuthorDto modelDto) throws Exception{
        modelValidator.runValidation(modelDto);
        AuthorModel authorModel = AuthorMapperInterface.INSTANCE.authorDtoToAuthor(modelDto);
        authorModel.setId(modelDto.getId());
        return (AuthorDto) authorRepository.update(authorModel);
    }


    public Boolean deleteById(Long id ) throws Exception{
        return authorRepository.delete(Long.parseLong(id.toString()));
    }

}
