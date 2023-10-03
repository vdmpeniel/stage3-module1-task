package com.mjc.school.service.validator;

import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.interfaces.ModelInterface;
import com.mjc.school.repository.interfaces.RepositoryInterface;
import com.mjc.school.repository.implementation.NewsRepository;
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
            RepositoryInterface<ModelInterface> modelRepository = message.contains("author")? (new AuthorRepository()): (new NewsRepository());
            long longId = Long.parseLong(id);
            return longId >= 0 && Objects.nonNull(modelRepository.readById(longId));

        } catch(Exception e){
            return false;
        }
    }

}
