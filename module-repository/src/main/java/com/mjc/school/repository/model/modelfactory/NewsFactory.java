package com.mjc.school.repository.model.modelfactory;

import com.mjc.school.repository.model.News;

import java.time.LocalDateTime;

public class NewsFactory implements ModelFactory{
    public News create(){
        return new News();
    }
    public News create(String title, String content, LocalDateTime createDate, LocalDateTime lastUpdateDate, Long authorId){
        return new News(title, content, createDate, lastUpdateDate, authorId);
    }
    public News mapperCreate(String title, String content, LocalDateTime createDate, LocalDateTime lastUpdateDate, Long authorId){
        return new News(-1L, title, content, createDate, lastUpdateDate, authorId);
    }
}
