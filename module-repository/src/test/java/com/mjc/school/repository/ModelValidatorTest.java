package com.mjc.school.repository;

import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;
import static org.junit.jupiter.api.Assertions.*;

class ModelValidatorTest {
    private News news;

    @BeforeEach
    void setUp() {
        news = new News(
            ".",
            ".",
            LocalDateTime.now(),
            LocalDateTime.now().plusMinutes(5),
            1L
        );
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void validateNews() {
        Set<ConstraintViolation<News>> violations = ModelValidator.validateNews(news);
        assertNotEquals(0, violations.size());
    }

    @Test
    void createViolationMap() {
        Set<ConstraintViolation<News>> violations = ModelValidator.validateNews(news);
        boolean hasFailingField = ModelValidator.createViolationMap(violations)
            .containsKey("content");
        assertTrue(hasFailingField);
    }

    @Test
    void createValidationErrorList() {
        Set<ConstraintViolation<News>> violations = ModelValidator.validateNews(news);
        assertNotEquals(
                0,
                ModelValidator.createValidationErrorList(ModelValidator.createViolationMap(violations)).size()
        );
    }
}