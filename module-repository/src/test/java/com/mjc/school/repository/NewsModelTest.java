package com.mjc.school.repository;

import com.mjc.school.repository.implementation.NewsModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NewsModelTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setId() {
        NewsModel newsModel = new NewsModel();
        newsModel.setId(1L);
        assertEquals(1L, newsModel.getId());
    }

    @Test
    void getId() {
        NewsModel newsModel = NewsModel.builder()
            .id(1L)
            .title("Some Title")
            .content("Some Content")
            .createDate(LocalDateTime.now())
            .lastUpdateDate(LocalDateTime.now().plusMinutes(5))
            .authorId(1L)
            .build();
        assertEquals(1L, newsModel.getId());
    }


    @Test
    void getTittle() {
        String tittle = "hello there!";
        NewsModel newsModel = new NewsModel();
        newsModel.setTitle(tittle);
        assertEquals(tittle, newsModel.getTitle());
    }

    @Test
    void getContent() {
        String content = "hello there! - Some Content there.";
        NewsModel newsModel = new NewsModel();
        newsModel.setContent(content);
        assertEquals(content, newsModel.getContent());
    }

    @Test
    void getCreateDate() {
        LocalDateTime now = LocalDateTime.now();
        NewsModel newsModel = new NewsModel();
        newsModel.setCreateDate(now);
        assertEquals(now, newsModel.getCreateDate());
    }

    @Test
    void getLastUpdateDate() {
        LocalDateTime now = LocalDateTime.now();
        NewsModel newsModel = new NewsModel();
        newsModel.setLastUpdateDate(now);
        assertEquals(now, newsModel.getLastUpdateDate());
    }

    @Test
    void getAuthorId() {
        Long authorId = 1_000L;
        NewsModel newsModel = new NewsModel();
        newsModel.setAuthorId(authorId);
        assertEquals(authorId, newsModel.getAuthorId());
    }

    @Test
    void setTittle() {
        String tittle = "hello there!";
        NewsModel newsModel = new NewsModel();
        newsModel.setTitle(tittle);
    }

    @Test
    void setContent() {
        String content = "hello there! - Some Content there.";
        NewsModel newsModel = new NewsModel();
        newsModel.setContent(content);
    }

    @Test
    void setCreateDate() {
        LocalDateTime now = LocalDateTime.now();
        NewsModel newsModel = new NewsModel();
        newsModel.setCreateDate(now);
    }

    @Test
    void setLastUpdateDate() {
        LocalDateTime now = LocalDateTime.now();
        NewsModel newsModel = new NewsModel();
        newsModel.setLastUpdateDate(now);
    }

    @Test
    void setAuthorId() {
        Long authorId = 1_000L;
        NewsModel newsModel = new NewsModel();
        newsModel.setAuthorId(authorId);
    }
}