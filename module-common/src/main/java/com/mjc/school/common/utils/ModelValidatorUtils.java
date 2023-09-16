package com.mjc.school.common.utils;


import com.mjc.school.common.exceptions.IllegalFieldValueException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class ModelValidatorUtils {
    private static PropertyLoader propertyLoader = PropertyLoader.getInstance();
    private ModelValidatorUtils(){}

    public static <T> Set<ConstraintViolation<T>> validate(T obj) {
        Validator validator;
        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        return validator.validate(obj);
    }

    public static <T> Map<String, String> createViolationMap(Set<ConstraintViolation<T>> violations){
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

    public static <T> void validateAndThrow(T obj) throws Exception{
        Set<ConstraintViolation<T>> violations = validate(obj);
        if (!violations.isEmpty()) {
            throw new IllegalFieldValueException(
                "An IllegalFieldValue error occurred: " + JsonUtils.serialize(createViolationMap(violations)),
                10001
            );
        }
    }
}