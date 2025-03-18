package com.idorasi.eligibility.service;

import com.idorasi.eligibility.dto.ApiResponse;
import com.idorasi.eligibility.dto.EligibilityRequest;
import com.idorasi.eligibility.dto.MemberStatus;
import com.idorasi.eligibility.entity.tables.records.EligibilityRecordsRecord;
import com.idorasi.eligibility.error.exception.EligibilityRecordExpiredException;
import com.idorasi.eligibility.error.exception.InvalidEmployeeGroupException;
import com.idorasi.eligibility.error.exception.RecordNotFound;
import com.idorasi.eligibility.repository.EligibilityRecordRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EligibilityService {

    private EligibilityRecordRepository eligibilityRecordRepository;
    private AuditLogsService auditLogsService;

    public ApiResponse<ApiResponse.EligibilityRecordDto> findEligibilityRecord(EligibilityRequest eligibilityRequest) {
        var memberStatus = MemberStatus.valueOfCode(eligibilityRequest.getMemberStatus(), eligibilityRequest);
        var dateOfBirth = extractDateOfBirth(eligibilityRequest.getEmployeeDateOfBirth());

        var eligibilityRecordOptional = switch (memberStatus) {
            case EMPLOYEE -> eligibilityRecordRepository.findEmployee(eligibilityRequest.getEmployeeId(), dateOfBirth);
            case DEPENDENT -> eligibilityRecordRepository.findDependent(eligibilityRequest.getEmployeeFirstName(),
                    eligibilityRequest.getEmployeeLastName(), dateOfBirth);
        };

        var eligibilityRecord = eligibilityRecordOptional
                .orElseThrow(() -> new RecordNotFound(eligibilityRequest));

        if (!eligibilityRecord.getEmployeeGroup().equalsIgnoreCase(eligibilityRequest.getEmployeeCode())) {
            throw new InvalidEmployeeGroupException(eligibilityRequest);
        }

        validateRecord(eligibilityRecord, eligibilityRequest);

        var eligibilityDto = ApiResponse.EligibilityRecordDto.builder()
                .memberUniqueId(eligibilityRecord.getMemberUniqueId())
                .firstName(eligibilityRecord.getFirstName())
                .lastName(eligibilityRecord.getLastName())
                .dateOfBirth(eligibilityRecord.getDateOfBirth().toString())
                .eligibilityStartDate(eligibilityRecord.getEligibilityStartDate().toString())
                .eligibilityEndDate(eligibilityRecord.getEligibilityEndDate().toString())
                .employeeStatus(eligibilityRecord.getEmployeeStatus())
                .employeeGroup(eligibilityRecord.getEmployeeGroup())
                .build();

        auditLogsService.insert(eligibilityRequest, "success", HttpStatus.OK.value());
        return new ApiResponse<>("success", eligibilityDto);
    }

    private void validateRecord(EligibilityRecordsRecord eligibilityRecord, EligibilityRequest eligibilityRequest) {
        var now = LocalDate.now();

        if (eligibilityRecord.getEligibilityStartDate().isAfter(now)) {
            throw new EligibilityRecordExpiredException("start_date",
                    "Eligibility start date was not reached",
                    eligibilityRequest);
        }

        if (eligibilityRecord.getEligibilityEndDate() != null && !eligibilityRecord.getEligibilityEndDate().isAfter(now)) {
            throw new EligibilityRecordExpiredException("end_date", "Eligibility record expired", eligibilityRequest);
        }
    }

    private LocalDate extractDateOfBirth(String employeeDateOfBirth) {
        try {
            return LocalDate.parse(employeeDateOfBirth);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date format provided " + employeeDateOfBirth);
        }
    }
}
