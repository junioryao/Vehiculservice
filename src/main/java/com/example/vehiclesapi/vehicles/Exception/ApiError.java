package com.example.vehiclesapi.vehicles.Exception;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.LocalDateTime;

/**
 * Declares methods to return errors and other messages from the API.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
class ApiError {

    private final String message;

    private final LocalDateTime localDateTime;

    ApiError(String message, LocalDateTime localDateTime) {
        this.message = message;
        this.localDateTime = localDateTime;
    }

    public String getMessage() {
        return message;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }
}
