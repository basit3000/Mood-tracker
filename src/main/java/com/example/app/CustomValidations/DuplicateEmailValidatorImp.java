
package com.example.app.customValidations;

import org.springframework.beans.factory.annotation.Autowired;

import com.example.app.repository.UserRepository;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DuplicateEmailValidatorImp implements ConstraintValidator<DuplicateEmail,String> {

 @Autowired
         UserRepository userRepository;

     @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

     return   !userRepository.existsByEmail(value);
         

    }

}
