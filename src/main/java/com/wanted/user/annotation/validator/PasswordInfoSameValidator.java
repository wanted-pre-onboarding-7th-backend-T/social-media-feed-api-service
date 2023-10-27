package com.wanted.user.annotation.validator;

import com.wanted.user.annotation.PasswordInfoSameValid;
import com.wanted.user.dto.request.UserPostRequestDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class PasswordInfoSameValidator implements ConstraintValidator<PasswordInfoSameValid, UserPostRequestDto> {

    @Override
    public void initialize(PasswordInfoSameValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(UserPostRequestDto value, ConstraintValidatorContext context) {
        String password = value.getPassword();
        String email = value.getEmail();
        String userName = value.getUserName();

        return !email.contains(password) && !password.contains(userName);
    }
}
