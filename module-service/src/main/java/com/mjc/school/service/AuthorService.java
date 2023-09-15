package com.mjc.school.service;

import com.mjc.school.common.utils.ModelValidatorUtils;
import com.mjc.school.repository.dao.AuthorDao;
import com.mjc.school.repository.model.Author;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.mappers.AuthorDtoToAuthorMapper;
import com.mjc.school.service.mappers.AuthorToAuthorDtoMapper;

import java.util.List;

public class AuthorService {
    AuthorDao authorDao = new AuthorDao();

    public AuthorService() throws Exception{}

    public AuthorDto createAuthor(AuthorDto authorDto) throws Exception {
        ModelValidatorUtils.validateAndThrow(authorDto);

        Author author = AuthorDtoToAuthorMapper.INSTANCE.authorDtoToAuthor(authorDto);
        authorDao.create(author);
        return getAuthorById(author.getId());
    }

    public List<AuthorDto> getAllAuthor() throws Exception {
        return authorDao.getAll().stream().map(AuthorToAuthorDtoMapper.INSTANCE::authorToAuthorDto).toList();
    }

    public AuthorDto getAuthorById(Long id) throws Exception {
        return AuthorToAuthorDtoMapper.INSTANCE.authorToAuthorDto(authorDao.findById(id));
    }

    public AuthorDto updateAuthorById(Long id, AuthorDto authorDto) throws Exception {
        ModelValidatorUtils.validateAndThrow(authorDto);

        Author author = AuthorDtoToAuthorMapper.INSTANCE.authorDtoToAuthor(authorDto);
        authorDao.update(id, author);
        return AuthorToAuthorDtoMapper.INSTANCE.authorToAuthorDto(authorDao.findById(id));
    }
    public boolean removeAuthorById(Long id) throws Exception {
        return authorDao.deleteById(id);
    }
}
