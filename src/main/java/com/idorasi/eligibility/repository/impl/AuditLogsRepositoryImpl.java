package com.idorasi.eligibility.repository.impl;

import com.idorasi.eligibility.entity.tables.AuditLogs;
import com.idorasi.eligibility.entity.tables.records.AuditLogsRecord;
import com.idorasi.eligibility.repository.AuditLogsRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class AuditLogsRepositoryImpl implements AuditLogsRepository {

    private DSLContext dslContext;

    @Override
    public void save(AuditLogsRecord auditLogsRecord) {
        dslContext.insertInto(AuditLogs.AUDIT_LOGS)
                .set(auditLogsRecord)
                .execute();
    }
}
