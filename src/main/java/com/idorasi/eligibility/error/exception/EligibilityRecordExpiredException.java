package com.idorasi.eligibility.error.exception;

import com.idorasi.eligibility.dto.EligibilityRequest;
import lombok.Getter;

@Getter
public class EligibilityRecordExpiredException extends RuntimeException {

    private final String field;
    private final String reason;
    private final EligibilityRequest eligibilityRequest;

    public EligibilityRecordExpiredException(String field, String reason, EligibilityRequest eligibilityRequest) {
        super(String.format("Eligibility record expired. %s %s", field, reason));
        this.field = field;
        this.reason = reason;
        this.eligibilityRequest = eligibilityRequest;
    }
}
