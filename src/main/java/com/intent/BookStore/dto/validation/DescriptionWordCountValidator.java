package com.intent.BookStore.dto.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;


public class DescriptionWordCountValidator implements ConstraintValidator<DescriptionWordCount, String> {
    private int minWordCount;

    @Override
    public void initialize(DescriptionWordCount constraintAnnotation) {
        this.minWordCount = constraintAnnotation.value();
    }

    @Override
    public boolean isValid(String description, ConstraintValidatorContext constraintValidatorContext) {
        if (description == null || description.trim().isEmpty()) {
            return false;
        }
        int wordCount = description.trim().split("\\s+").length;
        return wordCount >= minWordCount;
    }
}
