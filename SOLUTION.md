# Table of Contents
1. [Solution Overview](#solution-overview)
2. [Implemented Endpoints (API Module)](#implemented-endpoints-api-module)
3. [Implementation Details](#implementation-details)
4. [How to Run](#how-to-run)
5. [How to Validate the Solution (Step-by-Step)](#how-to-validate-the-solution-step-by-step)
6. [Example Requests and Responses](#example-requests-and-responses)
7. [Error Handling](#error-handling)
8. [Design Decisions](#design-decisions)
9. [Known Limitations & Future Improvements](#known-limitations--future-improvements)
10. [Dependencies](#dependencies)
11. [Contact](#contact)

## Error Handling

The API returns appropriate HTTP status codes and error messages for invalid requests, missing data, or server errors. Example error response:
```json
{
   "data": null,
   "status": "Employee not found"
}
```
Common error cases:
- 404 Not Found: Employee does not exist
- 400 Bad Request: Invalid input data
- 500 Internal Server Error: Unexpected server error

## Design Decisions

- Used Spring Boot for rapid REST API development.
- Service layer separates business logic from controllers.
- Logging is implemented for key operations and errors.
- Code is modular and follows clean coding practices.
- Test-driven development: unit and integration tests included.

## Known Limitations & Future Improvements

- Mock server data resets on each restart; persistent storage could be added.
- Rate limiting is random in the mock server and may affect testing.
- API does not implement authentication or authorization.
- More advanced error handling and validation can be added.

## Dependencies

- Java 17+
- Spring Boot
- Gradle
- Spotless (code formatting)
- JUnit (testing)

## Contact

For questions or support, contact: [your-email@domain.com]
## Example Requests and Responses

Below are example curl commands and expected responses for each endpoint:


### 1. Get All Employees
```
curl http://localhost:8111/api/v1/employee
```
Response:
```json
{
   "data": [
      {
         "id": "4a3a170b-22cd-4ac2-aad1-9bb5b34a1507",
         "employee_name": "Tiger Nixon",
         "employee_salary": 320800,
         "employee_age": 61,
         "employee_title": "Vice Chair Executive Principal of Chief Operations Implementation Specialist",
         "employee_email": "tnixon@company.com"
      },
      ...
   ],
   "status": "Successfully processed request."
}
```


### 2. Search Employees by Name
```
curl http://localhost:8111/api/v1/employee/search/Bill
```
Response:
```json
{
   "data": [
      {
         "id": "5255f1a5-f9f7-4be5-829a-134bde088d17",
         "employee_name": "Bill Bob",
         "employee_salary": 89750,
         "employee_age": 24,
         "employee_title": "Documentation Engineer",
         "employee_email": "billBob@company.com"
      }
   ],
   "status": "Successfully processed request."
}
```


### 3. Get Employee by ID
```
curl http://localhost:8111/api/v1/employee/5255f1a5-f9f7-4be5-829a-134bde088d17
```
Response:
```json
{
   "data": {
      "id": "5255f1a5-f9f7-4be5-829a-134bde088d17",
      "employee_name": "Bill Bob",
      "employee_salary": 89750,
      "employee_age": 24,
      "employee_title": "Documentation Engineer",
      "employee_email": "billBob@company.com"
   },
   "status": "Successfully processed request."
}
```


### 4. Get Highest Salary
```
curl http://localhost:8111/api/v1/employee/highestSalary
```
Response:
```json
{
   "data": 320800,
   "status": "Successfully processed request."
}
```


### 5. Get Top 10 Highest Earning Employee Names
```
curl http://localhost:8111/api/v1/employee/topTenHighestEarningEmployeeNames
```
Response:
```json
{
   "data": [
      "Tiger Nixon",
      "Bill Bob",
      ...
   ],
   "status": "Successfully processed request."
}
```



### 6. Create Employee
Sample Request Payload:
```json
{
   "name": "Jill Jenkins",
   "salary": 139082,
   "age": 48,
   "title": "Financial Advisor"
}
```
Example curl command:
```
curl -X POST http://localhost:8111/api/v1/employee \
   -H "Content-Type: application/json" \
   -d '{"name":"Jill Jenkins","salary":139082,"age":48,"title":"Financial Advisor"}'
```
Response:
```json
{
   "data": {
      "id": "d005f39a-beb8-4390-afec-fd54e91d94ee",
      "employee_name": "Jill Jenkins",
      "employee_salary": 139082,
      "employee_age": 48,
      "employee_title": "Financial Advisor",
      "employee_email": "jillj@company.com"
   },
   "status": "Successfully processed request."
}
```



### 7. Delete Employee by ID
Sample Request Payload (if required by API):
```json
{
   "name": "Bill Bob"
}
```
Example curl command:
```
curl -X DELETE http://localhost:8111/api/v1/employee/5255f1a5-f9f7-4be5-829a-134bde088d17
```
Response:
```json
{
   "data": true,
   "status": "Successfully processed request."
}
```
# Solution Overview

This document summarizes the implemented features and endpoints for the Employee API challenge.

## Implemented Endpoints (API Module)

- **GET /api/v1/employee**: Returns all employees.
- **GET /api/v1/employee/search/{searchString}**: Returns employees whose names contain or match the provided string.
- **GET /api/v1/employee/{id}**: Returns a single employee by ID.
- **GET /api/v1/employee/highestSalary**: Returns the highest salary among all employees.
- **GET /api/v1/employee/topTenHighestEarningEmployeeNames**: Returns the names of the top 10 highest earning employees.
- **POST /api/v1/employee**: Creates a new employee with the provided attributes.
- **DELETE /api/v1/employee/{id}**: Deletes the employee with the specified ID.

## Implementation Details

- All endpoints interact with the Mock Employee API at `http://localhost:8112/api/v1/employee`.
- Unit test cases are present.

## How to Run

1. Start the mock server:
   ```
   ./gradlew server:bootRun
   ```
2. Start the API application:
   ```
   ./gradlew api:bootRun
   ```


## How to Validate the Solution (Step-by-Step)

1. **Start the Mock Server**
   - Open a terminal in the project root.
   - Run:
     ```
     ./gradlew server:bootRun
     ```
   - Wait for the server to start and listen on port 8112.

2. **Start the API Application**
   - In a new terminal, run:
     ```
     ./gradlew api:bootRun
     ```
   - Wait for the API to start (usually on port 8111).

3. **Test Endpoints**
   - Use a tool like Postman, curl, or your browser to test the endpoints:
     - `GET http://localhost:8111/api/v1/employee` – List all employees
     - `GET http://localhost:8111/api/v1/employee/search/{searchString}` – Search employees by name
     - `GET http://localhost:8111/api/v1/employee/{id}` – Get employee by ID
     - `GET http://localhost:8111/api/v1/employee/highestSalary` – Get highest salary
     - `GET http://localhost:8111/api/v1/employee/topTenHighestEarningEmployeeNames` – Top 10 earners
     - `POST http://localhost:8111/api/v1/employee` – Create employee (provide JSON body)
     - `DELETE http://localhost:8111/api/v1/employee/{id}` – Delete employee by ID

4. **Check Logs**
   - Review the console output for both server and API for errors or status messages.

5. **Run Tests**
   - To run all tests, execute:
     ```
     ./gradlew test
     ```
   - Check the test reports for pass/fail status.

6. **Code Formatting**
   - To ensure code style, run:
     ```
     ./gradlew spotlessApply
     ```

## Notes
- The mock server must be running for the API to function correctly.
- See the main and module-specific README files for more details.
