package com.epam.campus.console;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import com.epam.campus.db.InMemoryRepository;
import com.epam.campus.util.employeeCommand.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        Scanner ob = new Scanner(System.in);
        InMemoryRepository repository = new InMemoryRepository();
        int choice;


        Map<Integer, Command> commands = new HashMap<>();
        commands.put(1, new AddEmployeeCommand(repository, ob));
        commands.put(2, new UpdateEmployeeCommand(repository, ob));
        commands.put(3, new DeleteEmployeeCommand(repository, ob));
        commands.put(4, new PayrollByDepartmentCommand(repository, ob));
        commands.put(5, new PayrollByIdCommand(repository, ob));
        commands.put(6, new PrintAllCommand());
        do {
            System.out.println("""
                    1. Create new Employee
                    2. Update Employee details
                    3. Delete existing Employee
                    4. Generate payroll by Department
                    5. Generate payroll of individual Employee
                    6. Display all Employees
                    0. Exit
                    """);

            System.out.print("Enter your choice: ");
            choice = Integer.parseInt(ob.nextLine());

            Command command = commands.get(choice);
            if (command != null) {
                command.execute();
            } else if (choice != 0) {
                System.out.println("Invalid Choice. Try again");
            }
        } while (choice != 0);
        ob.close();
        System.out.println("Exiting...");
    }
}