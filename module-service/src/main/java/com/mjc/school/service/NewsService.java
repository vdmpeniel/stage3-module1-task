package com.mjc.school.service;

import com.mjc.school.common.exceptions.IllegalFieldValueException;
import com.mjc.school.common.utils.JsonUtils;
import com.mjc.school.common.utils.PropertyLoader;
import com.mjc.school.common.utils.modelvalidatorutils.ModelValidatorUtils;
import com.mjc.school.repository.dao.ModelDaoInterface;
import com.mjc.school.repository.dao.NewsDao;
import com.mjc.school.repository.model.ModelInterface;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.*;
import com.mjc.school.service.mapper.NewsMapper;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

public class NewsService implements ServiceInterface{
    ModelDaoInterface newsDao;

    public NewsService(){
        try {
            newsDao = new NewsDao();

        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void setModelId(ModelInterface news, Long id) throws UnsupportedOperationException{
        if (id.equals(-1L)) { news.setId(id); }
        else { throw new UnsupportedOperationException("The id of this record can't be altered."); }
    }

    @Override
    public ResponseDto create(RequestDto requestDto){
        try {
            ModelValidatorUtils.runValidation(requestDto.getInputData());

            News news = NewsMapper.INSTANCE.newsDtoToNews((NewsDto) requestDto.getInputData());
            news.setCreateDate(LocalDateTime.now());
            news.setLastUpdateDate(news.getCreateDate());
            newsDao.create(news);

            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                    getById(
                        RequestDto.builder().lookupId(
                            news.getId()
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
                        newsDao.getAll()
                                .stream()
                                .map(model -> NewsMapper.INSTANCE.newsToNewsDto((News) model))
                                .map(model -> (ModelDtoInterface) model)
                                .toList())
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
                    List.of(NewsMapper.INSTANCE.newsToNewsDto(
                        (News) newsDao.findById(requestDto.getLookupId())
                    ))
                )
                .build();

            if(Objects.isNull(((NewsDto) responseDto.getResultSet().get(0)).getId())){
                throw new Exception(String.format("News with id %s does not exist.", requestDto.getLookupId()));
            }
            return responseDto;

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    @Override
    public ResponseDto updateById(RequestDto requestDto) {
        try{
            ModelValidatorUtils.runValidation(requestDto.getInputData());

            News news = NewsMapper.INSTANCE.newsDtoToNews((NewsDto) requestDto.getInputData());
            news.setLastUpdateDate(LocalDateTime.now());
            newsDao.update(requestDto.getLookupId(), news);
            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                    List.of(NewsMapper.INSTANCE.newsToNewsDto((News) newsDao.findById(requestDto.getLookupId())))
                )
                .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    @Override
    public ResponseDto removeById(RequestDto requestDto ) {
        try{
            newsDao.delete(requestDto.getLookupId());
            return ResponseDto.builder()
                    .status("OK")
                    .resultSet(null)
                    .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    private void validateInput(RequestDto requestDto)throws Exception{
        if(Objects.nonNull(requestDto.getLookupId())){
            if(requestDto.getLookupId() < 0){
                PropertyLoader propertyLoader = PropertyLoader.getInstance();
                String json = propertyLoader.getProperty("validation.error.news.newsid.min");
                json = json.replace("{fieldValue}", requestDto.getLookupId().toString());
                LinkedHashMap<String, String> errorMap = (LinkedHashMap<String, String>) JsonUtils.deserialize(json, Object.class);
                throw new IllegalFieldValueException(errorMap.get("message"), errorMap.get("code"));
            }
        }
        if(Objects.nonNull(requestDto.getInputData())) {
            ModelValidatorUtils.runValidation(requestDto.getInputData());
        }

    }

    @Override
    public ResponseDto buildErrorResponse(Exception e) {
        if (e instanceof IllegalFieldValueException) {
            IllegalFieldValueException ifve = (IllegalFieldValueException) e;
            return ResponseDto.builder()
                .status("Failed")
                .error(
                        ErrorDto.builder().code(ifve.getErrorCode()).message(ifve.getMessage()).build()
                )
                .build();

        } else {
            return ResponseDto.builder()
                .status("Failed")
                .error(
                        ErrorDto.builder().code("0000123").message(e.getMessage()).build()
                )
                .build();
        }
    }
}
