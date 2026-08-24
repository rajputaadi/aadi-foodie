package com.aadi.foodie.exception;

import com.aadi.foodie.dto.ErrorResponse;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;

@RestControllerAdvice
@RestController
public class GlobalExceptionHandler {

    private Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(NullPointerException.class)
    public String handleNullPointerException(NullPointerException ex) {
        logger.info(ex.getMessage());
        ex.printStackTrace();
        return "Null pointer exception!";
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Map<String, String> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        Map<String, String> errorMap = new HashMap<>();
        List<ObjectError> allErrors = ex.getBindingResult().getAllErrors();
        allErrors.forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errorMap.put(fieldName, errorMessage);
        });
        return errorMap;

    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleResourceNotFoundException(
            ResourceNotFoundException ex) {

        ErrorResponse errorResponse = ErrorResponse.builder()
                .message(ex.getMessage())
                .status(HttpStatus.NOT_FOUND)
                .build();

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

}
