package com.epam.campus.mapper;

import com.epam.campus.DTO.EmployeeDTO;
import com.epam.campus.model.Employee;

public class EmployeeMapper {
    public static EmployeeDTO toDTO(Employee employee){
        return EmployeeDTO.builder()
                .employeeId(employee.getEmployeeId())
                .name(employee.getName())
                .department(employee.getDepartment())
                .designation(employee.getDesignation())
                .dateOfJoining(employee.getDateOfJoining())
                .salary(employee.getSalary())
                .build();
    }

    public static Employee toEntity(EmployeeDTO employeeDto){
        return Employee.builder()
                .employeeId(employeeDto.getEmployeeId())
                .name(employeeDto.getName())
                .department(employeeDto.getDepartment())
                .designation(employeeDto.getDesignation())
                .dateOfJoining(employeeDto.getDateOfJoining())
                .salary(employeeDto.getSalary())
                .build();
    }
}
