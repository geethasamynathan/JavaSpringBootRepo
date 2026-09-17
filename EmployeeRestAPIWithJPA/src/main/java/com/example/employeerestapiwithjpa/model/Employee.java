package com.example.employeerestapiwithjpa.model;


import jakarta.persistence.*;

@Entity
@Table(name="employee")
public class Employee {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "employee_id")
        private Integer employeeId;

        @Column(name = "employee_name")
        private String employeeName;

        private Integer age;

        private Double salary;

        private String department;

        @Column(name = "permanent_employee")
        private Boolean permanentEmployee;

        public Employee() {
        }

        public Integer getEmployeeId() {
            return employeeId;
        }

        public void setEmployeeId(Integer employeeId) {
            this.employeeId = employeeId;
        }

        public String getEmployeeName() {
            return employeeName;
        }

        public void setEmployeeName(String employeeName) {
            this.employeeName = employeeName;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public Double getSalary() {
            return salary;
        }

        public void setSalary(Double salary) {
            this.salary = salary;
        }

        public String getDepartment() {
            return department;
        }

        public void setDepartment(String department) {
            this.department = department;
        }

        public Boolean getPermanentEmployee() {
            return permanentEmployee;
        }

        public void setPermanentEmployee(Boolean permanentEmployee) {
            this.permanentEmployee = permanentEmployee;
        }
    }