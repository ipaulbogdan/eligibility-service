package com.idorasi.eligibility.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.idorasi.eligibility.error.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class ApiResponse<T> {

    private String status;
    private T body;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    public record ErrorBody(ErrorCode errorCode, String message, List<ErrorDetail> details) {
    }

    public record ErrorDetail(String field, String issue) { }

    @Builder
    public record EligibilityRecordDto(
            String memberUniqueId,
            String firstName,
            String lastName,
            String dateOfBirth,
            String eligibilityStartDate,
            String eligibilityEndDate,
            String employeeStatus,
            String employeeGroup
    ) {}
}
