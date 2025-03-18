package com.idorasi.eligibility.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.idorasi.eligibility.validator.DependentRecordRequest;
import com.idorasi.eligibility.validator.EmployeeRecordRequest;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@EmployeeRecordRequest
@DependentRecordRequest
public class EligibilityRequest {

    @JsonProperty("employee_code")
    @NotBlank
    @Size(max = 10)
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    private String employeeCode;

    @JsonProperty("member_status")
    @NotBlank
    private String memberStatus;

    @JsonProperty("employee_id")
    @Size(min = 5, max = 20)
    @Pattern(regexp = "^[A-Za-z0-9]*$")
    private String employeeId;

    @JsonProperty("employee_date_of_birth")
    @NotBlank
    @Pattern(regexp = "^\\d{4}-(?:0[1-9]|1[0-2])-(?:0[1-9]|[12]\\d|3[01])$")
    private String employeeDateOfBirth;

    @JsonProperty("employee_first_name")
    @Size(max = 50)
    @Pattern(regexp = "^[a-zA-Z -']+$")
    private String employeeFirstName;

    @JsonProperty("employee_last_name")
    @Size(max = 50)
    @Pattern(regexp = "^[a-zA-Z -']+$")
    private String employeeLastName;
}
