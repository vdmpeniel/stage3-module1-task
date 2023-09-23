package com.mjc.school.service;

import com.mjc.school.common.utils.modelvalidatorutils.ModelValidatorUtils;
import com.mjc.school.repository.dao.AuthorDao;
import com.mjc.school.repository.dao.ModelDaoInterface;
import com.mjc.school.repository.model.Author;
import com.mjc.school.repository.model.ModelInterface;
import com.mjc.school.service.dto.AuthorDto;
import com.mjc.school.service.dto.ModelDtoInterface;
import com.mjc.school.service.dto.RequestDto;
import com.mjc.school.service.dto.ResponseDto;
import com.mjc.school.service.mapper.AuthorMapper;

import java.util.List;
import java.util.Objects;

public class AuthorService implements ServiceInterface{
    ModelDaoInterface authorDao = new AuthorDao();

    public AuthorService() throws Exception{}


    @Override
    public ResponseDto create(RequestDto requestDto) {
        try {
            ModelValidatorUtils.runValidation(requestDto.getInputData());

            ModelInterface author = AuthorMapper.INSTANCE.authorDtoToAuthor((AuthorDto) requestDto.getInputData());
            authorDao.create((Author) author);
            return ResponseDto
                    .builder()
                    .status("OK")
                    .resultSet(
                            getById(
                                    RequestDto.builder().lookupId(
                                            author.getId().toString()
                                    ).build()
                            ).getResultSet()
                    )
                    .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }


    @Override
    public ResponseDto getAll() {
        try{
            return ResponseDto
                    .builder()
                    .status("OK")
                    .resultSet(
                        authorDao.getAll()
                            .stream()
                            .map(model -> AuthorMapper.INSTANCE.authorToAuthorDto((Author) model))
                            .map(model -> (ModelDtoInterface) model)
                            .toList()
                    )
                    .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    @Override
    public ResponseDto getById(RequestDto requestDto) {
        try{
            ResponseDto responseDto = ResponseDto
                    .builder()
                    .status("OK")
                    .resultSet(
                            List.of(AuthorMapper.INSTANCE.authorToAuthorDto(
                                            (Author) authorDao.findById(Long.parseLong(requestDto.getLookupId()))
                            ))
                    )
                    .build();

            if(Objects.isNull(((AuthorDto) responseDto.getResultSet().get(0)).getId())){
                throw new Exception(String.format("News with id %s does not exist.", requestDto.getLookupId()));
            }
            return responseDto;

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    @Override
    public ResponseDto updateById(RequestDto requestDto) {
        return null;
    }

    @Override
    public ResponseDto removeById(RequestDto requestDto) {
        return null;
    }

    @Override
    public ResponseDto buildErrorResponse(Exception e) {
        return null;
    }

    public AuthorDto getById(Long id) throws Exception {
        return AuthorMapper.INSTANCE.authorToAuthorDto((Author) authorDao.findById(id));
    }


    public AuthorDto updateById(Long id, AuthorDto authorDto) throws Exception {
        ModelValidatorUtils.runValidation(authorDto);

        Author author = AuthorMapper.INSTANCE.authorDtoToAuthor(authorDto);
        authorDao.update(id, author);
        return AuthorMapper.INSTANCE.authorToAuthorDto((Author) authorDao.findById(id));
    }
    public boolean removeById(Long id) throws Exception {
        return authorDao.delete(id);
    }
}
