package com.vuidoan.product.validation.annotation;

import com.vuidoan.product.entity.EntityType;
import com.vuidoan.product.validation.validator.UniqueUserValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = UniqueUserValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueUser {
    String message() default "This username is existed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default{};
}
