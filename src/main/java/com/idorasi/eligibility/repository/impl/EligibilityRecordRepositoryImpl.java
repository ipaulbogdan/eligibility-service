package com.idorasi.eligibility.repository.impl;

import com.idorasi.eligibility.entity.tables.EligibilityRecords;
import com.idorasi.eligibility.entity.tables.records.EligibilityRecordsRecord;
import com.idorasi.eligibility.repository.EligibilityRecordRepository;
import lombok.AllArgsConstructor;
import org.jooq.DSLContext;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class EligibilityRecordRepositoryImpl implements EligibilityRecordRepository {

    private DSLContext dslContext;

    @Override
    public Optional<EligibilityRecordsRecord> findEmployee(String memberId, LocalDate dateOfBirth) {
        var eligibilityRecords = EligibilityRecords.ELIGIBILITY_RECORDS;

        return dslContext.selectFrom(eligibilityRecords)
                .where(eligibilityRecords.MEMBER_UNIQUE_ID.equalIgnoreCase(memberId))
                .and(eligibilityRecords.DATE_OF_BIRTH.eq(dateOfBirth))
                .fetchOptional();
    }

    @Override
    public Optional<EligibilityRecordsRecord> findDependent(String firstName, String lastName, LocalDate dateOfBirth) {
        var eligibilityRecordTable = EligibilityRecords.ELIGIBILITY_RECORDS;

        return dslContext.selectFrom(eligibilityRecordTable)
                .where(eligibilityRecordTable.FIRST_NAME.equalIgnoreCase(firstName))
                .and(eligibilityRecordTable.LAST_NAME.equalIgnoreCase(lastName))
                .and(eligibilityRecordTable.DATE_OF_BIRTH.eq(dateOfBirth))
                .fetchOptional();
    }
}
