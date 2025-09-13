
# Solution Overview

This document summarizes the implemented features and endpoints for the Employee API challenge.

## Implemented Endpoints (API Module)

- **GET /api/v2/employee**: Returns all employees.
- **GET /api/v2/employee/search/{searchString}**: Returns employees whose names contain or match the provided string.
- **GET /api/v2/employee/{id}**: Returns a single employee by ID.
- **GET /api/v2/employee/highestSalary**: Returns the highest salary among all employees.
- **GET /api/v2/employee/topTenHighestEarningEmployeeNames**: Returns the names of the top 10 highest earning employees.
- **POST /api/v2/employee**: Creates a new employee with the provided attributes.
- **DELETE /api/v2/employee/{id}**: Deletes the employee with the specified ID.

## Implementation Details

- All endpoints interact with the Mock Employee API at `http://localhost:8112/api/v2/employee`.
- Unit test cases are present.

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
     - `GET http://localhost:8111/api/v2/employee` – List all employees
     - `GET http://localhost:8111/api/v2/employee/search/{searchString}` – Search employees by name
     - `GET http://localhost:8111/api/v2/employee/{id}` – Get employee by ID
     - `GET http://localhost:8111/api/v2/employee/highestSalary` – Get highest salary
     - `GET http://localhost:8111/api/v2/employee/topTenHighestEarningEmployeeNames` – Top 10 earners
     - `POST http://localhost:8111/api/v2/employee` – Create employee (provide JSON body)
     - `DELETE http://localhost:8111/api/v2/employee/{id}` – Delete employee by ID

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
