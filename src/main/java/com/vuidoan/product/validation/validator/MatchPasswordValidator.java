package com.vuidoan.product.validation.validator;

import com.vuidoan.product.dto.UserDTO;
import com.vuidoan.product.entity.User;
import com.vuidoan.product.service.UserService;
import com.vuidoan.product.validation.annotation.MatchPassword;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MatchPasswordValidator implements ConstraintValidator<MatchPassword, UserDTO> {
    @Autowired
    UserService userService;

    @Override
    public void initialize(MatchPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(UserDTO value, ConstraintValidatorContext context) {
        User user = userService.findByUsername(value.getUsername());
        if(user == null) {
            return false;
        }
        String passwordInDB = user.getPassword();
        String passwordFromUser = value.getPassword();
        if (BCrypt.checkpw(passwordFromUser, passwordInDB)) {
            return true;
        }
        return false;
    }
}
