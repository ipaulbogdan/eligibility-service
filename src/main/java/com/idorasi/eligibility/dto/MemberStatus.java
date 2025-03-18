package com.idorasi.eligibility.dto;

import com.idorasi.eligibility.error.exception.InvalidMemberStatusException;

import java.util.Arrays;

public enum MemberStatus {

    EMPLOYEE,
    DEPENDENT;

    public static MemberStatus valueOfCode(String code, EligibilityRequest eligibilityRequest) throws IllegalArgumentException {
        return Arrays.stream(MemberStatus.values())
                .filter(val -> val.name().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new InvalidMemberStatusException(eligibilityRequest, code));
    }

}
