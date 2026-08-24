package com.aadi.foodie.utils;

public @interface ValidateGender {
    String message() default "Invalid Gender";
    Class<?>[] groups() default {};
//    Class<? extends Payload>[] payload() default{};
}
