--changeset pidorasi:1

CREATE TABLE eligibility_records (
    member_unique_id TEXT PRIMARY KEY,
    first_name TEXT,
    last_name TEXT,
    date_of_birth DATE, -- Store as ISO 8601 format (YYYY-MM-DD)
    gender TEXT,
    relations TEXT,
    sub_group TEXT,
    job_type TEXT,
    hire_date TEXT,
    eligibility_start_date DATE, -- Store as ISO 8601 format (YYYY-MM-DD)
    eligibility_end_date DATE, -- Store as ISO 8601 format (YYYY-MM-DD), can
    employee_status TEXT,
    phone_number TEXT,
    address_1 TEXT,
    address_2 TEXT,
    city TEXT,
    state_code TEXT,
    zip_code TEXT,
    country TEXT,
    employee_group TEXT);

CREATE TABLE audit_logs (
    id serial PRIMARY KEY,
    timestamp TEXT, -- Store as ISO 8601 format with time
    request_params TEXT, -- JSON string with sensitive data masked
    ip_address TEXT,
    result_status TEXT,
    response_code INTEGER);