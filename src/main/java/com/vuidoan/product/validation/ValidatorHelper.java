package com.vuidoan.product.validation;

import org.springframework.validation.BindingResult;

import java.util.List;
import java.util.stream.Collectors;

public class ValidatorHelper {
    public static List<String> returnValidationErrors(BindingResult bindingResult) {
        return bindingResult.getAllErrors().stream().map(e -> e.getDefaultMessage()).collect(Collectors.toList());
    }
}
