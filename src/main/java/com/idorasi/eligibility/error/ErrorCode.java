package com.idorasi.eligibility.error;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ErrorCode {

    VALIDATION_ERROR("Invalid request"),
    ACCESS_DENIED("Access denied"),
    INVALID_CREDENTIALS("Invalid credentials"),
    INVALID_EMPLOYEE_GROUP("Employee group not allowed"),
    ELIGIBILITY_EXPIRED("Record eligibility expired"),
    RECORD_NOT_FOUND("Record not found"),
    RATE_LIMIT_EXCEEDED("You reached the request count limit"),
    UNEXPECTED_ERROR("An unexpected error occurred");

    private final String message;
}
