package com.mjc.school.service.annotations;

import com.mjc.school.service.implementation.ExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ExistValidator.class)
public @interface Exist {
    String message() default "Invalid Id";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}