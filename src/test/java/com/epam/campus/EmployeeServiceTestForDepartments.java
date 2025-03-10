package com.epam.campus;

import com.epam.campus.DTO.EmployeeDTO;
import com.epam.campus.model.Employee;
import com.epam.campus.repository.EmployeeRepository;
import com.epam.campus.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceTestForDepartments {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeService employeeService;

    private List<Employee> mockEmployees;

    @BeforeEach
    void setUp() {
        mockEmployees = Arrays.asList(new Employee(101, "Alice", "IT", "Software Engineer", null, 60000), new Employee(102, "Bob", "HR", "HR Manager", null, 55000), new Employee(103, "Charlie", "IT", "Senior Developer", null, 70000), new Employee(104, "David", "Finance", "Accountant", null, 50000));
    }

    @Test
    void testFilterEmployeesByValidDepartment() {

        when(employeeRepository.findAll()).thenReturn(mockEmployees);


        Map<String, List<EmployeeDTO>> result = employeeService.getEmployeesByDepartment("IT");


        assertEquals(1, result.size());
        assertTrue(result.containsKey("IT"));
        assertEquals(2, result.get("IT").size());
        assertTrue(result.get("IT").stream().allMatch(e -> e.getDepartment().equals("IT")));
        verify(employeeRepository, times(1)).findAll();
    }


    @Test
    void testFilterEmployeesByNonExistentDepartment() {
        when(employeeRepository.findAll()).thenReturn(mockEmployees);


        Map<String, List<EmployeeDTO>> result = employeeService.getEmployeesByDepartment("Marketing");


        assertEquals(0, result.size());
        assertFalse(result.containsKey("Marketing"));

        verify(employeeRepository, times(1)).findAll();
    }


    @Test
    void testFilterEmployeesWhenNoEmployeesPresent() {
        when(employeeRepository.findAll()).thenReturn(Collections.emptyList());


        Map<String, List<EmployeeDTO>> result = employeeService.getEmployeesByDepartment("IT");


        assertEquals(0, result.size());

        verify(employeeRepository, times(1)).findAll();
    }
}
