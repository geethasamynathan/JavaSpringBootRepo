//package com.jdbc.jpademo;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.Id;
//import jakarta.persistence.Table;
//
//@Entity
//@Table(name = "employee")
//public class Employee {
//
//    @Id
//    private int employeeId;
//
//    private String employeeName;
//
//    private int age;
//
//    private double salary;
//
//    private String department;
//
//    private boolean permanentEmployee;
//
//    public Employee() {
//    }
//
//    public Employee(
//            int employeeId,
//            String employeeName,
//            int age,
//            double salary,
//            String department,
//            boolean permanentEmployee) {
//
//        this.employeeId = employeeId;
//        this.employeeName = employeeName;
//        this.age = age;
//        this.salary = salary;
//        this.department = department;
//        this.permanentEmployee = permanentEmployee;
//    }
//
//    public int getEmployeeId() {
//        return employeeId;
//    }
//
//    public void setEmployeeId(int employeeId) {
//        this.employeeId = employeeId;
//    }
//
//    public String getEmployeeName() {
//        return employeeName;
//    }
//
//    public void setEmployeeName(String employeeName) {
//        this.employeeName = employeeName;
//    }
//
//    public int getAge() {
//        return age;
//    }
//
//    public void setAge(int age) {
//        this.age = age;
//    }
//
//    public double getSalary() {
//        return salary;
//    }
//
//    public void setSalary(double salary) {
//        this.salary = salary;
//    }
//
//    public String getDepartment() {
//        return department;
//    }
//
//    public void setDepartment(String department) {
//        this.department = department;
//    }
//
//    public boolean isPermanentEmployee() {
//        return permanentEmployee;
//    }
//
//    public void setPermanentEmployee(boolean permanentEmployee) {
//        this.permanentEmployee = permanentEmployee;
//    }
//
//    @Override
//    public String toString() {
//
//        return "Employee{" +
//                "employeeId=" + employeeId +
//                ", employeeName='" + employeeName + '\'' +
//                ", age=" + age +
//                ", salary=" + salary +
//                ", department='" + department + '\'' +
//                ", permanentEmployee=" + permanentEmployee +
//                '}';
//    }
//}

package com.jdbc.jpademo;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employee")
public class Employee {

    @Id
    private int employeeId;

    private String employeeName;
    private int age;
    private double salary;
    private String department;
    private boolean permanentEmployee;

    public Employee() {
    }

    public Employee(
            int employeeId,
            String employeeName,
            int age,
            double salary,
            String department,
            boolean permanentEmployee) {

        this.employeeId = employeeId;
        this.employeeName = employeeName;
        this.age = age;
        this.salary = salary;
        this.department = department;
        this.permanentEmployee = permanentEmployee;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public boolean isPermanentEmployee() {
        return permanentEmployee;
    }

    public void setPermanentEmployee(boolean permanentEmployee) {
        this.permanentEmployee = permanentEmployee;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + employeeId +
                ", employeeName='" + employeeName + '\'' +
                ", age=" + age +
                ", salary=" + salary +
                ", department='" + department + '\'' +
                ", permanentEmployee=" + permanentEmployee +
                '}';
    }
}