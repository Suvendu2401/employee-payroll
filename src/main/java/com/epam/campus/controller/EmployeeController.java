package com.epam.campus.controller;

import com.epam.campus.DTO.EmployeeDTO;
import com.epam.campus.service.EmployeeService;
import com.epam.campus.util.Logging;
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
        Logging.logger.info("Get All Employees");
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public EmployeeDTO getEmployeeById(@PathVariable Integer id){
        Logging.logger.info("Get Employee by id: {}", id);
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public EmployeeDTO addEmployee(@RequestBody EmployeeDTO employeeDto){
        Logging.logger.info("Add Employee: {}",employeeDto);
        return employeeService.addEmployee(employeeDto);
    }

    @GetMapping("/payroll/{department}")
    public Double payrollByDepartment(@PathVariable String department){
        Logging.logger.info("Payroll by department: {}",department);
        return employeeService.getPayrollByJobTitle(department);
    }

    @GetMapping("/payroll/{jobTitle}")
    public Double payrollByJobTitle(@PathVariable String jobTitle){
        Logging.logger.info("Payroll by job Title: {}",jobTitle);
        return employeeService.getPayrollByJobTitle(jobTitle);
    }

    @GetMapping("/payroll")
    public Double totalPayroll(){
        Logging.logger.info("Total payroll");
        return employeeService.getTotalPayroll();
    }

    @GetMapping("/payroll/department/{departmentName}/average-salary")
    public Double averagePayroll(@PathVariable String departmentName){
        Logging.logger.info("Get average salary of Department: {}",departmentName);
        return employeeService.calculateAverageSalaryByDepartment(departmentName);
    }

    @GetMapping("/grouped-by-department")
    public Map<String, List<EmployeeDTO>> employeeByDepartment (){
        Logging.logger.info("Get list of Employees by Department");
        return employeeService.getEmployeesByDepartment();
    }

    @GetMapping("/top-salaries/{n}")
    public List<EmployeeDTO> topNHighestPaidEmployee(@PathVariable Integer n){
        Logging.logger.info("Get top {} highest paid employees", n);
        return employeeService.getTopNHighestPaidEmployees(n);
    }

    @GetMapping("/hired-in-last/{months}")
    public List<EmployeeDTO> hiredInLastNMonths(@PathVariable Integer months){
        Logging.logger.info("Get the list of employees who have joined in the last {} month(s) ",months);
        return employeeService.getHiredEmployeesInLastNMonth(months);
    }

    @PutMapping("/{id}")
    public EmployeeDTO updateEmployee (@PathVariable Integer id, @RequestBody EmployeeDTO employeeDto){
        Logging.logger.info("Update Employee: {}",id );
        return employeeService.updateEmployee(id, employeeDto);
    }

    @DeleteMapping("/{id}")
    public void deleteEmployee(@PathVariable Integer id){
        Logging.logger.info("Delete Employee: {}", id);
        employeeService.deleteEmployee(id);

    }
}
