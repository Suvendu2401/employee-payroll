package com.epam.campus.util.employeeCommand;

import com.epam.campus.db.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DeleteEmployeeCommand implements Command {
    @Autowired
    EmployeeRepository repository;
    @Autowired
    Scanner ob;

    public DeleteEmployeeCommand(EmployeeRepository repository, Scanner ob) {

        this.ob = ob;
        this.repository = repository;
    }

    @Override
    public void execute() {
        System.out.print("Enter Employee ID to delete: ");
        int deleteId = Integer.parseInt(ob.nextLine());
        if (employeeValidation.employeePresent(deleteId)) {
            repository.remove(deleteId);
            System.out.println("Employee Deleted with Employee Id: " + deleteId);
        }
        System.out.println("Employee not present in Database");
    }
}
