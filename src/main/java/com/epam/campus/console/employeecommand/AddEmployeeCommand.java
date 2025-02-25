package com.epam.campus.console.employeecommand;

import com.epam.campus.repository.EmployeeRepository;
import com.epam.campus.model.Employee;
import com.epam.campus.service.DepartmentService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Scanner;

@Component
public class AddEmployeeCommand implements Command {

    private final Scanner scanner;
    private final EmployeeRepository employeeRepository;
    private final AddDepartmentCommand addDepartmentCommand;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final DepartmentService departmentService;

    @Autowired
    public AddEmployeeCommand(Scanner scanner, EmployeeRepository employeeRepository,
                              AddDepartmentCommand addDepartmentCommand, DepartmentService departmentService) {
        this.scanner = scanner;
        this.employeeRepository = employeeRepository;
        this.addDepartmentCommand = addDepartmentCommand;
        this.departmentService = departmentService;
    }

    @Override
    public void execute() {
        String name = getInput("Name: ");
        int employeeId = getIntInput("Employee ID: ");
        String department = validateDepartment();
        String designation = validateDesignation(department);
        LocalDate dateOfJoining = getValidDate();

        double salary = departmentService.getSalary(department, designation);

        Employee employee = new Employee();
                employee.setEmployeeId(employeeId);
                employee.setName(name);
                employee.setDesignation(designation);
                employee.setDepartment(department);
                employee.setDateOfJoining(dateOfJoining);
                employee.setSalary(salary);

        employeeRepository.save(employee);
        System.out.println("Employee Added Successfully");
    }

    public String getInput(String prompt) {
        System.out.println(prompt);
        return scanner.nextLine().trim();
    }

    private int getIntInput(String prompt) {
        while (true) {
            try {
                System.out.println(prompt);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println("Invalid Input, try again");
            }
        }
    }

    private LocalDate getValidDate() {
        while (true) {
            try {
                System.out.print("Date of joining (dd-MM-yyyy): ");
                String dateInput = scanner.nextLine().trim();

                LocalDate localDate = LocalDate.parse(dateInput, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
                System.out.println("Parsed Date: " + localDate);
                return localDate;
            } catch (Exception e) {
                throw new RuntimeException("Invalid Date format: "+e);
            }
        }
    }

    private String validateDepartment() {
        String department = getInput("Employee Department");
        if (!departmentService.existsByName(department)) {
            System.out.println("Department doesnt exist. Do you want to add this department? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                addDepartmentCommand.execute();
            } else {
                throw new RuntimeException("Department is required. Operation cancelled.");
            }
        }
        return department;
    }

    private String validateDesignation(String department) {
        String designation = getInput("Employee Designation");
        if (!departmentService.getDesignationsByDepartment(department).containsKey(designation)) {
            System.out.print("Designation doesnt exist. Do you want to add this? (Y/N): ");
            if (scanner.nextLine().equalsIgnoreCase("Y")) {
                double salary = Double.parseDouble(getInput("Enter the base salary of this designation: "));
                departmentService.addDesignation(department, designation, salary);
            } else {
                throw new RuntimeException("Designation is required. Operation Cancelled.");
            }
        }
        return designation;
    }
}
