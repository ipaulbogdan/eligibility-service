package com.idorasi.eligibility.repository;

import com.idorasi.eligibility.entity.tables.records.EligibilityRecordsRecord;

import java.time.LocalDate;
import java.util.Optional;

public interface EligibilityRecordRepository {

    Optional<EligibilityRecordsRecord> findEmployee(String memberId, LocalDate dateOfBirth);

    Optional<EligibilityRecordsRecord> findDependent(String firstName, String lastName, LocalDate dateOfBirth);


}
