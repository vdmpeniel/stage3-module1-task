package com.mjc.school.repository;

import com.mjc.school.common.implementation.exceptions.IllegalFieldValueException;
import com.mjc.school.common.implementation.utils.modelvalidatorutils.ModelValidatorUtils;
import com.mjc.school.repository.model.NewsModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class NewsModelConstraintsTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setTittleConstraint() {
        assertThrows(IllegalFieldValueException.class, () -> {
            NewsModel newsModel = new NewsModel();
            newsModel.setTitle(".");
            ModelValidatorUtils.runValidation(newsModel);
        });
    }

    @Test
    void setContentConstraint() {
        assertThrows(IllegalFieldValueException.class, () -> {
            NewsModel newsModel = new NewsModel();
            newsModel.setContent(".");
            ModelValidatorUtils.runValidation(newsModel);
        });
    }

    @Test
    void setLastUpdateDateConstraint(){
        assertThrows(IllegalFieldValueException.class, () -> {
            NewsModel newsModel = new NewsModel();
                newsModel.setContent(".");
                newsModel.setLastUpdateDate(LocalDateTime.now());

            ModelValidatorUtils.runValidation(newsModel);
        });
    }
}
