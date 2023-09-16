package com.mjc.school.repository.model.modelfactory;

import com.mjc.school.repository.model.News;

import java.time.LocalDateTime;

public interface ModelFactory {
    News create();
    News create(String title, String content, LocalDateTime createDate, LocalDateTime lastUpdateDate, Long authorId);
    News mapperCreate(String title, String content, LocalDateTime createDate, LocalDateTime lastUpdateDate, Long authorId);
}
