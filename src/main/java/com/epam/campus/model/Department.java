package com.epam.campus.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.Map;
import java.util.TreeMap;

@Entity
@Table(name = "departments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String name;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "department_designations", joinColumns = @JoinColumn(name = "department_id"))
    @MapKeyColumn(name = "designation")
    @Column(name = "salary")
    private Map<String, Double> designations = new TreeMap<>();

}
