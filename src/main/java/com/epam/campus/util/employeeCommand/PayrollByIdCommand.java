package com.epam.campus.util.employeeCommand;

import com.epam.campus.db.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class PayrollByIdCommand implements Command {
    @Autowired
    EmployeeRepository repository;
    @Autowired
    Scanner ob;

    public PayrollByIdCommand(EmployeeRepository repository, Scanner ob) {
        this.ob = ob;
        this.repository = repository;
    }

    @Override
    public void execute() {
        System.out.print("Enter Employee ID: ");
        int searchId = Integer.parseInt(ob.nextLine());
        payrollService.generateIndividualPayroll(repository.get(searchId));

    }
}
