package com.mjc.school.service.validator;

import com.mjc.school.repository.dao.AuthorDao;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class ExistValidator implements ConstraintValidator<Exist, Long> {
    AuthorDao authorDao;
    @Override
    public void initialize(Exist constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        try { authorDao = new AuthorDao(); }
        catch(Exception e){
            System.out.println(e.getMessage());
        }
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        String fieldName = context.getDefaultConstraintMessageTemplate();
        String message = context.getDefaultConstraintMessageTemplate()
                .replace("{fieldName}", fieldName)
                .replace("{fieldValue}", id.toString());

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();

        try {
            return id >= 0 && Objects.nonNull(authorDao.findById(id));

        } catch(Exception e){
            return false;
        }
    }

}