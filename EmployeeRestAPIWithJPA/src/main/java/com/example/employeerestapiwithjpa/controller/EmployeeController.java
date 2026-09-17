package com.example.employeerestapiwithjpa.controller;


import com.example.employeerestapiwithjpa.model.Employee;
import com.example.employeerestapiwithjpa.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@CrossOrigin(origins = "*")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }
    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmplyees();
    }
    @GetMapping("/{id}")
    public Employee GetEmployeeById(@PathVariable int id){
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public Employee addNewEmployee(@RequestBody Employee newEmployee){
        return employeeService.addEmployee(newEmployee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable int id, @RequestBody Employee updatedEmployee)
    {
        return employeeService.updateEmployee(id,updatedEmployee);
    }
    @DeleteMapping("/{id}")
    public String deleteEmployee(@PathVariable  int id){
        boolean result=employeeService.deleteEmployee(id);
        if(result)
        {
            return "Employee deleted successfully";
        }
        else {
            return "Employee Not Found";
        }
    }

}

