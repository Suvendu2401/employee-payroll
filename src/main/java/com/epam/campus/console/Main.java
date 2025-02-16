package com.epam.campus.console;

import com.epam.campus.model.Employee;
import com.epam.campus.service.EmployeeService;
import com.epam.campus.service.PayrollService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner ob = new Scanner(System.in);
        EmployeeService employeeService = new EmployeeService();
        PayrollService payrollService = new PayrollService();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        int ans;

        do {
            System.out.println("""
                    1. Create new Employee
                    2. Delete existing Employee
                    3. Read Employee details
                    4. Generate payroll by Department
                    5. Generate payroll of individual Employee
                    6. Display all Employees
                    0. Exit
                    """);

            System.out.print("Enter your choice: ");
            ans = ob.nextInt();
            ob.nextLine();

            switch (ans) {
                case 1:
                    System.out.print("Name: ");
                    String name = ob.nextLine();

                    System.out.print("Employee ID: ");
                    int employeeId = ob.nextInt();
                    ob.nextLine();

                    System.out.println("Select Role:");
                    System.out.println("1. Software Engineer");
                    System.out.println("2. Senior Software Engineer");
                    System.out.println("3. Team Lead");
                    System.out.println("4. Manager");
                    System.out.println("5. Director");
                    System.out.print("Enter choice: ");
                    int roleChoice = ob.nextInt();
                    ob.nextLine();

                    String role = switch (roleChoice) {
                        case 1 -> "Software Engineer";
                        case 2 -> "Senior Software Engineer";
                        case 3 -> "Team Lead";
                        case 4 -> "Manager";
                        case 5 -> "Director";
                        default -> "Unknown";
                    };

                    System.out.print("Department: ");
                    String department = ob.nextLine();

                    Date dateOfJoining;
                    while (true) {
                        System.out.print("Date of Joining (dd-MM-yyyy): ");
                        String dateInput = ob.nextLine();
                        try {
                            dateOfJoining = dateFormat.parse(dateInput);
                            break;
                        } catch (ParseException e) {
                            System.out.println("Invalid date format! Try again.");
                        }
                    }

                    Employee employee = new Employee(employeeId, name, role, department, dateOfJoining);
                    employeeService.addEmployee(employee);
                    break;
                case 2:
                    System.out.print("Enter Employee ID to update: ");
                    int updateId = ob.nextInt();
                    ob.nextLine();
                    Employee existingEmployee = employeeService.getEmployee(updateId);
                    if (existingEmployee == null) {
                        System.out.println("Employee not found!");
                        break;
                    }

                    System.out.println("Updating Employee: " + existingEmployee);

                    System.out.print("New Name (leave blank to keep current: " + existingEmployee.getName() + "): ");
                    String newName = ob.nextLine();
                    if (newName.isEmpty()) {
                        newName = existingEmployee.getName();
                    }

                    System.out.println("Select New Role (leave blank to keep current: " + existingEmployee.getRole() + "):");
                    System.out.println("1. Software Engineer");
                    System.out.println("2. Senior Software Engineer");
                    System.out.println("3. Team Lead");
                    System.out.println("4. Manager");
                    System.out.println("5. Director");
                    System.out.print("Enter choice (or press Enter to skip): ");
                    String roleChoiceInput = ob.nextLine();
                    String newRole = existingEmployee.getRole();
                    if (!roleChoiceInput.isEmpty()) {
                        roleChoice = Integer.parseInt(roleChoiceInput);
                        newRole = switch (roleChoice) {
                            case 1 -> "Software Engineer";
                            case 2 -> "Senior Software Engineer";
                            case 3 -> "Team Lead";
                            case 4 -> "Manager";
                            case 5 -> "Director";
                            default -> existingEmployee.getRole();
                        };
                    }

                    System.out.print("New Department (leave blank to keep current: " + existingEmployee.getDepartment() + "): ");
                    String newDepartment = ob.nextLine();
                    if (newDepartment.isEmpty()) {
                        newDepartment = existingEmployee.getDepartment();
                    }

                    System.out.print("New Date of Joining (dd-MM-yyyy) (leave blank to keep current): ");
                    String dateInput = ob.nextLine();
                    Date newDateOfJoining = existingEmployee.getDateOfJoining();
                    if (!dateInput.isEmpty()) {
                        try {
                            newDateOfJoining = dateFormat.parse(dateInput);
                        } catch (ParseException e) {
                            System.out.println("Invalid date format! Keeping existing date.");
                        }
                    }

                    Employee updatedEmployee = new Employee(updateId, newName, newRole, newDepartment, newDateOfJoining);
                    employeeService.updateEmployee(updateId, updatedEmployee);
                    break;

                case 3:
                    System.out.print("Enter Employee ID to delete: ");
                    int deleteId = ob.nextInt();
                    ob.nextLine();
                    if (employeeService.removeEmployee(deleteId)) {
                        System.out.println("Employee successfully deleted.");
                    } else {
                        System.out.println("Employee not found!");
                    }
                    break;

                case 4:
                    System.out.print("Enter department name: ");
                    String dept = ob.nextLine();
                    payrollService.generatePayrollByDepartment(employeeService.getAllEmployees(), dept);
                    break;

                case 5:
                    System.out.print("Enter Employee ID: ");
                    int searchId = ob.nextInt();
                    payrollService.generateIndividualPayroll(employeeService.getEmployee(searchId));
                    break;

                case 6:
                    employeeService.getAllEmployees().values().forEach(System.out::println);
                    break;

                case 0:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("Invalid choice!");
            }
        } while (ans != 0);

    }
}