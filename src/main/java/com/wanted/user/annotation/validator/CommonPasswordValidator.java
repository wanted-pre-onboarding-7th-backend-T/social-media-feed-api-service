package com.wanted.user.annotation.validator;

import com.wanted.user.annotation.CommonPasswordValid;
import com.wanted.user.annotation.utils.CommonPasswords;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class CommonPasswordValidator implements ConstraintValidator<CommonPasswordValid,String> {

    @Override
    public void initialize(CommonPasswordValid constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        return !CommonPasswords.isCommonPassword(value);
    }
}
