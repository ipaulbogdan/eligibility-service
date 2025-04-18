/*
 * This file is generated by jOOQ.
 */
package com.idorasi.eligibility.entity.tables.records;


import com.idorasi.eligibility.entity.tables.AuditLogs;

import org.jooq.Record1;
import org.jooq.impl.UpdatableRecordImpl;


/**
 * This class is generated by jOOQ.
 */
@SuppressWarnings({ "all", "unchecked", "rawtypes", "this-escape" })
public class AuditLogsRecord extends UpdatableRecordImpl<AuditLogsRecord> {

    private static final long serialVersionUID = 1L;

    /**
     * Setter for <code>public.audit_logs.id</code>.
     */
    public void setId(Integer value) {
        set(0, value);
    }

    /**
     * Getter for <code>public.audit_logs.id</code>.
     */
    public Integer getId() {
        return (Integer) get(0);
    }

    /**
     * Setter for <code>public.audit_logs.timestamp</code>.
     */
    public void setTimestamp(String value) {
        set(1, value);
    }

    /**
     * Getter for <code>public.audit_logs.timestamp</code>.
     */
    public String getTimestamp() {
        return (String) get(1);
    }

    /**
     * Setter for <code>public.audit_logs.request_params</code>.
     */
    public void setRequestParams(String value) {
        set(2, value);
    }

    /**
     * Getter for <code>public.audit_logs.request_params</code>.
     */
    public String getRequestParams() {
        return (String) get(2);
    }

    /**
     * Setter for <code>public.audit_logs.ip_address</code>.
     */
    public void setIpAddress(String value) {
        set(3, value);
    }

    /**
     * Getter for <code>public.audit_logs.ip_address</code>.
     */
    public String getIpAddress() {
        return (String) get(3);
    }

    /**
     * Setter for <code>public.audit_logs.result_status</code>.
     */
    public void setResultStatus(String value) {
        set(4, value);
    }

    /**
     * Getter for <code>public.audit_logs.result_status</code>.
     */
    public String getResultStatus() {
        return (String) get(4);
    }

    /**
     * Setter for <code>public.audit_logs.response_code</code>.
     */
    public void setResponseCode(Integer value) {
        set(5, value);
    }

    /**
     * Getter for <code>public.audit_logs.response_code</code>.
     */
    public Integer getResponseCode() {
        return (Integer) get(5);
    }

    // -------------------------------------------------------------------------
    // Primary key information
    // -------------------------------------------------------------------------

    @Override
    public Record1<Integer> key() {
        return (Record1) super.key();
    }

    // -------------------------------------------------------------------------
    // Constructors
    // -------------------------------------------------------------------------

    /**
     * Create a detached AuditLogsRecord
     */
    public AuditLogsRecord() {
        super(AuditLogs.AUDIT_LOGS);
    }

    /**
     * Create a detached, initialised AuditLogsRecord
     */
    public AuditLogsRecord(Integer id, String timestamp, String requestParams, String ipAddress, String resultStatus, Integer responseCode) {
        super(AuditLogs.AUDIT_LOGS);

        setId(id);
        setTimestamp(timestamp);
        setRequestParams(requestParams);
        setIpAddress(ipAddress);
        setResultStatus(resultStatus);
        setResponseCode(responseCode);
    }
}
