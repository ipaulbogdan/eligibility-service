package com.idorasi.eligibility.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = DependentRecordRequestValidator.class)
@Target( { ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface DependentRecordRequest {
    String message() default "employee_first_name and employee_last_name must not be null";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}