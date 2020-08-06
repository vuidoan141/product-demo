package com.vuidoan.product.validation.validator;

import com.vuidoan.product.dto.CategoryDTO;
import com.vuidoan.product.repository.CategoryRepository;
import com.vuidoan.product.validation.annotation.UniqueCategory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueCategoryValidator implements ConstraintValidator<UniqueCategory, CategoryDTO> {
    @Autowired
    CategoryRepository categoryRepository;

    @Override
    public void initialize(UniqueCategory constraintAnnotation) {

    }

    @Override
    public boolean isValid(CategoryDTO value, ConstraintValidatorContext context) {
        return categoryRepository.countByNameAndIdNot(value.getName(), value.getId()) <= 0;
    }
}
