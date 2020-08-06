package com.vuidoan.product.validation.annotation;

import com.vuidoan.product.validation.validator.UniqueCategoryValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Constraint(validatedBy = UniqueCategoryValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface UniqueCategory {
    String message() default "This category is existed!";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default{};
}
