package com.epam.campus.controller;

import com.epam.campus.DTO.EmployeeDTO;
import com.epam.campus.service.EmployeeService;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService = employeeService;
    }

    @GetMapping
    public List<EmployeeDTO> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Integer id){
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO employeeDto){
        return employeeService.addEmployee(employeeDto);
    }

    @GetMapping("/payroll/{department}")
    public Double payrollByDepartment(@PathVariable String department){
        return employeeService.getPayrollByJobTitle(department);
    }

    @GetMapping("/payroll/{jobTitle}")
    public Double payrollByJobTitle(@PathVariable String jobTitle){
        return employeeService.getPayrollByJobTitle(jobTitle);
    }

    @GetMapping("/payroll")
    public Double totalPayroll(){
        return employeeService.getTotalPayroll();
    }

    @GetMapping("/payroll/department/{departmentName}/average-salary")
    public Double averagePayroll(@PathVariable String departmentName){
        return employeeService.calculateAverageSalaryByDepartment(departmentName);
    }

    @GetMapping("/grouped-by-department")
    public Map<String, List<EmployeeDTO>> employeeByDepartment (){
        return employeeService.getEmployeesByDepartment();
    }

    @GetMapping("/top-salaries/{n}")
    public List<EmployeeDTO> topNHighestPaidEmployee(@PathVariable int n){
        return employeeService.getTopNHighestPaidEmployees(n);
    }

    @PutMapping("/{id}")
    public EmployeeDTO updateEmployee (@PathVariable Integer id, @RequestBody EmployeeDTO employeeDto){
        return employeeService.updateEmployee(id, employeeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Integer id){
        employeeService.deleteEmployee(id);

    }
}
