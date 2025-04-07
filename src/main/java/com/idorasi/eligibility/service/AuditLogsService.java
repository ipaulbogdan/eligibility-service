package com.idorasi.eligibility.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.idorasi.eligibility.dto.AuditLogDto;
import com.idorasi.eligibility.dto.EligibilityRequest;
import com.idorasi.eligibility.dto.LoginRequest;
import com.idorasi.eligibility.entity.tables.records.AuditLogsRecord;
import com.idorasi.eligibility.repository.AuditLogsRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.web.util.ContentCachingRequestWrapper;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AuditLogsService {

    private AuditLogsRepository auditLogsRepository;
    private HttpServletRequest httpServletRequest;
    private ObjectMapper objectMapper;

    public <T> void insert(T requestBody, String resultStatus, Integer statusCode) {
        var auditLog = new AuditLogsRecord();
        auditLog.setTimestamp(LocalDateTime.now().toString());
        auditLog.setResponseCode(statusCode);
        auditLog.setResultStatus(resultStatus);
        auditLog.setIpAddress(httpServletRequest.getRemoteAddr());
        auditLog.setRequestParams(getRequestBodyString(requestBody).orElse(""));

        auditLogsRepository.save(auditLog);
    }

    private <T> Optional<String> getRequestBodyString(T requestBody) {
        if(requestBody instanceof LoginRequest r) {
            var loginRequest = new LoginRequest(r.username(), "***");
            return Optional.ofNullable(writeObjectToJson(loginRequest));
        }

        return Optional.ofNullable(writeObjectToJson(requestBody));
    }

    private String writeObjectToJson(Object o) {
        try {
            return o == null ? null : objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            log.error("Failed to map object to string");
        }

        return null;
    }
}
