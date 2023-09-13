package com.mjc.school.repository.model;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.Set;

public class NewsValidator {
    static{
        System.setProperty("javax.validation.Validation.messageInterpolator", "org.hibernate.validator.messageinterpolation.ParameterMessageInterpolator");
    }
    public static Set<ConstraintViolation<News>> validateNews(News news) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(news);
    }
}