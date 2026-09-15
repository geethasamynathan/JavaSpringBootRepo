

package com.example.jpdemo.service;

import com.example.jpdemo.entity.Employee;
import com.example.jpdemo.repository.EmployeeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeService(
            EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    public Employee addEmployee(Employee employee) {

        return employeeRepository.save(employee);
    }

    public List<Employee> getAllEmployees() {

        return employeeRepository.findAll();
    }

    public Employee getEmployeeById(Long id) {

        return employeeRepository
                .findById(id)
                .orElse(null);
    }

    public Employee updateEmployee(
            Long id,
            Employee newEmployee) {

        Employee employee =
                employeeRepository
                        .findById(id)
                        .orElse(null);

        if (employee == null) {
            return null;
        }

        employee.setEmployeeName(
                newEmployee.getEmployeeName());

        employee.setAge(
                newEmployee.getAge());

        employee.setSalary(
                newEmployee.getSalary());

        employee.setDepartment(
                newEmployee.getDepartment());

        return employeeRepository.save(employee);
    }

    public boolean deleteEmployee(Long id) {

        if (!employeeRepository.existsById(id)) {
            return false;
        }

        employeeRepository.deleteById(id);

        return true;
    }
}