package com.epam.campus.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Employee {
    private int employeeId;
    private String name;
    private String department;
    private String designation;
    private Date dateOfJoining;

    public Employee(int employeeId, String name, String designation, String department, Date dateOfJoining) {
        this.employeeId = employeeId;
        this.name = name;
        this.designation = designation;
        this.department = department;
        this.dateOfJoining = dateOfJoining;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "\nEmployee ID: " + employeeId +
                "\nName: " + name +
                "\nDepartment: " + department +
                "\nRole:" + designation +
                "\nDate Of Joining: " + dateOfJoining +
                '}';
    }

    public static class Builder {
        private int employeeId;
        private String name;
        private String department;
        private String designation;
        private Date dateOfJoining;

        public Builder setEmployeeId(int employeeId) {
            this.employeeId = employeeId;
            return this;
        }

        public Builder setName(String name) {
            this.name = name;
            return this;
        }

        public Builder setDepartment(String department) {
            this.department = department;
            return this;
        }

        public Builder setDesignation(String designation) {
            this.designation = designation;
            return this;
        }

        public Builder setDateOfJoining(Date dateOfJoining) {
            this.dateOfJoining = dateOfJoining;
            return this;
        }

        public Employee build() {
            return new Employee(employeeId, name, department, designation, dateOfJoining);
        }
    }
}
