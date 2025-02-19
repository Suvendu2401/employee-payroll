package com.epam.campus.util.employeeCommand;

import com.epam.campus.db.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class PayrollByDepartmentCommand implements Command {
    @Autowired
    EmployeeRepository repository;
    @Autowired
    Scanner ob;

    public PayrollByDepartmentCommand(EmployeeRepository repository, Scanner ob) {
        this.ob = ob;
        this.repository = repository;
    }

    @Override
    public void execute() {
        System.out.print("Enter department name: ");
        String dept = ob.nextLine();
        payrollService.generatePayrollByDepartment(repository.getAll(), dept);
    }
}
