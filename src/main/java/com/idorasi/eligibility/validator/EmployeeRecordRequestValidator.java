package com.idorasi.eligibility.validator;

import com.idorasi.eligibility.dto.EligibilityRequest;
import com.idorasi.eligibility.dto.MemberStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class EmployeeRecordRequestValidator implements ConstraintValidator<EmployeeRecordRequest, EligibilityRequest> {

    @Override
    public void initialize(EmployeeRecordRequest recordRequest) {
    }

    @Override
    public boolean isValid(EligibilityRequest eligibilityRequest, ConstraintValidatorContext cxt) {
        if (MemberStatus.DEPENDENT.toString().equalsIgnoreCase(eligibilityRequest.getMemberStatus())) {
            return true;
        }

        return eligibilityRequest.getEmployeeId() != null;
    }

}