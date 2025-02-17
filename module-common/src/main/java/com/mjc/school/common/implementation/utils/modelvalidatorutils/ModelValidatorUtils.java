package com.mjc.school.common.implementation.utils.modelvalidatorutils;


import com.mjc.school.common.implementation.exceptions.IllegalFieldValueException;
import com.mjc.school.common.implementation.utils.PropertyLoader;
import com.mjc.school.common.implementation.utils.JsonUtils;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class ModelValidatorUtils {
    private static PropertyLoader propertyLoader;

    static {
        try {
            propertyLoader = PropertyLoader.getInstance();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private ModelValidatorUtils(){}

    public static <T> Set<ConstraintViolation<T>> validate(T obj) {
        Validator validator;
        //CustomValidatorFactory.createValidatorFactory()

        try (ValidatorFactory factory = Validation.buildDefaultValidatorFactory()) {
            validator = factory.getValidator();
        }
        return validator.validate(obj);
    }

    public static <T> Map<String, String> createViolationMap(Set<ConstraintViolation<T>> violations){
        return violations.stream()
            .collect(Collectors.toMap(
                violation -> violation.getPropertyPath().toString(),
                ConstraintViolation::getMessage
            ));
    }


    public static List<String> createValidationErrorList(Map<String, String> violationMap){
        return violationMap.entrySet().stream().
            map(entry -> "Error at field " + entry.getKey() + ": " + entry.getValue())
            .collect(Collectors.toList());
    }

    public static <T> List<Object> runValidation(T obj) throws Exception{
        Set<ConstraintViolation<T>> violations = validate(obj);
        if (!violations.isEmpty()) {
            List<Object> exceptionList = violations.stream()
                    .map(violation -> {
                        try {
                            String errorJson = propertyLoader.getProperty(violation.getMessage());
                            errorJson = errorJson.replace("{fieldValue}", violation.getInvalidValue().toString())
                                                 .replace("{fieldName}", violation.getPropertyPath().toString());
                            return JsonUtils.deserialize(errorJson, Object.class);

                        } catch(Exception e){
                            LinkedHashMap<String, String> errorMap = new LinkedHashMap<>();
                            errorMap.put("code", "0000100");
                            errorMap.put("priority", "-1");
                            errorMap.put("message", "Error running serialization: " + e.getMessage());
                            return errorMap;
                        }
                    })
                    .sorted(Comparator.comparing( errorMap -> Integer.parseInt( ((LinkedHashMap<String, String>) errorMap).get("priority"))))
                    .toList();

            LinkedHashMap<String, String> priorityError = (LinkedHashMap<String, String>) exceptionList.get(0);
            throw new IllegalFieldValueException(priorityError.get("message"), priorityError.get("code"));
        }
        return null;
    }
}