package com.mjc.school.repository;

import com.mjc.school.repository.implementation.AuthorModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AuthorModelTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setId() {
        AuthorModel authorModel = new AuthorModel();
        authorModel.setId(1L);
        assertEquals(1L, authorModel.getId());
    }

    @Test
    void getId() {
        AuthorModel authorModel = new AuthorModel();
        authorModel.setId(1L);
        assertEquals(1L, authorModel.getId());
    }

    @Test
    void getName() {
        String name = "John Smith";
        AuthorModel authorModel = AuthorModel.builder()
                .name(name)
                .build();
        assertEquals(name, authorModel.getName());
    }

    @Test
    void setName() {
        String name = "John Smith";
        AuthorModel authorModel = new AuthorModel();
        authorModel.setName(name);
        assertEquals(name, authorModel.getName());
    }
}