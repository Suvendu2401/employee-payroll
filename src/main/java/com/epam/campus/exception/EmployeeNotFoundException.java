package com.epam.campus.exception;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(int employeeId) {
        super("Employee not found for employee Id: " + employeeId);
    }
}
