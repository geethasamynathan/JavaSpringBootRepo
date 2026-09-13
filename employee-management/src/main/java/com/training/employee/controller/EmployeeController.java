package com.training.employee.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.training.employee.model.Employee;
import com.training.employee.service.EmployeeService;


@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @GetMapping
    public List<Employee> getAllEmployees(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmplopyeeById(@PathVariable  int id){
        return employeeService.getEmployeeById(id);
    }

    @PostMapping
    public Employee AddEmplyee(@RequestBody  Employee employee){
       return  employeeService.addEmployee(employee);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(@PathVariable int id,
                                   @RequestBody Employee employee)
    {
        return employeeService.updateEmployee(id,employee);
    }
    @DeleteMapping("/{id}")
    public String deleteemployee(@PathVariable int id){
        boolean deleted= employeeService.deleteEmployee(id);
        if(deleted)
        {
            return "Employee deleted Successfully";
        }
        return "Employee not Found";
    }
}
