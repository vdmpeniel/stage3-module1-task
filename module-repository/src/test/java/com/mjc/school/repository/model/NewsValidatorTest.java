package com.mjc.school.repository.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Path;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

class NewsValidatorTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void validateNews() {
        News news = new News(
                ".",
                ".",
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(5),
                1L
        );
        Set<ConstraintViolation<News>> violations = NewsValidator.validateNews(news);
        List<String> fields = violations.stream()
                .map(ConstraintViolation::getPropertyPath)
                .map(Path::toString)
                .filter(string ->List.of("tittle", "content")
                .contains(string))
                .toList();
        fields.forEach(System.out::println);

    }
}