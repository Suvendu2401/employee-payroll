package com.epam.campus.service;
import org.springframework.stereotype.Service;

import com.epam.campus.model.Employee;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class PayrollService {

    private static final Map<String, Double> roleSalaryMap = new HashMap<>();

    static {
        roleSalaryMap.put("Junior", 60000.0);
        roleSalaryMap.put("Senior", 90000.0);
        roleSalaryMap.put("Lead", 120000.0);
        roleSalaryMap.put("Manager", 150000.0);
        roleSalaryMap.put("Director", 200000.0);
    }

    public double getSalaryForRole(String role) {
        return roleSalaryMap.getOrDefault(role, 50000.0);
    }

    public void generatePayrollByDepartment(Map<Integer, Employee> employees, String department) {
        List<Employee> deptEmployees = employees.values().stream().filter(employee -> employee.getDepartment().equalsIgnoreCase(department)).collect(Collectors.toList());
        if (deptEmployees.isEmpty()) {
            System.out.println("No employees found in department: " + department);
            return;
        }
        System.out.println("Payroll: ");
        double totalSalary = 0;
        for (Employee emp : deptEmployees) {
            double salary = getSalaryForRole(emp.getDesignation());
            totalSalary += salary;
        }
        System.out.println("Total Department Payroll: $" + totalSalary);
    }

    public void generateIndividualPayroll(Employee employee) {
        if (employee == null) {
            System.out.println("Employee Not found");
            return;
        }

        double salary = getSalaryForRole(employee.getDesignation());
        System.out.println("Payroll for Employee: " + employee.getName());
        System.out.println("Role: " + employee.getDesignation());
        System.out.println("Salary: $" + salary);
    }
}
