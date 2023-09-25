package com.mjc.school.service.implementation;

import com.mjc.school.repository.implementation.AuthorDao;
import com.mjc.school.repository.interfaces.ModelDaoInterface;
import com.mjc.school.repository.implementation.NewsDao;
import com.mjc.school.service.annotations.Exist;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.Objects;

public class ExistValidator implements ConstraintValidator<Exist, String> {

    @Override
    public void initialize(Exist constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }


    @Override
    public boolean isValid(String id, ConstraintValidatorContext context) {
        context.disableDefaultConstraintViolation();
        String fieldName = context.getDefaultConstraintMessageTemplate();
        String message = context.getDefaultConstraintMessageTemplate();

        context.disableDefaultConstraintViolation();
        context.buildConstraintViolationWithTemplate(message)
                .addConstraintViolation();

        try {
            ModelDaoInterface modelDao = modelDao = message.contains("author")? (new AuthorDao()): (new NewsDao());
            long longId = Long.parseLong(id);
            return longId >= 0 && Objects.nonNull(modelDao.readById(longId));

        } catch(Exception e){
            return false;
        }
    }

}
