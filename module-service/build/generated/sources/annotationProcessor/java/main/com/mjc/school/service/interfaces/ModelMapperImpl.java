package com.mjc.school.service.interfaces;

import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-10T23:08:23-0400",
    comments = "version: 1.4.2.Final, compiler: IncrementalProcessingEnvironment from gradle-language-java-7.2.jar, environment: Java 17.0.8.1 (Amazon.com Inc.)"
)
public class ModelMapperImpl implements ModelMapper {

    @Override
    public List<NewsDtoResponse> modelListToDtoList(List<NewsModel> newsModelList) {
        if ( newsModelList == null ) {
            return null;
        }

        List<NewsDtoResponse> list = new ArrayList<NewsDtoResponse>( newsModelList.size() );
        for ( NewsModel newsModel : newsModelList ) {
            list.add( modelToDto( newsModel ) );
        }

        return list;
    }

    @Override
    public NewsDtoResponse modelToDto(NewsModel newsModel) {
        if ( newsModel == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String content = null;
        LocalDateTime createDate = null;
        LocalDateTime lastUpdatedDate = null;
        Long authorId = null;

        id = newsModel.getId();
        title = newsModel.getTitle();
        content = newsModel.getContent();
        createDate = newsModel.getCreateDate();
        lastUpdatedDate = newsModel.getLastUpdatedDate();
        authorId = newsModel.getAuthorId();

        NewsDtoResponse newsDtoResponse = new NewsDtoResponse( id, title, content, createDate, lastUpdatedDate, authorId );

        return newsDtoResponse;
    }

    @Override
    public NewsModel dtoToModel(NewsDtoRequest newsModelRequest) {
        if ( newsModelRequest == null ) {
            return null;
        }

        Long id = null;
        String title = null;
        String content = null;
        Long authorId = null;

        id = newsModelRequest.id();
        title = newsModelRequest.title();
        content = newsModelRequest.content();
        authorId = newsModelRequest.authorId();

        LocalDateTime createDate = null;
        LocalDateTime lastUpdatedDate = null;

        NewsModel newsModel = new NewsModel( id, title, content, createDate, lastUpdatedDate, authorId );

        return newsModel;
    }
}
