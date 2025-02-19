package com.epam.campus.util.employeeCommand;

import com.epam.campus.service.PayrollService;
import com.epam.campus.validation.EmployeeValidation;

import java.text.SimpleDateFormat;


public interface Command {
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    EmployeeValidation employeeValidation = new EmployeeValidation();
    PayrollService payrollService = new PayrollService();

    void execute();
}
