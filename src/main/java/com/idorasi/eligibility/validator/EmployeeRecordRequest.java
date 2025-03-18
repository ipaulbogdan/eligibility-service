package com.idorasi.eligibility.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = EmployeeRecordRequestValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface EmployeeRecordRequest {
    String message() default "employee_id must not be null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}