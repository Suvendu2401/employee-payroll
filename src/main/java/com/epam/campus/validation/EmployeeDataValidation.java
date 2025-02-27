package com.epam.campus.validation;

import com.epam.campus.DTO.EmployeeDTO;
import org.springframework.stereotype.Component;

@Component
public class EmployeeDataValidation {
    public void validateEmployeeData(EmployeeDTO employeeDto){
        if (employeeDto.getName() == null || employeeDto.getName().trim().isEmpty()){
            throw new IllegalArgumentException("Employee Name cannot be empty");
        }
        if(employeeDto.getDepartment() == null || employeeDto.getDepartment().trim().isEmpty()){
            throw new IllegalArgumentException("Employee Department cannot be empty");
        }
        if(employeeDto.getDesignation() == null || employeeDto.getDesignation().trim().isEmpty()){
            throw new IllegalArgumentException("Employee Designation cannot be empty");
        }
        if(employeeDto.getDateOfJoining() == null){
            throw new IllegalArgumentException("Employee DOJ cannot be empty");
        }
        if(employeeDto.getSalary() < 0){
            throw new IllegalArgumentException("Employee Salary cannot be empty");
        }
    }
}
