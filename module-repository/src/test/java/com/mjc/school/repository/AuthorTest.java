package com.mjc.school.repository;

import com.mjc.school.repository.model.Author;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setId() {
        Author author = new Author();
        author.setId(1L);
        assertEquals(1L, author.getId());
    }

    @Test
    void getId() {
        Author author = new Author();
        author.setId(1L);
        assertEquals(1L, author.getId());
    }

    @Test
    void getName() {
        String name = "John Smith";
        Author author = Author.builder()
                .name(name)
                .build();
        assertEquals(name, author.getName());
    }

    @Test
    void setName() {
        String name = "John Smith";
        Author author = new Author();
        author.setName(name);
        assertEquals(name, author.getName());
    }
}