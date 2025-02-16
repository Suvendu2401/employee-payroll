package com.epam.campus.model;

import java.util.Date;

public class Employee {
    private int employeeId;
    private String name;
    private String department;
    private String role;
    private Date dateOfJoining;

    public Employee(int employeeId, String name, String role, String department, Date dateOfJoining){
        this.employeeId = employeeId;
        this.name = name;
        this.role = role;
        this.department = department;
        this.dateOfJoining = dateOfJoining;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public String getName() {
        return name;
    }

    public String getRole() {
        return role;
    }

    public String getDepartment() {
        return department;
    }

    public Date getDateOfJoining() {
        return dateOfJoining;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "\nEmployee ID: " + employeeId +
                "\nName: " + name + '\'' +
                "\nDepartment: " + department + '\'' +
                "\nRole:" + role + '\'' +
                "\nDate Of Joining: " + dateOfJoining +
                '}';
    }
}
