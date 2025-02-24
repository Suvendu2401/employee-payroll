package com.epam.campus.console.employeecommand;

import com.epam.campus.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class UpdateDepartmentCommand implements Command{

    public final DepartmentService departmentService;
    public final Scanner scanner;

    @Autowired
    public UpdateDepartmentCommand(DepartmentService departmentService, Scanner scanner) {
        this.departmentService = departmentService;
        this.scanner = scanner;
    }

    @Override
    public void execute() {
        String[] department = checkValidDepartment();

        if(!departmentService.existsByName(department[0])){
            System.out.println("Department " + department[0] + "does not exist!");
            return;
        }
        departmentService.updateDepartment(department[0], department[1]);
        System.out.println("Department " + department[0] + " updated to " + department[1]);

    }
    public String[] checkValidDepartment(){
        while(true){
            System.out.println("Enter the department name that is to be updated: ");
            String dept = scanner.nextLine().trim();

            System.out.println("Enter the new name of the department: ");
            String updatedDept = scanner.nextLine().trim();

            if(dept.isEmpty() || updatedDept.isEmpty()){
                System.out.println("The department name cannot be empty");
                continue;
            }
            if(updatedDept.equals(dept)){
                System.out.println("The updated Department name cannot be same as old name");
                continue;
            }
            return new String[]{dept, updatedDept};
        }
    }
}
