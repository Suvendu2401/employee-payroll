package com.epam.campus.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "employees")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    @Id
    private int employeeId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String department;

    @Column(nullable = false)
    private String designation;

    @Column(name = "date_of_joining")
    private LocalDate dateOfJoining;

    @Column(nullable = false)
    private double salary;

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

    public void setDateOfJoining(LocalDate dateOfJoining) {
    }
}
