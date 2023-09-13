package com.mjc.school.repository;


import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ModelValidator {
    private ModelValidator(){}

    public static <T> Set<ConstraintViolation<T>> validateNews(T obj) {

        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        return validator.validate(obj);
    }

    public static Map<String, String> createViolationMap(Set<ConstraintViolation<News>> violations){
        return violations.stream()
            .collect(Collectors.toMap(
                    violation -> violation.getPropertyPath().toString(),
                    violation -> violation.getMessage()
            ));
    }

    // this could be in service layer
    public static List<String> createValidationErrorList(Map<String, String> violationMap){
        return violationMap.entrySet().stream().
            map(entry -> "Error at field " + entry.getKey() + ": " + entry.getValue())
            .collect(Collectors.toList());
    }
}