package com.example.vehiclesapi.vehicles.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Implements the Error controller related to any errors handled by the Vehicles API
 */
@ControllerAdvice
class ErrorControllerForCarNotFound {

    @ExceptionHandler(CarNotFoundException.class)
    ResponseEntity<Object> ErrorHandler(CarNotFoundException e) {
        ApiError apiError = new ApiError(e.getMessage(), LocalDateTime.now());
        return new ResponseEntity<>(apiError, HttpStatus.NOT_FOUND);
    }

}

