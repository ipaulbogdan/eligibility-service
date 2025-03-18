package com.idorasi.eligibility.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idorasi.eligibility.dto.AuditLogDto;
import com.idorasi.eligibility.dto.EligibilityRequest;
import com.idorasi.eligibility.entity.tables.records.AuditLogsRecord;
import com.idorasi.eligibility.repository.AuditLogsRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@AllArgsConstructor
public class AuditLogsService {

    private AuditLogsRepository auditLogsRepository;
    private HttpServletRequest httpServletRequest;
    private ObjectMapper objectMapper;

    public void insert(EligibilityRequest eligibilityRequest, String resultStatus, Integer statusCode) {
        var auditLog = new AuditLogsRecord();
        auditLog.setTimestamp(LocalDateTime.now().toString());
        auditLog.setRequestParams(eligibilityRequest == null ? "" : getRequestParams(eligibilityRequest));
        auditLog.setResponseCode(statusCode);
        auditLog.setResultStatus(resultStatus);
        auditLog.setIpAddress(httpServletRequest.getRemoteAddr());

        auditLogsRepository.save(auditLog);
    }

    private String getRequestParams(EligibilityRequest eligibilityRequest) {
        var maskedRequest = new EligibilityRequest(eligibilityRequest.getEmployeeCode(),
                eligibilityRequest.getMemberStatus(),
                eligibilityRequest.getEmployeeId(),
                "****",
                "****",
                "****");

        try {
            return objectMapper.writeValueAsString(maskedRequest);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
