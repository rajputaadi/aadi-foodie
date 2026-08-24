package com.aadi.foodie.utils;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.logging.Logger;

public class GenderValidator implements ConstraintValidator<ValidateGender, String> {
    private Logger logger = Logger.getLogger(GenderValidator.class.getName());

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null || value.isEmpty()) {
            logger.warning("Invalid gender string");
            return false;
        }
        if (value.toLowerCase().equals("male") || value.toLowerCase().equals("female")) {
            return true;
        }
        return false;
    }
}
