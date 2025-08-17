package com.example.app.CustomValidations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;


@Documented
@Constraint(validatedBy = StrongPwdValidatorImp.class) // link validator
@Target({ ElementType.FIELD, ElementType.PARAMETER })   // where you can use
@Retention(RetentionPolicy.RUNTIME)
public @interface StrongPwd {


    
    String message() default "Password must be at least 8 chars, contain a digit and an uppercase letter";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
    
}
