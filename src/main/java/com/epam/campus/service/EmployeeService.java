package com.epam.campus.service;

import com.epam.campus.model.Employee;

import java.util.HashMap;
import java.util.Map;

public class EmployeeService {
    private Map<Integer, Employee> employeeDB = new HashMap<>();

    public void addEmployee(Employee employee) {
        if (employeeDB.containsKey(employee.getEmployeeId())) {
            System.out.println("Error: Employee ID already exists.");
        } else {
            employeeDB.put(employee.getEmployeeId(), employee);
            System.out.println("Employee added successfully!");
        }
    }

    public Employee getEmployee(int employeeId) {
        return employeeDB.get(employeeId);
    }

    public boolean updateEmployee(int employeeId, Employee updatedEmployee) {
        if (employeeDB.containsKey(employeeId)) {
            employeeDB.put(employeeId, updatedEmployee);
            System.out.println("Employee details updated successfully!");
            return true;
        } else {
            System.out.println("Employee not found!");
            return false;
        }
    }

    public boolean removeEmployee(int employeeId) {
        if (employeeDB.containsKey(employeeId)) {
            employeeDB.remove(employeeId);
            System.out.println("Employee deleted successfully!");
            return true;
        } else {
            System.out.println("Employee not found!");
            return false;
        }
    }

    public Map<Integer, Employee> getAllEmployees() {
        return employeeDB;
    }
}
