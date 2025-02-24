package com.epam.campus.service;

import com.epam.campus.repository.DepartmentRepository;
import com.epam.campus.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

@Service
public class DepartmentService {

    private final DepartmentRepository departmentRepository;

    @Autowired
    public DepartmentService(DepartmentRepository departmentRepository){
        this.departmentRepository = departmentRepository;
    }

    public boolean existsByName(String departmentName) {
        return departmentRepository.existsByName(departmentName);
    }

    public void addDepartment(String departmentName){
        if(departmentRepository.existsByName(departmentName)){
            System.out.println("Department already exists.");
            return;
        }
        Department department = new Department();
        department.setName(departmentName);
        departmentRepository.save(department);
        System.out.println("Department added successfully");
    }

    public void addDesignation(String departmentName, String designation, double salary){
        Optional<Department> departmentOpt = departmentRepository.findByName(departmentName);
        if(departmentOpt.isEmpty()){
            System.out.println("Department not found");
            return;
        }

        Department department = departmentOpt.get();
        department.getDesignations().put(designation, salary);
        departmentRepository.save(department);
        System.out.println("Designation added to "+ departmentName);
    }

    public void updateDepartment(String departmentName, String newDepartmentName){
        Optional<Department> departmentOpt = departmentRepository.findByName(departmentName);
        if (departmentOpt.isEmpty()){
            System.out.println("Department not found");
            return;
        }

        Department department = new Department();
        department.setName(newDepartmentName);
        departmentRepository.save(department);
        System.out.println("Department updated");
    }

    public void deleteDesignation(String departmentName, String designation){
        Optional<Department>departmentOpt = departmentRepository.findByName(departmentName);
        if(departmentOpt.isEmpty()){
            System.out.println("Department not found");
            return;
        }
        Department department = new Department();
       if(department.getDesignations().remove(designation) != null){
           departmentRepository.save(department);
           System.out.println(("Designation removed"));
       }else{
           System.out.println("Designation not found");
       }
    }

    public Map<String, Double> getDesignationByDepartment(String departmentName){
        Optional<Department> departmentOpt = departmentRepository.findByName(departmentName);
        return departmentOpt.map(Department::getDesignations).orElse(new TreeMap<>());
    }

    public void deleteDepartment(String departmentName){
        Optional<Department> departmentOpt =departmentRepository.findByName(departmentName);
        if (departmentOpt.isEmpty()){
            System.out.println("Department not found");
            return;
        }
        Department department = departmentOpt.get();
        if(!department.getDesignations().isEmpty()){
            System.out.println("Cannot delete department. Department contains designations. Delete designations first.");
           return;
        }
        departmentRepository.deleteByName(departmentName);
        System.out.println("Department: " + departmentName+ " deleted Successfully");
    }

    public Map<String, Double> getDesignationsByDepartment(String departmentName) {
        Optional<Department> departmentOpt = departmentRepository.findByName(departmentName);
        return departmentOpt.map(Department::getDesignations).orElse(new TreeMap<>());
    }

    public double getSalary(String departmentName, String designation){
        Optional<Department>departmentOpt = departmentRepository.findByName(departmentName);
        if(departmentOpt.isEmpty()){
            System.out.println("Department is not present. Value returned 0.0");
            return 0.0;
        }
        Department department = departmentOpt.get();
        Map<String, Double> designations = department.getDesignations();
        if(!designations.containsKey(designation)){
            System.out.println("Designation doesnt exist. Returned 0.0");
            return 0.0;
        }
        return designations.get(designation);
    }
}
