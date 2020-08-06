package com.vuidoan.product.validation.validator;

import com.vuidoan.product.dto.UserDTO;
import com.vuidoan.product.repository.UserRepository;
import com.vuidoan.product.validation.annotation.UniqueUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UniqueUserValidator implements ConstraintValidator<UniqueUser, UserDTO> {
    @Autowired
    UserRepository userRepository;

    @Override
    public void initialize(UniqueUser constraintAnnotation) {
    }

    @Override
    public boolean isValid(UserDTO user, ConstraintValidatorContext context) {
        return userRepository.countByUsernameAndIdNot(user.getUsername(), user.getId()) <= 0;
    }
}
