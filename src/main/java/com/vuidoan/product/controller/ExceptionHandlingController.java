package com.vuidoan.product.controller;

import com.vuidoan.product.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlingController {
    Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @ExceptionHandler(value = ResourceNotFoundException.class)
    public ResponseEntity<String> handleResourceNotFoundException(ResourceNotFoundException e) {
        String message = e.toString();
        String messageResponse = message.substring(message.lastIndexOf(": ") + 1);
        logger.error(message);
        return new ResponseEntity<String>(messageResponse, HttpStatus.NOT_FOUND);
    }
}
