package com.epam.campus.util.employeeCommand;

import com.epam.campus.db.EmployeeRepository;
import com.epam.campus.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

@Component
public class AddEmployeeCommand implements Command {
    @Autowired
    Scanner ob;

    @Autowired
    EmployeeRepository repository;

    public AddEmployeeCommand(EmployeeRepository repository, Scanner ob) {
        this.ob = ob;
        this.repository = repository;
    }

    @Override
    public void execute() {
        ob.nextLine();
        System.out.print("Name: ");
        String name = ob.nextLine();

        System.out.print("Employee ID: ");
        int employeeId = Integer.parseInt(ob.nextLine());

        System.out.print("Employee Department: ");
        String department = ob.nextLine();

        System.out.print("""
                Select Designation:
                1. Junior
                2. Senior
                3. Lead
                4. Manager
                5. Director 
                """);
        System.out.print("Enter choice: ");
        int roleChoice = Integer.parseInt(ob.nextLine());

        String designation = switch (roleChoice) {
            case 1 -> "Junior";
            case 2 -> "Senior";
            case 3 -> "Lead";
            case 4 -> "Manager";
            case 5 -> "Director";
            default -> "Unknown";
        };
        Date dateOfJoining;
        while (true) {
            System.out.print("Date of Joining: (dd-MM-yyyy): ");
            String dateInput = ob.nextLine();
            try {
                dateOfJoining = dateFormat.parse(dateInput);
                break;
            } catch (ParseException e) {
                System.out.println("Invalid Date Format. Try again.");
            }
        }

        Employee employee = new Employee.Builder().setEmployeeId(employeeId).setName(name).setDesignation(designation).setDepartment(department).setDateOfJoining(dateOfJoining).build();
        repository.add(employee);
        System.out.println("Employee Added");
    }
}
