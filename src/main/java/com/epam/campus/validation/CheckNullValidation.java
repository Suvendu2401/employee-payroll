package com.epam.campus.validation;

import com.epam.campus.db.EmployeeRepository;

public class CheckNullValidation {

    /*
    returns true if the repository is empty;
     */
    public boolean checkNull(EmployeeRepository repository) {
        return repository == null;
    }
}
