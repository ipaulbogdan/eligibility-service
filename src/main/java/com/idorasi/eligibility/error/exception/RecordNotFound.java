package com.idorasi.eligibility.error.exception;

import com.idorasi.eligibility.dto.EligibilityRequest;
import lombok.Getter;

@Getter
public class RecordNotFound extends RuntimeException {

    private final EligibilityRequest eligibilityRequest;

    public RecordNotFound(EligibilityRequest eligibilityRequest) {
        super();
        this.eligibilityRequest = eligibilityRequest;
    }
}
