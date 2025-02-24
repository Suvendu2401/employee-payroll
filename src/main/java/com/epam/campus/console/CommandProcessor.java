package com.epam.campus.console;


import com.epam.campus.console.employeecommand.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Scanner;

@Component
class CommandProcessor implements CommandLineRunner {
    private final Scanner scanner;
    private final Map<Integer, Command> commands;

    @Autowired
    public CommandProcessor(
            Scanner scanner,
            AddEmployeeCommand addEmployeeCommand,
            UpdateEmployeeCommand updateEmployeeCommand,
            DeleteEmployeeCommand deleteEmployeeCommand,
            PayrollByDepartmentCommand payrollByDepartmentCommand,
            PayrollByIdCommand payrollByIdCommand,
            PrintAllCommand printAllCommand,
            AddDepartmentCommand addDepartmentCommand,
            UpdateDepartmentCommand updateDepartmentCommand
    ) {
        this.scanner = scanner;
        this.commands = Map.of(
                1, addEmployeeCommand,
                2, updateEmployeeCommand,
                3, deleteEmployeeCommand,
                4, payrollByDepartmentCommand,
                5, payrollByIdCommand,
                6, printAllCommand,
                7, addDepartmentCommand,
                8, updateDepartmentCommand
        );
    }

    @Override
    public void run(String... args) {
        int choice;

        do {
            System.out.println("""
                     1. Create new Employee
                     2. Update Employee details
                     3. Delete existing Employee
                     4. Generate payroll by Department
                     5. Generate payroll of individual Employee
                     6. Display all Employees
                     7. Add Department
                     8. Update Department
                     0. Exit
                    """);

            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(scanner.nextLine());

            Command command = commands.get(choice);
            if (command != null) {
                command.execute();
            } else if (choice != 0) {
                System.out.println("Invalid Choice. Try again");
            }
        } while (choice != 0);

        scanner.close();
        System.out.println("Exiting...");
    }
}

//public class Main {
//    public static void main(String[] args) {
//        SpringApplication.run(Main.class,args);
//        Scanner ob;
//        EmployeeRepository repository = new InMemoryRepository();
//        DepartmentRepository departmentRepository = new InMemoryDepartmentRepository();
//        int choice;
//
//
//        Map<Integer, Command> commands = new HashMap<>();
//        commands.put(1, new AddEmployeeCommand(repository, ob));
//        commands.put(2, new UpdateEmployeeCommand(repository, ob));
//        commands.put(3, new DeleteEmployeeCommand(repository, ob));
//        commands.put(4, new PayrollByDepartmentCommand(repository, ob));
//        commands.put(5, new PayrollByIdCommand(repository, ob));
//        commands.put(6, new PrintAllCommand());
//        commands.put(7, new AddDepartmentCommand(departmentRepository, ob));
//        commands.put(8, new UpdateDepartmentCommand(departmentRepository, ob));
//        //command 9 : delete department
//        //command 10 : add designation
//        //command 11 : update designation
//        //command 12 : delete designation
//        do {
//            System.out.println("""
//                    1. Create new Employee
//                    2. Update Employee details
//                    3. Delete existing Employee
//                    4. Generate payroll by Department
//                    5. Generate payroll of individual Employee
//                    6. Display all Employees
//                    7. Add Department
//                    0. Exit
//                    """);
//
//            System.out.print("Enter your choice: ");
//            choice = Integer.parseInt(ob.nextLine());
//
//            Command command = commands.get(choice);
//            if (command != null) {
//                command.execute();
//            } else if (choice != 0) {
//                System.out.println("Invalid Choice. Try again");
//            }
//        } while (choice != 0);
//        ob.close();
//        System.out.println("Exiting...");
//    }
//}