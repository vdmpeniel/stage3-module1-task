package com.mjc.school.common.utils;

import com.mjc.school.common.implementation.ModelValidator;
import com.mjc.school.repository.model.NewsModel;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModelValidatorTest {
    private NewsModel newsModel;
    private ModelValidator validator;

    @BeforeEach
    void setUp() throws Exception{
        newsModel = NewsModel.builder()
                .title(".")
                .content(".")
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now().plusMinutes(5))
                .authorId(1L)
                .build();

        validator = ModelValidator.getInstance();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void validate() {
        Set<ConstraintViolation<NewsModel>> violations = validator.validate(newsModel);
        assertNotEquals(0, violations.size());
    }

    @Test
    void createViolationMap() {
        Set<ConstraintViolation<NewsModel>> violations = validator.validate(newsModel);
        boolean hasFailingField = validator.createViolationMap(violations)
            .containsKey("content");
        assertTrue(hasFailingField);
    }

    @Test
    void createValidationErrorList() {
        Set<ConstraintViolation<NewsModel>> violations = validator.validate(newsModel);
        assertNotEquals(
                0,
                validator.createValidationErrorList(validator.createViolationMap(violations)).size()
        );
    }
}