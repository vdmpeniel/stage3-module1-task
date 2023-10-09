package com.mjc.school.repository;

import com.mjc.school.common.exceptions.IllegalFieldValueException;
import com.mjc.school.common.implementation.ModelValidator;
import com.mjc.school.repository.model.NewsModel;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class NewsModelConstraintsTest {
    private ModelValidator validator;

    @BeforeEach
    void setUp() throws Exception{
        validator = ModelValidator.getInstance();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void setTittleConstraint() {
        assertThrows(IllegalFieldValueException.class, () -> {
            NewsModel newsModel = new NewsModel();
            newsModel.setTitle(".");
            validator.runValidation(newsModel);
        });
    }

    @Test
    void setContentConstraint() {
        assertThrows(IllegalFieldValueException.class, () -> {
            NewsModel newsModel = new NewsModel();
            newsModel.setContent(".");
            validator.runValidation(newsModel);
        });
    }

    @Test
    void setLastUpdateDateConstraint(){
        assertThrows(IllegalFieldValueException.class, () -> {
            NewsModel newsModel = new NewsModel();
                newsModel.setContent(".");
                newsModel.setLastUpdateDate(LocalDateTime.now());

            validator.runValidation(newsModel);
        });
    }
}
