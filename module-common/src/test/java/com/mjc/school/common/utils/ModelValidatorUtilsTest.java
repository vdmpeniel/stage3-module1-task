package com.mjc.school.common.utils;

import com.mjc.school.common.implementation.utils.modelvalidatorutils.ModelValidatorUtils;
import com.mjc.school.repository.implementation.NewsModel;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModelValidatorUtilsTest {
    private NewsModel newsModel;

    @BeforeEach
    void setUp() {
        newsModel = NewsModel.builder()
                .title(".")
                .content(".")
                .createDate(LocalDateTime.now())
                .lastUpdateDate(LocalDateTime.now().plusMinutes(5))
                .authorId(1L)
                .build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void validate() {
        Set<ConstraintViolation<NewsModel>> violations = ModelValidatorUtils.validate(newsModel);
        assertNotEquals(0, violations.size());
    }

    @Test
    void createViolationMap() {
        Set<ConstraintViolation<NewsModel>> violations = ModelValidatorUtils.validate(newsModel);
        boolean hasFailingField = ModelValidatorUtils.createViolationMap(violations)
            .containsKey("content");
        assertTrue(hasFailingField);
    }

    @Test
    void createValidationErrorList() {
        Set<ConstraintViolation<NewsModel>> violations = ModelValidatorUtils.validate(newsModel);
        assertNotEquals(
                0,
                ModelValidatorUtils.createValidationErrorList(ModelValidatorUtils.createViolationMap(violations)).size()
        );
    }
}