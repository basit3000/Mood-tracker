package com.example.app.CustomValidations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeLimitValidatorImp implements ConstraintValidator<AgeLimit,Integer> {


     @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

         boolean is18Plus=value>=18;;
         return  is18Plus;

    }

}
