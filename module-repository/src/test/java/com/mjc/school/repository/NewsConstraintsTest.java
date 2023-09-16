package com.mjc.school.repository;

import com.mjc.school.common.exceptions.IllegalFieldValueException;
import com.mjc.school.common.utils.ModelValidatorUtils;
import com.mjc.school.repository.model.News;
import com.mjc.school.repository.model.modelfactory.ModelFactory;
import com.mjc.school.repository.model.modelfactory.NewsFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class NewsConstraintsTest {
    ModelFactory newsFactory = new NewsFactory();

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setTittleConstraint() {
        assertThrows(IllegalFieldValueException.class, () -> {
            News news = newsFactory.create();
            news.setTitle(".");
            ModelValidatorUtils.validateAndThrow(news);
        });
    }

    @Test
    void setContentConstraint() {
        assertThrows(IllegalFieldValueException.class, () -> {
            News news = newsFactory.create();
            news.setContent(".");
            ModelValidatorUtils.validateAndThrow(news);
        });
    }

    @Test
    void setLastUpdateDateConstraint(){
        assertThrows(IllegalFieldValueException.class, () -> {
            News news = newsFactory.create();
                news.setContent(".");
                news.setLastUpdateDate(LocalDateTime.now());

            ModelValidatorUtils.validateAndThrow(news);
        });
    }
}
