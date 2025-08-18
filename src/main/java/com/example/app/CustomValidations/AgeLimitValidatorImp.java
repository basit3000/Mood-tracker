package com.example.app.customValidations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class AgeLimitValidatorImp implements ConstraintValidator<AgeLimit,Integer> {


     @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {

         boolean is18Plus=value>=5;;
         return  is18Plus;

    }

}
