package com.mjc.school.repository;

import com.mjc.school.repository.model.News;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NewsTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setId() {
        assertThrows(UnsupportedOperationException.class, () -> {
            News news = new News();
            news.setId(1L);
        });
    }

    @Test
    void getId() {
        News news = new News(
            "Some Title",
            "Some Content",
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(5),
            1L
        );
        assertNotNull(news.getId());
    }


    @Test
    void getTittle() {
        String tittle = "hello there!";
        News news = new News();
        news.setTitle(tittle);
        assertEquals(tittle, news.getTitle());
    }

    @Test
    void getContent() {
        String content = "hello there! - Some Content there.";
        News news = new News();
        news.setContent(content);
        assertEquals(content, news.getContent());
    }

    @Test
    void getCreateDate() {
        LocalDateTime now = LocalDateTime.now();
        News news = new News();
        news.setCreateDate(now);
        assertEquals(now, news.getCreateDate());
    }

    @Test
    void getLastUpdateDate() {
        LocalDateTime now = LocalDateTime.now();
        News news = new News();
        news.setLastUpdateDate(now);
        assertEquals(now, news.getLastUpdateDate());
    }

    @Test
    void getAuthorId() {
        Long authorId = 1_000L;
        News news = new News();
        news.setAuthorId(authorId);
        assertEquals(authorId, news.getAuthorId());
    }

    @Test
    void setTittle() {
        String tittle = "hello there!";
        News news = new News();
        news.setTitle(tittle);
    }

    @Test
    void setContent() {
        String content = "hello there! - Some Content there.";
        News news = new News();
        news.setContent(content);
    }

    @Test
    void setCreateDate() {
        LocalDateTime now = LocalDateTime.now();
        News news = new News();
        news.setCreateDate(now);
    }

    @Test
    void setLastUpdateDate() {
        LocalDateTime now = LocalDateTime.now();
        News news = new News();
        news.setLastUpdateDate(now);
    }

    @Test
    void setAuthorId() {
        Long authorId = 1_000L;
        News news = new News();
        news.setAuthorId(authorId);
    }
}