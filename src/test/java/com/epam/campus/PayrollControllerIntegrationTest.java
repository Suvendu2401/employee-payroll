package com.epam.campus;

import com.epam.campus.DTO.EmployeeDTO;
import com.epam.campus.controller.EmployeeController;
import com.epam.campus.service.EmployeeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import java.time.LocalDate;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.http.MediaType.APPLICATION_JSON;


@WebMvcTest(EmployeeController.class)
class EmployeePayrollIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testTotalPayrollCalculation() throws Exception {
        when(employeeService.getTotalPayroll()).thenReturn(200000.0);

        mockMvc.perform(get("/api/employees/payroll")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(APPLICATION_JSON))
                .andExpect(jsonPath("$").value(200000.0));
    }

    @Test
    void testPayrollForEmptyEmployeeList() throws Exception {
        when(employeeService.getTotalPayroll()).thenReturn(0.0);

        mockMvc.perform(get("/api/employees/payroll")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(0.0));
    }

    @Test
    void testPayrollForNegativeSalary() throws Exception {
        when(employeeService.getTotalPayroll()).thenReturn(-5000.0);

        mockMvc.perform(get("/api/employees/payroll")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").value(-5000.0));
    }

    @Test
    void testTopNHighestPaidEmployees() throws Exception {
        List<EmployeeDTO> topEmployees = List.of(
                new EmployeeDTO(1, "Alice", "Engineering", "Software Engineer",LocalDate.of(2020,5,15), 100000.0),
                new EmployeeDTO(2, "Bob", "Engineering", "Senior Engineer",LocalDate.of(2020,6,16), 90000.0)
        );
        when(employeeService.getTopNHighestPaidEmployees(2)).thenReturn(topEmployees);

        mockMvc.perform(get("/api/employees/top-salaries/2")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2))
                .andExpect(jsonPath("$[0].name").value("Alice"))
                .andExpect(jsonPath("$[1].name").value("Bob"));
    }

    @Test
    void testPayrollWithNullSalaryValues() throws Exception {
        when(employeeService.getTotalPayroll()).thenReturn(null);

        mockMvc.perform(get("/api/employees/payroll")
                        .contentType(APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").doesNotExist());
    }
}
