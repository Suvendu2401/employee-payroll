package com.epam.campus.util.employeeCommand;

import com.epam.campus.db.EmployeeRepository;
import com.epam.campus.validation.CheckNullValidation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Scanner;

@Component
public class PrintAllCommand implements Command {
    @Autowired
    EmployeeRepository repository;

    @Override
    public void execute() {
        CheckNullValidation checkNullValidation = new CheckNullValidation();
        if (checkNullValidation.checkNull(repository))
            System.out.println("Repository Empty. Nothing to print!");
        else repository.getAll().values().forEach(System.out::println);
    }
}
