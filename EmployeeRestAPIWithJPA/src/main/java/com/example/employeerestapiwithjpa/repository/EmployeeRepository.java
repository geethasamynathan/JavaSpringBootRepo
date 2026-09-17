package com.example.employeerestapiwithjpa.repository;


import com.example.employeerestapiwithjpa.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
        extends JpaRepository<Employee,Integer> {
}
