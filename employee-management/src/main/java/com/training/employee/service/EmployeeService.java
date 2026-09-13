package com.training.employee.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.training.employee.model.Employee;

@Service
public class EmployeeService {

    private final List<Employee> employees =
            new ArrayList<>();

    public EmployeeService() {

        employees.add(
                new Employee(
                        101,
                        "Ravi",
                        "IT",
                        55000
                )
        );

        employees.add(
                new Employee(
                        102,
                        "Priya",
                        "Finance",
                        60000
                )
        );
    }

    public List<Employee> getAllEmployees() {
        return employees;
    }

    public Employee getEmployeeById(int id) {

        return employees.stream()
                .filter(employee ->
                        employee.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public Employee addEmployee(Employee employee) {

        employees.add(employee);

        return employee;
    }

    public Employee updateEmployee(
            int id,
            Employee updatedEmployee) {

        Employee existingEmployee =
                getEmployeeById(id);

        if (existingEmployee != null) {

            existingEmployee.setName(
                    updatedEmployee.getName());

            existingEmployee.setDepartment(
                    updatedEmployee.getDepartment());

            existingEmployee.setSalary(
                    updatedEmployee.getSalary());
        }

        return existingEmployee;
    }

    public boolean deleteEmployee(int id) {

        return employees.removeIf(
                employee ->
                        employee.getId() == id
        );
    }
}