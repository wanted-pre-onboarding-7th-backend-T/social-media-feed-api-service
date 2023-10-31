package com.wanted.user.validation.validator;

import com.wanted.user.validation.annotation.CommonPasswordValid;
import com.wanted.user.validation.utils.CommonPasswords;
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
