package com.mjc.school.service.implementation;

import com.mjc.school.common.implementation.utils.modelvalidatorutils.ModelValidatorUtils;
import com.mjc.school.repository.implementation.AuthorDao;
import com.mjc.school.repository.interfaces.ModelDaoInterface;
import com.mjc.school.repository.implementation.Author;
import com.mjc.school.repository.interfaces.ModelInterface;
import com.mjc.school.service.interfaces.AuthorMapperInterface;
import com.mjc.school.service.interfaces.ServiceInterface;
import com.mjc.school.service.interfaces.ModelDtoInterface;

import java.util.List;
import java.util.Objects;

public class AuthorService implements ServiceInterface {
    ModelDaoInterface authorDao = new AuthorDao();

    public AuthorService() throws Exception{}


    @Override
    public ResponseDto create(RequestDto requestDto) {
        try {
            ModelValidatorUtils.runValidation(requestDto.getInputData());

            ModelInterface author = AuthorMapperInterface.INSTANCE.authorDtoToAuthor((AuthorDto) requestDto.getInputData());
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
                            .map(model -> AuthorMapperInterface.INSTANCE.authorToAuthorDto((Author) model))
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
                            List.of(AuthorMapperInterface.INSTANCE.authorToAuthorDto(
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
        return AuthorMapperInterface.INSTANCE.authorToAuthorDto((Author) authorDao.findById(id));
    }


    public AuthorDto updateById(Long id, AuthorDto authorDto) throws Exception {
        ModelValidatorUtils.runValidation(authorDto);

        Author author = AuthorMapperInterface.INSTANCE.authorDtoToAuthor(authorDto);
        authorDao.update(id, author);
        return AuthorMapperInterface.INSTANCE.authorToAuthorDto((Author) authorDao.findById(id));
    }
    public boolean removeById(Long id) throws Exception {
        return authorDao.delete(id);
    }
}
