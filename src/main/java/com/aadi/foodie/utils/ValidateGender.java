package com.aadi.foodie.utils;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {GenderValidator.class})
public @interface ValidateGender {

    String message() default "Invalid Gender";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}