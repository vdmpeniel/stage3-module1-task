package com.mjc.school.repository;

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
        assertThrows(UnsupportedOperationException.class, () -> {
            Author author = new Author();
            author.setId(1L);
        });
    }

    @Test
    void getId() {
        Author author = new Author();
        assertNotNull(author.getId());
    }

    @Test
    void getName() {

        Author author = new Author("John Smith");

    }

    @Test
    void setName() {
    }
}