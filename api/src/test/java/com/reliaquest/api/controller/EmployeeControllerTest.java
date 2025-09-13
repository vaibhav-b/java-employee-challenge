package com.reliaquest.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.reliaquest.api.model.Employee;
import com.reliaquest.api.model.EmployeeInput;
import com.reliaquest.api.service.EmployeeService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class EmployeeControllerTest {
    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeController employeeController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllEmployees() {
        Employee emp = new Employee();
        emp.setEmployee_name("John Doe");
        when(employeeService.getAllEmployees()).thenReturn(Collections.singletonList(emp));
        ResponseEntity<List<Employee>> response = employeeController.getAllEmployees();
        assertEquals(1, response.getBody().size());
        assertEquals("John Doe", response.getBody().get(0).getEmployee_name());
    }

    @Test
    void testGetEmployeesByNameSearch() {
        Employee emp1 = new Employee();
        emp1.setEmployee_name("Alice");
        Employee emp2 = new Employee();
        emp2.setEmployee_name("Bob");
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(emp1, emp2));
        ResponseEntity<List<Employee>> response = employeeController.getEmployeesByNameSearch("ali");
        assertEquals(1, response.getBody().size());
        assertEquals("Alice", response.getBody().get(0).getEmployee_name());
    }

    @Test
    void testGetEmployeeById_Found() {
        Employee emp = new Employee();
        emp.setEmployee_name("Jane");
        when(employeeService.getEmployeeById("1")).thenReturn(emp);
        ResponseEntity<Employee> response = employeeController.getEmployeeById("1");
        assertEquals("Jane", response.getBody().getEmployee_name());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeService.getEmployeeById("2")).thenReturn(null);
        ResponseEntity<Employee> response = employeeController.getEmployeeById("2");
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testGetHighestSalaryOfEmployees() {
        Employee emp1 = new Employee();
        emp1.setEmployee_salary(100);
        Employee emp2 = new Employee();
        emp2.setEmployee_salary(200);
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(emp1, emp2));
        ResponseEntity<Integer> response = employeeController.getHighestSalaryOfEmployees();
        assertEquals(200, response.getBody());
    }

    @Test
    void testGetTopTenHighestEarningEmployeeNames() {
        Employee emp1 = new Employee();
        emp1.setEmployee_name("A");
        emp1.setEmployee_salary(100);
        Employee emp2 = new Employee();
        emp2.setEmployee_name("B");
        emp2.setEmployee_salary(200);
        when(employeeService.getAllEmployees()).thenReturn(Arrays.asList(emp1, emp2));
        ResponseEntity<List<String>> response = employeeController.getTopTenHighestEarningEmployeeNames();
        assertEquals(Arrays.asList("B", "A"), response.getBody());
    }

    @Test
    void testCreateEmployee_Success() {
        EmployeeInput input = new EmployeeInput();
        input.setName("New");
        Employee emp = new Employee();
        emp.setEmployee_name("New");
        when(employeeService.createEmployee(input)).thenReturn(emp);
        ResponseEntity<Employee> response = employeeController.createEmployee(input);
        assertEquals("New", response.getBody().getEmployee_name());
    }

    @Test
    void testCreateEmployee_Failure() {
        EmployeeInput input = new EmployeeInput();
        input.setName("Fail");
        when(employeeService.createEmployee(input)).thenReturn(null);
        ResponseEntity<Employee> response = employeeController.createEmployee(input);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    void testDeleteEmployeeById_Success() {
        Employee emp = new Employee();
        emp.setEmployee_name("Del");
        when(employeeService.getEmployeeById("1")).thenReturn(emp);
        when(employeeService.deleteEmployeeById("1")).thenReturn(true);
        ResponseEntity<String> response = employeeController.deleteEmployeeById("1");
        assertEquals("Del", response.getBody());
    }

    @Test
    void testDeleteEmployeeById_NotFound() {
        when(employeeService.getEmployeeById("2")).thenReturn(null);
        ResponseEntity<String> response = employeeController.deleteEmployeeById("2");
        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    void testDeleteEmployeeById_Failure() {
        Employee emp = new Employee();
        emp.setEmployee_name("Del");
        when(employeeService.getEmployeeById("1")).thenReturn(emp);
        when(employeeService.deleteEmployeeById("1")).thenReturn(false);
        ResponseEntity<String> response = employeeController.deleteEmployeeById("1");
        assertEquals(500, response.getStatusCodeValue());
    }
}
