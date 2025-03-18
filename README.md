# Eligibility Service API

## Setup

1. Build the application image:
   ```shell
   ./gradlew bootBuildImage
   ```
2. Navigate to the deployment folder and start services:
   ```bash
   cd deployment
   docker compose up -d
   ```
The service should now be up and running on port **5050**.

## API Calls

Swagger documentation is available at: [http://localhost:5050/swagger-ui/index.html](http://localhost:5050/swagger-ui/index.html)

### Authentication

There are two predefined users:

- **User**:
    - Username: `user`
    - Password: `password`
    - Role: `ROLE_API_USER`
- **Admin**:
    - Username: `admin`
    - Password: `password`
    - Role: `ROLE_ADMIN`

#### Login

To generate a JWT token, which will be required for authentication in subsequent API requests, use the following cURL command:

```sh
curl --location 'http://localhost:5050/api/login' \
--header 'Content-Type: application/json' \
--data '{
    "username": "user",
    "password": "password"
}'
```

#### Refresh Token

To refresh an expired token, use the refresh token generated in the previous call in the following cURL command:

```sh
curl --location 'http://localhost:5050/api/refresh' \
--header 'Content-Type: application/json' \
--data '{
    "refreshToken": "eyJhbGciOiJIUzUxMiJ9.eyJleHAiOjE3NDIzMjIyMjgsInN1YiI6InVzZXIiLCJpYXQiOjE3NDIyMzU4Mjh9.cAruYQTv2Tqk-pBi9RUXlH7wnBxOvHA8pbxQO4CYwndezfL8jONyeqMkORg_ncP7C6CQVsoaqt6qDjD_BDBaRA"
}'
```

### Eligibility Request Scenarios

#### 1. Employee Record Found

```sh
curl --location 'http://localhost:5050/api/eligibility/verify' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TOKEN>' \
--data '{
    "employee_code": "5", 
    "member_status": "employee", 
    "employee_id": "M86420", 
    "employee_date_of_birth": "1982-04-30"
}'
```

#### 2. Dependent Record Found

```sh
curl --location 'http://localhost:5050/api/eligibility/verify' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TOKEN>' \
--data '{
    "employee_code": "2", 
    "member_status": "dependent", 
    "employee_date_of_birth": "1990-02-20",
    "employee_first_name": "Jane",
    "employee_last_name": "Smith"
}'
```

#### 3. Record Not Found (Wrong Birthdate)

```sh
curl --location 'http://localhost:5050/api/eligibility/verify' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TOKEN>' \
--data '{
    "employee_code": "5", 
    "member_status": "employee", 
    "employee_id": "M86420", 
    "employee_date_of_birth": "1985-06-15"
}'
```

#### 4. Invalid Request

```sh
curl --location 'http://localhost:5050/api/eligibility/verify' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TOKEN>' \
--data '{
    "member_status": "employee", 
    "employee_id": "M86420", 
    "employee_date_of_birth": "1985-06-15"
}'
```

#### 5. Record Start Date Not Reached

```sh
curl --location 'http://localhost:5050/api/eligibility/verify' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TOKEN>' \
--data '{
    "employee_code": "1", 
    "member_status": "employee", 
    "employee_id": "M12345", 
    "employee_date_of_birth": "1985-06-15"
}'
```

#### 6. Expired Record

```sh
curl --location 'http://localhost:5050/api/eligibility/verify' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TOKEN>' \
--data '{
    "employee_code": "4", 
    "member_status": "employee", 
    "employee_id": "M13579", 
    "employee_date_of_birth": "1995-09-10"
}'
```

#### 7. Employee Group Not Allowed

```sh
curl --location 'http://localhost:5050/api/eligibility/verify' \
--header 'Content-Type: application/json' \
--header 'Authorization: Bearer <TOKEN>' \
--data '{
    "employee_code": "3", 
    "member_status": "employee", 
    "employee_id": "M13579", 
    "employee_date_of_birth": "1995-09-10"
}'
```

