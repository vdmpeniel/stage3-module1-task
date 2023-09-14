package com.mjc.school.repository;

import com.mjc.school.common.exceptions.IllegalFieldValueException;
import com.mjc.school.common.utils.ModelValidatorUtils;
import com.mjc.school.repository.model.News;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class NewsConstraintsTest {
    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setTittleConstraint() {
        assertThrows(IllegalFieldValueException.class, () -> {
            News news = new News();
            news.setTitle(".");
            ModelValidatorUtils.validateAndThrow(news);
        });
    }

    @Test
    void setContentConstraint() {
        assertThrows(IllegalFieldValueException.class, () -> {
            News news = new News();
            news.setContent(".");
            ModelValidatorUtils.validateAndThrow(news);
        });
    }

    @Test
    void setLastUpdateDateConstraint(){
        assertThrows(IllegalFieldValueException.class, () -> {
            News news = new News();
            news.setLastUpdateDate(null);
            ModelValidatorUtils.validateAndThrow(news);
        });
    }
}
