package com.example.employeerestapiwithjpa.service;

import com.example.employeerestapiwithjpa.model.Employee;
import com.example.employeerestapiwithjpa.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(EmployeeRepository employeeRepository)
    {
        this.employeeRepository=employeeRepository;
    }

    public Employee addEmployee(Employee employee){
        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmplyees(){
        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(int id)
    {
        return employeeRepository.findById(id).orElse(null);
    }

    public Employee updateEmployee(Integer id, Employee updatedEmployee){
        Employee existingEmployee=employeeRepository.findById(id).orElse(null);
        if(existingEmployee==null)
        {
            return null;
        }
        existingEmployee.setEmployeeName(updatedEmployee.getEmployeeName());
        existingEmployee.setSalary(updatedEmployee.getSalary());
        existingEmployee.setAge(updatedEmployee.getAge());
        existingEmployee.setDepartment(updatedEmployee.getDepartment());
        existingEmployee.setPermanentEmployee(updatedEmployee.getPermanentEmployee());

        return   employeeRepository.save(existingEmployee);

    }
    public boolean deleteEmployee(int id){
        if(!employeeRepository.existsById(id)){
           return false;
        }
        employeeRepository.deleteById(id);
        return true;
    }
}
