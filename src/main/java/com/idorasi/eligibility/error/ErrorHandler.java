package com.idorasi.eligibility.error;

import com.idorasi.eligibility.dto.ApiResponse;
import com.idorasi.eligibility.dto.EligibilityRequest;
import com.idorasi.eligibility.error.exception.EligibilityRecordExpiredException;
import com.idorasi.eligibility.error.exception.InvalidEmployeeGroupException;
import com.idorasi.eligibility.error.exception.InvalidMemberStatusException;
import com.idorasi.eligibility.error.exception.RecordNotFound;
import com.idorasi.eligibility.service.AuditLogsService;
import com.idorasi.eligibility.util.SnakeCaseConverter;
import io.micrometer.common.util.StringUtils;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@RestControllerAdvice
@AllArgsConstructor
public class ErrorHandler {

    private AuditLogsService auditLogsService;

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<ApiResponse.ErrorBody>> handle(MethodArgumentNotValidException e) {
        var errors = e.getBindingResult().getFieldErrors().stream()
                .filter(f -> StringUtils.isNotBlank(f.getField()) && StringUtils.isNotBlank(f.getDefaultMessage()))
                .map(f -> new ApiResponse.ErrorDetail(SnakeCaseConverter.convert(f.getField()), f.getDefaultMessage()))
                .toList();

        var errorBody = new ApiResponse.ErrorBody(ErrorCode.VALIDATION_ERROR,
                ErrorCode.VALIDATION_ERROR.getMessage(),
                errors);

        var apiError = new ApiResponse<>("error", errorBody);

        if (e.getTarget() instanceof EligibilityRequest) {
            auditLogsService.insert((EligibilityRequest) e.getTarget(), "error", BAD_REQUEST.value());
        }

        return ResponseEntity.status(BAD_REQUEST)
                .body(apiError);
    }

    @ExceptionHandler(value = InvalidEmployeeGroupException.class)
    public ResponseEntity<ApiResponse<ApiResponse.ErrorBody>> handle(InvalidEmployeeGroupException e) {
        var errorBody = new ApiResponse.ErrorBody(ErrorCode.INVALID_EMPLOYEE_GROUP,
                ErrorCode.INVALID_EMPLOYEE_GROUP.getMessage(),
                null);

        auditLogsService.insert(e.getEligibilityRequest(), "error", HttpStatus.FORBIDDEN.value());
        return ResponseEntity.status(FORBIDDEN)
                .body(new ApiResponse<>("error", errorBody));
    }

    @ExceptionHandler(value = EligibilityRecordExpiredException.class)
    public ResponseEntity<ApiResponse<ApiResponse.ErrorBody>> handle(EligibilityRecordExpiredException e) {
        var errorDetails = new ApiResponse.ErrorDetail(e.getField(), e.getReason());
        var errorBody = new ApiResponse.ErrorBody(ErrorCode.ELIGIBILITY_EXPIRED,
                ErrorCode.ELIGIBILITY_EXPIRED.getMessage(),
                List.of(errorDetails));

        auditLogsService.insert(e.getEligibilityRequest(), "error", HttpStatus.NOT_ACCEPTABLE.value());

        return ResponseEntity.status(NOT_ACCEPTABLE)
                .body(new ApiResponse<>("error", errorBody));
    }

    @ExceptionHandler(value = RecordNotFound.class)
    public ResponseEntity<ApiResponse<ApiResponse.ErrorBody>> handle(RecordNotFound e) {
        var errorBody = new ApiResponse.ErrorBody(ErrorCode.RECORD_NOT_FOUND,
                ErrorCode.RECORD_NOT_FOUND.getMessage(),
                null);

        auditLogsService.insert(e.getEligibilityRequest(), "error", NOT_FOUND.value());

        return ResponseEntity.status(NOT_FOUND)
                .body(new ApiResponse<>("error", errorBody));
    }

    @ExceptionHandler(value = InvalidMemberStatusException.class)
    public ResponseEntity<ApiResponse<ApiResponse.ErrorBody>> handle(InvalidMemberStatusException e) {
        var errorDetails = new ApiResponse.ErrorDetail("member_status",
                "Invalid member_status code provided: " + e.getValue());

        auditLogsService.insert(e.getEligibilityRequest(), "error", BAD_REQUEST.value());

        var errorBody = new ApiResponse.ErrorBody(ErrorCode.VALIDATION_ERROR,
                ErrorCode.VALIDATION_ERROR.getMessage(),
                List.of(errorDetails));

        return ResponseEntity.status(BAD_REQUEST)
                .body(new ApiResponse<>("error", errorBody));
    }

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<ApiResponse.ErrorBody>> handle(Exception e) {

        auditLogsService.insert(null, "error", BAD_REQUEST.value());

        log.error("An unexpected error occurred", e);

        var errorBody = new ApiResponse.ErrorBody(ErrorCode.UNEXPECTED_ERROR,
                ErrorCode.UNEXPECTED_ERROR.getMessage(),
                null);

        return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                .body(new ApiResponse<>("error", errorBody));
    }

}
