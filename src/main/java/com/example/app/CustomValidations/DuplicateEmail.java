package com.example.app.CustomValidations;

import jakarta.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import jakarta.validation.Constraint;



@Documented
@Constraint(validatedBy = DuplicateEmailValidatorImp.class) // link validator
@Target({ ElementType.FIELD, ElementType.PARAMETER })   // where you can use
@Retention(RetentionPolicy.RUNTIME)

public @interface  DuplicateEmail {
    
     String message() default "Email is already used";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
