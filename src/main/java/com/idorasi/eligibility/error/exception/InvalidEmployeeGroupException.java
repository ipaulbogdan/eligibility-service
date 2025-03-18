package com.idorasi.eligibility.error.exception;

import com.idorasi.eligibility.dto.EligibilityRequest;
import lombok.Getter;

@Getter
public class InvalidEmployeeGroupException extends RuntimeException {

    private final EligibilityRequest eligibilityRequest;

    public InvalidEmployeeGroupException(EligibilityRequest eligibilityRequest) {
        super();
        this.eligibilityRequest = eligibilityRequest;
    }
}
