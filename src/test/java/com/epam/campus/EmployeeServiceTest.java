package com.epam.campus;

import com.epam.campus.DTO.EmployeeDTO;
import com.epam.campus.exception.EmployeeNotFoundException;
import com.epam.campus.mapper.EmployeeMapper;
import com.epam.campus.model.Employee;
import com.epam.campus.repository.EmployeeRepository;
import com.epam.campus.service.EmployeeService;
import com.epam.campus.validation.EmployeeDataValidation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.time.LocalDate;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


public class EmployeeServiceTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @Mock
    private EmployeeDataValidation employeeDataValaidation;

    @InjectMocks
    private EmployeeService employeeService;

    private Employee employee;
    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
        employee = new Employee (101, "Suvendu", "IT", "Developer", LocalDate.of(2020,5,15), 50000);
        employeeDTO = EmployeeMapper.toDTO(employee);
    }

    @Test
    void testAddEmployee_Success() {
        when(employeeRepository.save(any(Employee.class))).thenReturn(employee);
        EmployeeDTO result = employeeService.addEmployee(employeeDTO);
        assertNotNull(result);
        assertEquals(employeeDTO.getName(), result.getName());
    }

    @Test
    void testGetEmployeeById_Found() {
        when(employeeRepository.findById(101)).thenReturn(Optional.of(employee));
        EmployeeDTO result = employeeService.getEmployeeById(101);
        assertEquals("Suvendu", result.getName());
    }

    @Test
    void testGetEmployeeById_NotFound() {
        when(employeeRepository.findById(999)).thenReturn(Optional.empty());
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.getEmployeeById(999));
    }

    @Test
    void testDeleteEmployee_Success() {
        when(employeeRepository.existsById(101)).thenReturn(true);
        doNothing().when(employeeRepository).deleteById(101);
        assertDoesNotThrow(() -> employeeService.deleteEmployee(101));
    }

    @Test
    void testDeleteEmployee_NotFound() {
        when(employeeRepository.existsById(999)).thenReturn(false);
        assertThrows(EmployeeNotFoundException.class, () -> employeeService.deleteEmployee(999));
    }

    @Test
    void testGetEmployeeById_ThrowsException() {
        when(employeeRepository.findById(101)).thenThrow(new RuntimeException("Database Error"));
        assertThrows(RuntimeException.class, () -> employeeService.getEmployeeById(101));
    }


}
