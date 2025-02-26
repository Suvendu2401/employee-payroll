package com.epam.campus.DTO;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EmployeeDTO {

    private int employeeId;
    private String name;
    private String department;
    private String designation;
    private LocalDate dateOfJoining;
    private double salary;


}
