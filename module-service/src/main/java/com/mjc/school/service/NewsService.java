package com.mjc.school.service;

import com.mjc.school.common.utils.ModelValidatorUtils;
import com.mjc.school.repository.dao.NewsDao;
import com.mjc.school.repository.model.News;
import com.mjc.school.service.dto.ErrorDto;
import com.mjc.school.service.dto.NewsDto;
import com.mjc.school.service.dto.ResponseDto;
import com.mjc.school.service.mapper.NewsMapper;

import java.time.LocalDateTime;
import java.util.List;

public class NewsService {
    NewsDao newsDao = new NewsDao();

    public NewsService() throws Exception{}

    public void resetId(News news, Long id) throws UnsupportedOperationException{
        if (id.equals(-1L)) { news.setId(id); }
        else { throw new UnsupportedOperationException("The id of this record can't be altered."); }
    }

    public ResponseDto createNews(NewsDto newsDto){
        try {
            ModelValidatorUtils.validateAndThrow(newsDto);

            News news = NewsMapper.INSTANCE.newsDtoToNews(newsDto);
            news.setCreateDate(LocalDateTime.now());
            news.setLastUpdateDate(news.getCreateDate());
            newsDao.create(news);
            return ResponseDto
                    .builder()
                    .status("OK")
                    .resultSet(getNewsById(news.getId()).getResultSet())
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

    public ResponseDto getNewsById(Long id) {
        try{
            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(List.of(NewsMapper.INSTANCE.newsToNewsDto(newsDao.findById(id))))
                .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }

    public ResponseDto updateNewsById(Long id, NewsDto newsDto) {
        try{
            //ModelValidatorUtils.validateAndThrow(newsDto);

            News news = NewsMapper.INSTANCE.newsDtoToNews(newsDto);
            newsDao.update(id, news);
            return ResponseDto
                .builder()
                .status("OK")
                .resultSet(List.of(NewsMapper.INSTANCE.newsToNewsDto(newsDao.findById(id))))
                .build();

        } catch(Exception e){
            return buildErrorResponse(e);
        }
    }
    public boolean removeNewsById( Long id ) {
        try{
            return newsDao.deleteById( id );

        } catch( Exception e ){
            return false;
        }
    }

    public ResponseDto buildErrorResponse( Exception e ){
        return ResponseDto.builder()
            .status( "Failed" )
            .errorList(
                List.of(ErrorDto.builder().code( "0002" ).message( e.getMessage()).build() )
            )
            .build();
    }
}
