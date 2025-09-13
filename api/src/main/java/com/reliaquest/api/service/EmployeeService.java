package com.reliaquest.api.service;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeInput;
import java.util.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private final RestTemplate restTemplate;
    private final String baseUrl;

    public EmployeeService(@Value("${employee.api.base-url:http://localhost:8112/api/v1/employee}") String baseUrl) {
        this.restTemplate = new RestTemplate();
        this.baseUrl = baseUrl;
    }

    public List<Employee> getAllEmployees() {
        logger.info("Fetching all employees from mock server");
        ResponseEntity<Map> response = restTemplate.getForEntity(baseUrl, Map.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            List<Map<String, Object>> data =
                    (List<Map<String, Object>>) response.getBody().get("data");
            List<Employee> employees = new ArrayList<>();
            for (Map<String, Object> item : data) {
                employees.add(mapToEmployee(item));
            }
            logger.info("Fetched {} employees", employees.size());
            return employees;
        }
        logger.warn("Failed to fetch employees or empty response");
        return Collections.emptyList();
    }

    public Employee getEmployeeById(String id) {
        logger.info("Fetching employee by id: {}", id);
        String url = baseUrl + "/" + id;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
            logger.info("Employee found for id: {}", id);
            return mapToEmployee(data);
        }
        logger.warn("Employee not found for id: {}", id);
        return null;
    }

    public Employee createEmployee(EmployeeInput input) {
        logger.info("Creating employee: {}", input.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> body = new HashMap<>();
        body.put("name", input.getName());
        body.put("salary", input.getSalary());
        body.put("age", input.getAge());
        body.put("title", input.getTitle());
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(baseUrl, request, Map.class);
        if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
            Map<String, Object> data = (Map<String, Object>) response.getBody().get("data");
            logger.info("Employee created: {}", data.get("employee_name"));
            return mapToEmployee(data);
        }
        logger.warn("Failed to create employee: {}", input.getName());
        return null;
    }

    public boolean deleteEmployeeById(String id) {
        logger.info("Deleting employee by id: {}", id);
        String url = baseUrl + "/" + id;
        try {
            restTemplate.delete(url);
            logger.info("Employee deleted: {}", id);
            return true;
        } catch (Exception e) {
            logger.error("Failed to delete employee: {}", id, e);
            return false;
        }
    }

    private Employee mapToEmployee(Map<String, Object> map) {
        Employee emp = new Employee();
        emp.setId((String) map.get("id"));
        emp.setEmployee_name((String) map.get("employee_name"));
        emp.setEmployee_salary((Integer) map.get("employee_salary"));
        emp.setEmployee_age((Integer) map.get("employee_age"));
        emp.setEmployee_title((String) map.get("employee_title"));
        emp.setEmployee_email((String) map.get("employee_email"));
        return emp;
    }
}
