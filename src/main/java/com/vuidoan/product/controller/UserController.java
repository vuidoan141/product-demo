package com.vuidoan.product.controller;

import com.vuidoan.product.dto.UserDTO;
import com.vuidoan.product.entity.User;
import com.vuidoan.product.security.JwtTokenProvider;
import com.vuidoan.product.service.UserService;
import com.vuidoan.product.validation.ValidatorHelper;
import com.vuidoan.product.validation.group.OnCreate;
import com.vuidoan.product.validation.group.OnUpdatePassword;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class UserController {
    @Autowired
    UserService userService;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JwtTokenProvider jwtTokenProvider;
    @Autowired
    ModelMapper modelMapper;
    private ObjectError error;

    @PostMapping("/register")
    public ResponseEntity<?> register(@Validated(OnCreate.class) @RequestBody UserDTO userDTO,
                                      BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errors = ValidatorHelper.returnValidationErrors(bindingResult);
            return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);
        }
        userDTO.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userService.save(modelMapper.map(userDTO, User.class));
        return new ResponseEntity<String>("Register Successfully", HttpStatus.OK);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserDTO userDTO) {
        Authentication authentication =
                authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getUsername(),
                        userDTO.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtTokenProvider.generateToken(authentication);
        return new ResponseEntity<String>(token, HttpStatus.OK);
    }

    @PostMapping("/change-password")
    public ResponseEntity<?> changePassword(@Validated(OnUpdatePassword.class) @RequestBody UserDTO userDTO,
                                                  BindingResult bindingResult) {
        if (!userDTO.getNewPassword().equals(userDTO.getConfirmPassword())) {
            return new ResponseEntity<String>("The new password and confirm password are not matching",
                    HttpStatus.BAD_REQUEST);
        }
        if (bindingResult.hasErrors()) {
            List<String> errors = ValidatorHelper.returnValidationErrors(bindingResult);
            return new ResponseEntity<List<String>>(errors, HttpStatus.BAD_REQUEST);
        }
        User user = userService.findByUsername(userDTO.getUsername());
        user.setPassword(passwordEncoder.encode(userDTO.getNewPassword()));
        userService.save(user);
        return new ResponseEntity<String>("Change Password Successfully", HttpStatus.OK);
    }
}
