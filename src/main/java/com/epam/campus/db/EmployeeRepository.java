package com.epam.campus.db;

import com.epam.campus.model.Employee;

import java.util.Map;

public interface EmployeeRepository {

    void add(Employee employee);

    Employee get(int employeeId);

    void update(int employeeId, Employee updatedEmployee);

    Map<Integer, Employee> getAll();

    void remove(int employeeId);
}
