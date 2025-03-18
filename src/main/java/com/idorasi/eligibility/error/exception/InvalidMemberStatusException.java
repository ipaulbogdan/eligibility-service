package com.idorasi.eligibility.error.exception;

import com.idorasi.eligibility.dto.EligibilityRequest;
import lombok.Getter;

@Getter
public class InvalidMemberStatusException extends RuntimeException {

    private final String value;
    private final EligibilityRequest eligibilityRequest;

    public InvalidMemberStatusException(EligibilityRequest eligibilityRequest, String value) {
        super("Invalid member_status provided: " + value);
        this.value = value;
        this.eligibilityRequest = eligibilityRequest;
    }

}
