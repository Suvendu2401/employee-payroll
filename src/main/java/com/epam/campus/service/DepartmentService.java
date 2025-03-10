package com.epam.campus.service;

import com.epam.campus.repository.DepartmentRepository;
import com.epam.campus.model.Department;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.epam.campus.util.Logging;
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
            Logging.logger.error("Department already exists.");
            return;
        }
        Department department = new Department();
        department.setName(departmentName);
        departmentRepository.save(department);
        Logging.logger.info("Department added successfully");
    }

    public void addDesignation(String departmentName, String designation, double salary){
        Optional<Department> departmentOpt = departmentRepository.findByName(departmentName);
        if(departmentOpt.isEmpty()){
            Logging.logger.error("Department not found");
            return;
        }

        Department department = departmentOpt.get();
        department.getDesignations().put(designation, salary);
        departmentRepository.save(department);
        Logging.logger.info("Designation added to "+ departmentName);
    }

    public void updateDepartment(String departmentName, String newDepartmentName){
        Optional<Department> departmentOpt = departmentRepository.findByName(departmentName);
        if (departmentOpt.isEmpty()){
            Logging.logger.error("Department not found");
            return;
        }

        Department department = new Department();
        department.setName(newDepartmentName);
        departmentRepository.save(department);
        Logging.logger.info("Department updated");
    }

    public void deleteDesignation(String departmentName, String designation){
        Optional<Department>departmentOpt = departmentRepository.findByName(departmentName);
        if(departmentOpt.isEmpty()){
            Logging.logger.error("Department not found");
            return;
        }
        Department department = new Department();
       if(department.getDesignations().remove(designation) != null){
           departmentRepository.save(department);
           Logging.logger.info("Designation removed");
       }else{
           Logging.logger.error("Designation not found");
       }
    }

    public Map<String, Double> getDesignationByDepartment(String departmentName){
        Optional<Department> departmentOpt = departmentRepository.findByName(departmentName);
        return departmentOpt.map(Department::getDesignations).orElse(new TreeMap<>());
    }

    public void deleteDepartment(String departmentName){
        Optional<Department> departmentOpt =departmentRepository.findByName(departmentName);
        if (departmentOpt.isEmpty()){
            Logging.logger.error("Department not found");
            return;
        }
        Department department = departmentOpt.get();
        if(!department.getDesignations().isEmpty()){
            Logging.logger.warn("Cannot delete department. Department contains designations. Delete designations first.");
           return;
        }
        departmentRepository.deleteByName(departmentName);
        Logging.logger.debug("Department: " + departmentName+ " deleted Successfully");
    }


    public double getSalary(String departmentName, String designation){
        Optional<Department>departmentOpt = departmentRepository.findByName(departmentName);
        if(departmentOpt.isEmpty()){
            Logging.logger.error("Department is not present. Value returned 0.0");
            return 0.0;
        }
        Department department = departmentOpt.get();
        Map<String, Double> designations = department.getDesignations();
        if(!designations.containsKey(designation)){
            Logging.logger.error("Designation doesnt exist. Returned 0.0");
            return 0.0;
        }
        return designations.get(designation);
    }
}
