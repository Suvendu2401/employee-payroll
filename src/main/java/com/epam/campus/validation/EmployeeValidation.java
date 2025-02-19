package com.epam.campus.validation;

import com.epam.campus.db.EmployeeRepository;
import com.epam.campus.db.InMemoryRepository;
import com.epam.campus.model.Employee;

/*
Returns true if employee is present
 */
public class EmployeeValidation {
    EmployeeRepository repository = new InMemoryRepository();

    public boolean employeePresent(int employeeId) {
        Employee existingEmployee = repository.get(employeeId);
        return existingEmployee != null;
    }
}
