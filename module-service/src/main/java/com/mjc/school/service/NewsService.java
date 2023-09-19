package com.mjc.school.service;

import com.mjc.school.common.utils.ModelValidatorUtils;
import com.mjc.school.repository.dao.NewsDao;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.ErrorDto;
import com.mjc.school.service.dto.RequestDto;
import com.mjc.school.service.dto.ResponseDto;
import com.mjc.school.service.mapper.NewsMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

public class NewsService {
    NewsDao newsDao;

    public NewsService(){
        try {
            newsDao = new NewsDao();

        } catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    public void setModelId(News news, Long id) throws UnsupportedOperationException{
        if (id.equals(-1L)) { news.setId(id); }
        else { throw new UnsupportedOperationException("The id of this record can't be altered."); }
    }

    public ResponseDto createNews(RequestDto requestDto){
        try {
            ModelValidatorUtils.validateAndThrow(requestDto.getInputData());

            News news = NewsMapper.INSTANCE.newsDtoToNews(requestDto.getInputData());
            news.setCreateDate(LocalDateTime.now());
            news.setLastUpdateDate(news.getCreateDate());
            newsDao.create(news);

            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                    getNewsById(
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

    public ResponseDto getAllNews() {
        try{
            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(newsDao.getAll().stream().map(NewsMapper.INSTANCE::newsToNewsDto).toList())
                .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    public ResponseDto getNewsById(RequestDto requestDto) {
        try{
            ResponseDto responseDto = ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                    List.of(NewsMapper.INSTANCE.newsToNewsDto(
                        newsDao.findById(requestDto.getLookupId())
                    ))
                )
                .build();

            if(Objects.isNull(responseDto.getResultSet().get(0).getId())){
                throw new Exception(String.format("News with id %s does not exist.", requestDto.getLookupId()));
            }
            return responseDto;

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    public ResponseDto updateNewsById(RequestDto requestDto) {
        try{
            ModelValidatorUtils.validateAndThrow(requestDto.getInputData());

            News news = NewsMapper.INSTANCE.newsDtoToNews(requestDto.getInputData());
            news.setLastUpdateDate(LocalDateTime.now());
            newsDao.update(requestDto.getLookupId(), news);
            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(
                    List.of(NewsMapper.INSTANCE.newsToNewsDto(newsDao.findById(requestDto.getLookupId())))
                )
                .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    public ResponseDto removeNewsById( RequestDto requestDto ) {
        try{
            newsDao.deleteById(requestDto.getLookupId());
            return ResponseDto.builder()
                    .status("OK")
                    .resultSet(null)
                    .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    public ResponseDto buildErrorResponse( Exception e ){
        return ResponseDto.builder()
            .status("Failed")
            .errorList(
                List.of(ErrorDto.builder().code("0002").message(e.getMessage()).build() )
            )
            .build();
    }
}
