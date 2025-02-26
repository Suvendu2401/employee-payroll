package com.epam.campus.service;

import com.epam.campus.DTO.EmployeeDTO;
import com.epam.campus.DTO.EmployeeMapper;
import com.epam.campus.exception.EmployeeNotFoundException;
import com.epam.campus.model.Employee;
import com.epam.campus.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public EmployeeDTO addEmployee(EmployeeDTO employeeDto) {
        if(employeeDto == null)
            throw new IllegalArgumentException("Employee Data cannot be null");
        validateEmployeeData(employeeDto);
        Employee employee = EmployeeMapper.toEntity(employeeDto);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.toDTO(savedEmployee);
    }

    public EmployeeDTO getEmployeeById(int id) {
        if(id < 0 )
            throw new IllegalArgumentException("ID cannot be less than 0");
        return employeeRepository.findById(id)
                .map(EmployeeMapper::toDTO)
                .orElseThrow(()-> new EmployeeNotFoundException(id));

    }

    public Double getPayrollByJobTitle(String jobTitle){
        if (jobTitle == null || jobTitle.trim().isEmpty())
            throw new IllegalArgumentException("The Job Title cannot be empty.");
        return employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getDepartment().equalsIgnoreCase(jobTitle))
                .mapToDouble(Employee::getSalary)
                .reduce(0,Double::sum);
    }

    public Double getTotalPayroll(){
        return employeeRepository.findAll()
                .stream()
                .mapToDouble(Employee::getSalary)
                .reduce(0,Double::sum);
    }

    public List<EmployeeDTO> getAllEmployees() {
        return employeeRepository.findAll()
                .stream()
                .map(EmployeeMapper::toDTO)
                .toList();
    }

    public void deleteEmployee (int id) {
        if(id < 0 )
            throw new IllegalArgumentException("ID cannot be less than 0");
        if(!employeeRepository.existsById(id))
            throw new EmployeeNotFoundException(id);
        employeeRepository.deleteById(id);
    }

    public EmployeeDTO updateEmployee(int employeeId, EmployeeDTO employeeDto) {
        if (employeeDto == null)
            throw new IllegalArgumentException("Employee values cannot be null");
        validateEmployeeData(employeeDto);

        return employeeRepository.findById(employeeId)
                .map(existingEmployee -> {
                    existingEmployee.setName(employeeDto.getName());
                    existingEmployee.setDepartment(employeeDto.getDepartment());
                    existingEmployee.setDesignation(employeeDto.getDesignation());
                    existingEmployee.setDateOfJoining(employeeDto.getDateOfJoining());
                    existingEmployee.setSalary(employeeDto.getSalary());
                    Employee employee = employeeRepository.save(existingEmployee);
                    return EmployeeMapper.toDTO(employee);
                }).orElseThrow(() -> new EmployeeNotFoundException(employeeId));
    }

    public Map<String,List<EmployeeDTO>> getEmployeesByDepartment(String department) {
        if (department == null|| department.trim().isEmpty())
            throw new IllegalArgumentException("Department cannot be empty");
        List<Employee> emp =  employeeRepository.findAll();
        List<EmployeeDTO> empDto = emp.stream().map(EmployeeMapper::toDTO).toList();
        return empDto.stream().filter(e->e.getDepartment().equalsIgnoreCase(department)).collect(Collectors.groupingBy(EmployeeDTO::getDepartment));
    }

    public Double calculateAverageSalaryByDepartment(String department){
        if(department == null ||department.trim().isEmpty() )
            throw new IllegalArgumentException("Department cannot be empty");
        return employeeRepository.findAll()
                .stream()
                .filter(employee -> employee.getDepartment().equalsIgnoreCase(department))
                .mapToDouble(Employee::getSalary)
                .average()
                .orElse(0.0);
    }

    public Map<String, List<EmployeeDTO>> getEmployeesByDepartment(){
        List<Employee> emp = employeeRepository.findAll();
        List<EmployeeDTO> empDto = emp.stream().map(EmployeeMapper::toDTO).toList();
        return empDto.stream().collect(Collectors.groupingBy(EmployeeDTO::getDepartment));
    }

    public List<EmployeeDTO> getTopNHighestPaidEmployees(int n){
        if (n <= 0 || n > employeeRepository.findAll().size())
            throw new IllegalArgumentException("The value of n cannot be <= 0 and more than total number of Employees");
        List<Employee> emp = employeeRepository.findAll();
        return emp.stream().map(EmployeeMapper::toDTO).sorted().limit(n).toList();
    }

    private void validateEmployeeData(EmployeeDTO employeeDto){
        if (employeeDto.getName() == null || employeeDto.getName().trim().isEmpty()){
            throw new IllegalArgumentException("Employee Name cannot be empty");
        }
        if(employeeDto.getDepartment() == null || employeeDto.getDepartment().trim().isEmpty()){
            throw new IllegalArgumentException("Employee Department cannot be empty");
        }
        if(employeeDto.getDesignation() == null || employeeDto.getDesignation().trim().isEmpty()){
            throw new IllegalArgumentException("Employee Designation cannot be empty");
        }
        if(employeeDto.getDateOfJoining() == null){
            throw new IllegalArgumentException("Employee DOJ cannot be empty");
        }
        if(employeeDto.getSalary() < 0){
            throw new IllegalArgumentException("Employee Salary cannot be empty");
        }
    }
}
