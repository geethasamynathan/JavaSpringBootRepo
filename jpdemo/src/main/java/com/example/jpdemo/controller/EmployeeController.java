package com.example.jpdemo.controller;
import com.example.jpdemo.entity.Employee;
import com.example.jpdemo.repository.EmployeeRepository;
import com.example.jpdemo.service.EmployeeService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;
    public EmployeeController(
            EmployeeService employeeService)
    {
        this.employeeService=employeeService;
    }
    @PostMapping
    public Employee addEMployee(
            @RequestBody Employee employee)
    {
        return employeeService.addEmployee(employee);
    }
    @GetMapping("/{id}")
    public Employee getEmployeeById(@PathVariable Long id)
    {
        return employeeService.getEmployeeById(id);
    }
    @PutMapping("/{id}")
    public Employee updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee)
    {
        return employeeService.updateEmployee(id,employee);
    }
    @DeleteMapping("/{id}")
    public String deleteEmplopyee(
            @PathVariable Long id){
        boolean deleted=employeeService.deleteEmployee((id));
        if(deleted){
            return "Employee deleted successfully";
        }
        return "employee not found";
    }


}
