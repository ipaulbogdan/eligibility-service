--changeset pidorasi:2

INSERT INTO eligibility_records (
    member_unique_id, first_name, last_name, date_of_birth, gender, relations, sub_group, job_type, hire_date,
    eligibility_start_date, eligibility_end_date, employee_status, phone_number, address_1, address_2, city,
    state_code, zip_code, country, employee_group
) VALUES
('M12345', 'John', 'Doe', '1985-06-15', 'Male', 'Self', 'A1', 'Full-Time', '2010-08-01', '2027-09-01', '2030-12-31', 'Active',
 '555-1234', '123 Main St', 'Apt 4B', 'New York', 'NY', '10001', 'USA', '1'),

('M67890', 'Jane', 'Smith', '1990-02-20', 'Female', 'Spouse', 'B2', 'Part-Time', '2018-04-15', '2018-05-01', '2028-04-30', 'Active',
 '555-5678', '456 Elm St', NULL, 'Los Angeles', 'CA', '90012', 'USA', '2'),

('M24680', 'Robert', 'Johnson', '1978-11-05', 'Male', 'Child', 'C3', 'Contractor', '2022-06-01', '2022-07-01', '2027-06-30', 'Inactive',
 '555-9876', '789 Oak St', 'Suite 100', 'Chicago', 'IL', '60607', 'USA', '3'),

('M13579', 'Emily', 'Davis', '1995-09-10', 'Female', 'Self', 'D4', 'Intern', '2024-01-10', '2024-02-01', '2024-08-31', 'Active',
 '555-2468', '321 Maple St', NULL, 'Houston', 'TX', '77002', 'USA', '4'),

('M86420', 'Michael', 'Brown', '1982-04-30', 'Male', 'Self', 'E5', 'Full-Time', '2005-09-20', '2005-10-01', '2035-12-31', 'Active',
 '555-1357', '159 Cedar St', 'Unit 12', 'Seattle', 'WA', '98101', 'USA', '5');
