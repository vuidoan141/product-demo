package com.vuidoan.product.validation.annotation;

import com.vuidoan.product.validation.validator.MatchPasswordValidator;
import com.vuidoan.product.validation.validator.UniqueUserValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = MatchPasswordValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface MatchPassword {
    String message() default "Wrong password!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
