package com.idorasi.eligibility.repository;

import com.idorasi.eligibility.entity.tables.records.AuditLogsRecord;

public interface AuditLogsRepository {

    void save(AuditLogsRecord auditLogsRecord);
}
