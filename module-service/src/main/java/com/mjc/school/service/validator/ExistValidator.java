package com.mjc.school.service.validator;

import com.mjc.school.repository.dao.AuthorDao;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class ExistValidator implements ConstraintValidator<Exist, String> {
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
    public boolean isValid(String idString, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        String fieldName = context.getDefaultConstraintMessageTemplate();
        String message = context.getDefaultConstraintMessageTemplate()
                .replace("{fieldName}", fieldName)
                .replace("{fieldValue}", idString);

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();

        try {
            long id = Long.parseLong(idString);
            return id >= 0 && Objects.nonNull(authorDao.findById(id));

        } catch(Exception e){
            return false;
        }
    }

}