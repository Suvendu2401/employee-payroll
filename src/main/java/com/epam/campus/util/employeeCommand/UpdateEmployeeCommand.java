package com.epam.campus.util.employeeCommand;

import com.epam.campus.db.EmployeeRepository;
import com.epam.campus.model.Employee;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.util.Date;
import java.util.Scanner;

@Component
public class UpdateEmployeeCommand implements Command {
    @Autowired
    EmployeeRepository repository;
    @Autowired
    Scanner ob;
    public UpdateEmployeeCommand(EmployeeRepository repository, Scanner ob) {
        this.ob = ob;
        this.repository = repository;
    }

    @Override
    public void execute() {
        System.out.print("Enter Employee ID to update: ");
        int updateId = Integer.parseInt(ob.nextLine());

        if (!employeeValidation.employeePresent(updateId)) {
            System.out.println("Employee is not present.");

        }

        Employee existingEmployee = repository.get(updateId);
        System.out.print("Updating Employee: " + existingEmployee);
        System.out.print("New name(Leave blank to keep previous name): ");
        String newName = ob.nextLine();
        if (newName.isEmpty()) {
            newName = existingEmployee.getName();
        }
        System.out.printf("""
                Select New Role (Leave Blank to keep the default role: %s
                1. Junior
                2. Senior
                3. Lead
                4. Manager
                5. Director
                Enter choice (or press Enter to keep Default):
                %n""", existingEmployee.getDesignation());

        String roleChoiceInput = ob.nextLine();
        String newDesignation = existingEmployee.getDesignation();
        if (!roleChoiceInput.isEmpty()) {
            int roleChoice = Integer.parseInt(roleChoiceInput);
            newDesignation = switch (roleChoice) {
                case 1 -> "Junior";
                case 2 -> "Senior";
                case 3 -> "Lead";
                case 4 -> "Manager";
                case 5 -> "Director";
                default -> existingEmployee.getDesignation();
            };
        }
        System.out.print("New Department (Leave Blank to keep existing department: " + existingEmployee.getDepartment() + "): ");
        String newDepartment = ob.nextLine();
        if (newDepartment.isEmpty()) {
            newDepartment = existingEmployee.getDepartment();
        }
        System.out.print("New Date of Joining (dd-MM-yyyy) (lEave Blank to keep the Previous date): ");
        String dateInput = ob.nextLine();
        Date newDateOfJoining = existingEmployee.getDateOfJoining();
        if (!dateInput.isEmpty()) {
            try {
                newDateOfJoining = dateFormat.parse(dateInput);
            } catch (ParseException e) {
                System.out.println("Invalid date format.");
            }
        }

        Employee updatedEmployee = new Employee.Builder()
                .setEmployeeId(updateId)
                .setName(newName)
                .setDesignation(newDesignation)
                .setDepartment(newDepartment)
                .setDateOfJoining(newDateOfJoining)
                .build();
        repository.update(updateId, updatedEmployee);
    }
}
