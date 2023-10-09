package com.mjc.school.service.factory;

import com.mjc.school.service.implementation.CustomMessageInterpolator;
import jakarta.validation.Validation;
import jakarta.validation.ValidatorFactory;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;

public class CustomValidatorFactory {
    public static ValidatorFactory createValidatorFactory() {
        HibernateValidatorConfiguration configuration = Validation
                .byProvider(HibernateValidator.class)
                .configure();

        configuration.messageInterpolator(new CustomMessageInterpolator());
        return configuration.buildValidatorFactory();
    }
}
