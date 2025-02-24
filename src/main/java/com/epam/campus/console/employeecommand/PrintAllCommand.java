package com.epam.campus.console.employeecommand;

import com.epam.campus.model.Employee;
import com.epam.campus.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PrintAllCommand implements Command {
    private final EmployeeService employeeService;

    @Autowired
    public PrintAllCommand(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @Override
    public void execute() {
        List<Employee> employees = employeeService.getAllEmployees();
        if (employees.isEmpty())
        {
            System.out.println("Repository Empty. Nothing to print!");
        }
        else {
            System.out.println("Employee List: ");
            employees.forEach(System.out::println);
        }
    }
}
