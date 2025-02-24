package com.epam.campus.console.employeecommand;

import com.epam.campus.model.Employee;
import com.epam.campus.service.DepartmentService;
import com.epam.campus.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class PayrollByDepartmentCommand implements Command {

    private final EmployeeService employeeService;
    private final Scanner scanner;
    private final DepartmentService departmentService;

    @Autowired
    public PayrollByDepartmentCommand(EmployeeService employeeService, Scanner scanner, DepartmentService departmentService) {
        this.scanner = scanner;
        this.departmentService = departmentService;
        this.employeeService = employeeService;
    }

    @Override
    public void execute() {
        System.out.print("Enter department name: ");
        String dept = scanner.nextLine().trim();

        if(dept.isEmpty()){
            System.out.println("Department cannot be empty.");
            return;
        }

        if(!departmentService.existsByName(dept)){
            System.out.println("Department " + dept + " doesnt exist");
            return;
        }
        List<Employee> employees  = employeeService.getEmployeesByDepartment(dept);
        if(employees.isEmpty()){
            System.out.println("No employees found in the database.");
            return;
        }
        double totalPayroll = employees.stream().mapToDouble(Employee::getSalary).sum();

        System.out.println("Payroll for department: "+ dept);
        System.out.println("Payroll : " + totalPayroll);
    }
}
