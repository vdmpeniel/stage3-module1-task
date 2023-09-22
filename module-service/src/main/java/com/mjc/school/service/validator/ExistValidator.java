package com.mjc.school.service.validator;

import com.mjc.school.repository.dao.AuthorDao;
import com.mjc.school.repository.dao.ModelDaoInterface;
import com.mjc.school.repository.dao.NewsDao;
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
            return longId >= 0 && Objects.nonNull(modelDao.findById(longId));

        } catch(Exception e){
            return false;
        }
    }

}
