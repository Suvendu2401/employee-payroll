package com.epam.campus.console.employeecommand;

import com.epam.campus.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class DeleteEmployeeCommand implements Command {

    EmployeeService employeeService;
    Scanner scanner;

    @Autowired
    public DeleteEmployeeCommand(EmployeeService employeeService, Scanner scanner) {

        this.scanner = scanner;
        this.employeeService = employeeService;
    }

    @Override
    public void execute() {
        int deleteId = getValidEmployeeId();

        if (employeeService.getEmployeeById(deleteId) != null) {
            employeeService.deleteEmployee(deleteId);
            System.out.println("Employee Deleted with Employee Id: " + deleteId);
            return;
        }
        System.out.println("Employee not present in Database");
    }

    private int getValidEmployeeId(){
        while (true){
            System.out.println("Enter the employee ID to delete: ");
            try{
                return Integer.parseInt(scanner.nextLine().trim());
            }catch (NumberFormatException e){
                System.out.println("Invalid input, Enter a valid numeric Employee ID");
            }
        }
    }
}
