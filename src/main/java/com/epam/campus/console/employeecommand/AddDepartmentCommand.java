package com.epam.campus.console.employeecommand;

import com.epam.campus.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class AddDepartmentCommand implements Command {
    private final DepartmentService departmentService;
    private final Scanner scanner;

    @Autowired
    public AddDepartmentCommand(DepartmentService departmentService, Scanner scanner) {
        this.scanner = scanner;
        this.departmentService = departmentService;
    }

    @Override
    public void execute() {
        System.out.println("Enter Department name: ");
        String department = scanner.nextLine().trim();

        if(department.isEmpty()){
            System.out.println("Department name cannot be Empty");
            return;
        }
        if(departmentService.existsByName(department))
        {
            System.out.println("Department already exists!");
            return;
        }

        departmentService.addDepartment(department);
        System.out.println("Department added Successfully");
    }
}
