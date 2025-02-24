package com.epam.campus.console.employeecommand;

import com.epam.campus.model.Employee;
import com.epam.campus.service.DepartmentService;
import com.epam.campus.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Component
public class UpdateEmployeeCommand implements Command {

    private final EmployeeService employeeService;
    private final DepartmentService departmentService;
    private final Scanner scanner;
    private final SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Autowired
    public UpdateEmployeeCommand(EmployeeService employeeService, Scanner scanner, DepartmentService departmentService) {
        this.scanner = scanner;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @Override
    public void execute() {
        int updateId = getValidEmployeeId();
        Optional<Employee>existingEmployee = employeeService.getEmployeeById(updateId);

        if (existingEmployee.isEmpty()) {
            System.out.println("Employee Not found!");
            return;
        }
        Employee employee = existingEmployee.get();
        System.out.println("Updating employee: " + employee.getName() + " -> " + employee.getEmployeeId());

        String newName = getUpdatedInput("New name (Leave blank to keep previous name): ", employee.getName());

        String newDesignation = getUpdatedDesignation(employee.getDepartment(), employee.getDesignation());
        String newDepartment = getUpdatedInput("New Department ( Leave blank to keep existing: " + employee.getDepartment() + "):", employee.getDepartment());

        Date newDateOfJoining = getUpdatedDate(employee.getDateOfJoining());

        Employee updatedEmployee = new Employee();
        updatedEmployee.setName(newName);
        updatedEmployee.setDepartment(newDepartment);
        updatedEmployee.setDesignation(newDesignation);
        updatedEmployee.setDateOfJoining(newDateOfJoining);
        updatedEmployee.setSalary(departmentService.getSalary(newDepartment,newDesignation));

        employeeService.updateEmployee(updateId, updatedEmployee);
        System.out.println("Employee updated successfully.");
    }

    private int getValidEmployeeId() {
        while (true) {
            System.out.println("Enter the employee Id to update: ");
            try {
                return Integer.parseInt(scanner.nextLine().trim());

            } catch (NumberFormatException e) {
                System.out.println("Invalid input, Enter valid Employee Id:");
            }
        }
    }

    private String getUpdatedInput(String prompt, String currentValue) {
        System.out.println(prompt);
        String input = scanner.nextLine().trim();
        return input.isEmpty() ? currentValue : input;
    }

    private String getUpdatedDesignation(String department, String currentDesignation) {

        Map<String, Double> designations = departmentService.getDesignationByDepartment(department);

        if (designations == null || designations.isEmpty()) {
            System.out.println("No Designation found for this department. ");
            return currentDesignation;
        }
        System.out.println("Select new Role (keep blank to leave previous role): " + currentDesignation);
        List<String> designationList = new ArrayList<>(designations.keySet());
        int i = 0;
        designationList.forEach(e -> System.out.println((i + 1) + ". " + designationList.get(i)));
        System.out.println("Enter choice, Leave blank to keep existing: ");

        String roleChoiceInput = scanner.nextLine().trim();
        if (roleChoiceInput.isEmpty()) {
            return currentDesignation;
        }
        try {
            int roleChoice = Integer.parseInt(roleChoiceInput);
            if (roleChoice >= 1 && roleChoice <= designationList.size()) {
                return designationList.get(roleChoice - 1);
            } else {
                System.out.println("Invalid choice, Keeping Existing designation.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Invalid entry. keeping Existing Designation");
        }
        return currentDesignation;
    }

    private Date getUpdatedDate(Date currentDate) {
        while (true) {
            System.out.print("New Date of Joining (dd-MM-yyyy) (Leave Blank to keep Previous date): ");
            String dateInput = scanner.nextLine().trim();
            if (dateInput.isEmpty()) {
                return currentDate;
            }
            try {
                return dateFormat.parse(dateInput);
            } catch (ParseException e) {
                System.out.println(" Invalid date format. Please try again.");
            }
        }
    }
}