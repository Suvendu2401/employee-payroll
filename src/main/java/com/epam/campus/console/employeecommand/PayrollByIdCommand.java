package com.epam.campus.console.employeecommand;

import com.epam.campus.model.Employee;
import com.epam.campus.service.DepartmentService;
import com.epam.campus.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.Scanner;

@Component
public class PayrollByIdCommand implements Command {

    public final EmployeeService employeeService;
    public final Scanner scanner;
    public final DepartmentService departmentService;

    @Autowired
    public PayrollByIdCommand(EmployeeService employeeService, Scanner scanner, DepartmentService departmentService) {
        this.scanner = scanner;
        this.employeeService = employeeService;
        this.departmentService = departmentService;
    }

    @Override
    public void execute() {
        int searchId = getValidEmployeeId();
        Employee employee =employeeService.getEmployeeById(searchId);

        if(employee == null){
            System.out.println("Employee wth Id: " + searchId + "not found." );
            return;
        }

        System.out.println("Employee with employee ID: " + searchId + "Payroll : " + employee.getSalary());
    }

    private int getValidEmployeeId(){
        while(true){
            System.out.println("Enter Employee ID: ");
            try{
                int id = Integer.parseInt(scanner.nextLine().trim());
                if(id > 0){
                    return id;
                }else{
                    System.out.println("Employee Id must be greater than 0.");
                }
            }catch(NumberFormatException e){
                System.out.println("Invalid Input. Try again");

            }
        }
    }
}
