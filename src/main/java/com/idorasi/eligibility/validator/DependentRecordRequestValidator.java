package com.idorasi.eligibility.validator;

import com.idorasi.eligibility.dto.EligibilityRequest;
import com.idorasi.eligibility.dto.MemberStatus;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class DependentRecordRequestValidator implements ConstraintValidator<DependentRecordRequest, EligibilityRequest> {

    @Override
    public void initialize(DependentRecordRequest dependentRecordRequest) {
    }

    @Override
    public boolean isValid(EligibilityRequest eligibilityRequest, ConstraintValidatorContext cxt) {
        if (MemberStatus.EMPLOYEE.toString().equalsIgnoreCase(eligibilityRequest.getMemberStatus())) {
            return true;
        }

        return eligibilityRequest.getEmployeeLastName() != null && eligibilityRequest.getEmployeeFirstName() != null;
    }

}