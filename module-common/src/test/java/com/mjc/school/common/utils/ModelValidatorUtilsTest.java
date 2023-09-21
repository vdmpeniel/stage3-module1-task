package com.mjc.school.common.utils;

import com.mjc.school.common.utils.modelvalidatorutils.ModelValidatorUtils;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.modelfactory.ModelFactory;
import com.mjc.school.repository.model.modelfactory.NewsFactory;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ModelValidatorUtilsTest {
    private News news;
    private final ModelFactory newsFactory = new NewsFactory();

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
    void validate() {
        Set<ConstraintViolation<News>> violations = ModelValidatorUtils.validate(news);
        assertNotEquals(0, violations.size());
    }

    @Test
    void createViolationMap() {
        Set<ConstraintViolation<News>> violations = ModelValidatorUtils.validate(news);
        boolean hasFailingField = ModelValidatorUtils.createViolationMap(violations)
            .containsKey("content");
        assertTrue(hasFailingField);
    }

    @Test
    void createValidationErrorList() {
        Set<ConstraintViolation<News>> violations = ModelValidatorUtils.validate(news);
        assertNotEquals(
                0,
                ModelValidatorUtils.createValidationErrorList(ModelValidatorUtils.createViolationMap(violations)).size()
        );
    }
}