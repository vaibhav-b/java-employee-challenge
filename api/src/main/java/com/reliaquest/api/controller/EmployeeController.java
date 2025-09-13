package com.reliaquest.api.controller;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeInput;
import com.reliaquest.api.service.EmployeeService;
import java.util.List;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmployeeController implements IEmployeeController<Employee, EmployeeInput> {

    private static final Logger logger = LoggerFactory.getLogger(EmployeeController.class);
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Override
    public ResponseEntity<List<Employee>> getAllEmployees() {
        logger.info("API: getAllEmployees called");
        List<Employee> employees = employeeService.getAllEmployees();
        return ResponseEntity.ok(employees);
    }

    @Override
    public ResponseEntity<List<Employee>> getEmployeesByNameSearch(String searchString) {
        logger.info("API: getEmployeesByNameSearch called with searchString={}", searchString);
        List<Employee> employees = employeeService.getAllEmployees();
        List<Employee> filtered = employees.stream()
                .filter(e -> e.getEmployee_name() != null
                        && e.getEmployee_name().toLowerCase().contains(searchString.toLowerCase()))
                .collect(Collectors.toList());
        logger.info("API: getEmployeesByNameSearch found {} employees", filtered.size());
        return ResponseEntity.ok(filtered);
    }

    @Override
    public ResponseEntity<Employee> getEmployeeById(String id) {
        logger.info("API: getEmployeeById called with id={}", id);
        Employee employee = employeeService.getEmployeeById(id);
        if (employee != null) {
            logger.info("API: getEmployeeById found employee for id={}", id);
            return ResponseEntity.ok(employee);
        } else {
            logger.warn("API: getEmployeeById did not find employee for id={}", id);
            return ResponseEntity.notFound().build();
        }
    }

    @Override
    public ResponseEntity<Integer> getHighestSalaryOfEmployees() {
        logger.info("API: getHighestSalaryOfEmployees called");
        List<Employee> employees = employeeService.getAllEmployees();
        Integer maxSalary = employees.stream()
                .map(Employee::getEmployee_salary)
                .filter(s -> s != null)
                .max(Integer::compareTo)
                .orElse(0);
        logger.info("API: getHighestSalaryOfEmployees result={}", maxSalary);
        return ResponseEntity.ok(maxSalary);
    }

    @Override
    public ResponseEntity<List<String>> getTopTenHighestEarningEmployeeNames() {
        logger.info("API: getTopTenHighestEarningEmployeeNames called");
        List<Employee> employees = employeeService.getAllEmployees();
        List<String> top10 = employees.stream()
                .sorted((e1, e2) -> Integer.compare(
                        e2.getEmployee_salary() != null ? e2.getEmployee_salary() : 0,
                        e1.getEmployee_salary() != null ? e1.getEmployee_salary() : 0))
                .limit(10)
                .map(Employee::getEmployee_name)
                .collect(Collectors.toList());
        logger.info("API: getTopTenHighestEarningEmployeeNames result={}", top10);
        return ResponseEntity.ok(top10);
    }

    @Override
    public ResponseEntity<Employee> createEmployee(EmployeeInput employeeInput) {
        logger.info("API: createEmployee called for name={}", employeeInput.getName());
        Employee created = employeeService.createEmployee(employeeInput);
        if (created != null) {
            logger.info("API: createEmployee created employee name={}", created.getEmployee_name());
            return ResponseEntity.ok(created);
        } else {
            logger.warn("API: createEmployee failed for name={}", employeeInput.getName());
            return ResponseEntity.badRequest().build();
        }
    }

    @Override
    public ResponseEntity<String> deleteEmployeeById(String id) {
        logger.info("API: deleteEmployeeById called for id={}", id);
        Employee employee = employeeService.getEmployeeById(id);
        if (employee == null) {
            logger.warn("API: deleteEmployeeById did not find employee for id={}", id);
            return ResponseEntity.notFound().build();
        }
        boolean deleted = employeeService.deleteEmployeeById(id);
        if (deleted) {
            logger.info("API: deleteEmployeeById deleted employee name={}", employee.getEmployee_name());
            return ResponseEntity.ok(employee.getEmployee_name());
        } else {
            logger.error("API: deleteEmployeeById failed to delete employee for id={}", id);
            return ResponseEntity.status(500).body("Could not delete employee");
        }
    }

}
