package com.epam.campus.controller;

import com.epam.campus.DTO.EmployeeDTO;
import com.epam.campus.service.EmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(EmployeeController.class)
class EmployeeControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmployeeService employeeService;

    private EmployeeDTO employeeDTO;

    @BeforeEach
    void setUp() {

        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        employeeDTO = new EmployeeDTO(101, "Suvendu", "IT", "Developer", LocalDate.of(2020, 5,15), 50000.0);
    }

    @Test
    void testGetAllEmployees() throws Exception {
        when(employeeService.getAllEmployees()).thenReturn(List.of(employeeDTO));

        mockMvc.perform(get("/api/employees"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(1))
                .andExpect(jsonPath("$[0].name").value("Suvendu"));
    }

    @Test
    void testGetEmployeeById() throws Exception {
        when(employeeService.getEmployeeById(101)).thenReturn(employeeDTO);

        mockMvc.perform(get("/api/employees/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Suvendu"));
    }

    @Test
    void testDeleteEmployee() throws Exception {
        Mockito.doNothing().when(employeeService).deleteEmployee(101);

        mockMvc.perform(delete("/api/employees/101"))
                .andExpect(status().isOk());
    }

    @Test
    void testEmployeeByDepartment() throws Exception {
        when(employeeService.getEmployeesByDepartment()).thenReturn(Map.of("IT", List.of(employeeDTO)));

        mockMvc.perform(get("/api/employees/grouped-by-department"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.IT[0].name").value("Suvendu"));
    }
}
