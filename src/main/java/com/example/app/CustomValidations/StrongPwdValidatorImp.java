package com.example.app.CustomValidations;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class StrongPwdValidatorImp implements ConstraintValidator<StrongPwd, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) return false;

        boolean hasUppercase = value.chars().anyMatch(Character::isUpperCase);
        boolean hasDigit = value.chars().anyMatch(Character::isDigit);

        return value.length() >= 8 && hasUppercase && hasDigit;
    }
}
