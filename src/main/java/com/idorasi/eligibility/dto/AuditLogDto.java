package com.idorasi.eligibility.dto;

import java.time.LocalDateTime;

public record AuditLogDto(
        LocalDateTime timestamp,
        String requestParams,
        String ipAddress,
        String resultStatus,
        String responseCode
) {
}
