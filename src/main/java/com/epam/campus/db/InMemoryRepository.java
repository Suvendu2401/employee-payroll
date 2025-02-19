package com.epam.campus.db;

import com.epam.campus.model.Employee;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

@Repository
public class InMemoryRepository implements EmployeeRepository {

    private static final Map<Integer, Employee> employeeDatabase = new HashMap<>();

    @Override
    public void add(Employee employee) {
        employeeDatabase.put(employee.getEmployeeId(), employee);
    }

    @Override
    public Employee get(int employeeId) {
        return employeeDatabase.get(employeeId);
    }

    @Override
    public void update(int employeeId, Employee updatedEmployee) {
        employeeDatabase.put(employeeId, updatedEmployee);
    }

    @Override
    public Map<Integer, Employee> getAll() {
        return employeeDatabase;
    }

    @Override
    public void remove(int employeeId) {
        employeeDatabase.remove(employeeId);
    }
}
