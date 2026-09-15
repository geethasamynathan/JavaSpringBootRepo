package com.example.jpdemo.repository;
import com.example.jpdemo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
    extends JpaRepository<Employee,Long>{
}
