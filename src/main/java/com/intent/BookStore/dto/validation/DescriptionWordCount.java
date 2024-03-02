package com.intent.BookStore.dto.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Constraint(validatedBy = DescriptionWordCountValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DescriptionWordCount {
    String message() default "Description must contain at most {value} words";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    int value() default 5;
}
