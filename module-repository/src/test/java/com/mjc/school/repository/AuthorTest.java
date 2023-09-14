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
        String name = "John Smith";
        Author author = new Author(name);
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