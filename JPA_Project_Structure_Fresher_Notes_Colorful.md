<div align="center">

# <span style="color:#7C3AED;">🌈 JPA Project Structure — Fresher Notes</span>

### <span style="color:#2563EB;">Spring Boot + Spring Data JPA + H2 Database</span>

**Step-by-Step Beginner Guide with CRUD, Project Structure, Flow, and Testing**

---

</div>

> 🧑‍💻 **Use Case:** Employee Management Application  
> 🎯 **Goal:** Understand Entity → Repository → Service → Controller → Database flow clearly.

We will create a simple:

**Employee Management Application**

The project will support:

```
```

```
Create Employee
Read Employee
Update Employee
Delete Employee
```

## 1. Create Spring Boot Project

Go to Spring Initializr or create a Spring Boot project from IntelliJ/STS.

Choose:

```
```

```
Project: Maven
Language: Java
Spring Boot: Current stable version
Java: 21
Packaging: Jar
```

Example project details:

```
```

```
Group: com.example
Artifact: jpademo
Name: jpademo
Package Name: com.example.jpademo
```

Add these dependencies:

```
```

```
Spring Web
Spring Data JPA
H2 Database
```

After creating the project, open it in IntelliJ or STS.

---

---

# 2. Project Folder Structure

Your project should look like this:

```
```

```
jpademo
│
├── src
│   ├── main
│   │   ├── java
│   │   │   └── com.example.jpademo
│   │   │       │
│   │   │       ├── JpaDemoApplication.java
│   │   │       │
│   │   │       ├── controller
│   │   │       │   └── EmployeeController.java
│   │   │       │
│   │   │       ├── service
│   │   │       │   └── EmployeeService.java
│   │   │       │
│   │   │       ├── repository
│   │   │       │   └── EmployeeRepository.java
│   │   │       │
│   │   │       └── entity
│   │   │           └── Employee.java
│   │   │
│   │   └── resources
│   │       └── application.properties
│   │
│   └── test
│       └── java
│
├── pom.xml
└── mvnw
```

The important folders are:

```
```

```
entity
repository
service
controller
```

---

---

# 3. Understand the Flow

The application flow is:

```
```

```
Client
  ↓
Controller
  ↓
Service
  ↓
Repository
  ↓
JPA
  ↓
Hibernate
  ↓
H2 Database
```

Each layer has a specific job.

---

---

# 4. Entity Layer

Create package:

```
```

```
com.example.jpademo.entity
```

Create:

```
```

```
Employee.java
```

Code:

```
```

```
package com.example.jpademo.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long employeeId;

    private String employeeName;

    private int age;

    private double salary;

    private String department;

    public Employee() {
    }

    public Employee(String employeeName,
                    int age,
                    double salary,
                    String department) {

        this.employeeName = employeeName;
        this.age = age;
        this.salary = salary;
        this.department = department;
    }

    public Long getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(Long employeeId) {
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
}
```

What this means:

```
```

```
Employee class
      ↓
employees table
```

Fields become columns.

---

---

# 5. Repository Layer

Create package:

```
```

```
com.example.jpademo.repository
```

Create:

```
```

```
EmployeeRepository.java
```

Code:

```
```

```
package com.example.jpademo.repository;

import com.example.jpademo.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository
        extends JpaRepository<Employee, Long> {
}
```

That single interface gives us:

```
```

```
save()
findAll()
findById()
deleteById()
count()
existsById()
```

You do not need to write SQL for basic CRUD operations.

---

---

# 6. Service Layer

Create package:

```
```

```
com.example.jpademo.service
```

Create:

```
```

```
EmployeeService.java
```

Code:

```
```

```
package com.example.jpademo.service;

import com.example.jpademo.entity.Employee;
import com.example.jpademo.repository.EmployeeRepository;
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
```

The Service layer contains business logic.

For example:

```
```

```
Controller should not directly contain all database logic.
```

Instead:

```
```

```
Controller
   ↓
Service
   ↓
Repository
```

---

---

# 7. Controller Layer

Create package:

```
```

```
com.example.jpademo.controller
```

Create:

```
```

```
EmployeeController.java
```

Code:

```
```

```
package com.example.jpademo.controller;

import com.example.jpademo.entity.Employee;
import com.example.jpademo.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(
            EmployeeService employeeService) {

        this.employeeService = employeeService;
    }

    @PostMapping
    public Employee addEmployee(
            @RequestBody Employee employee) {

        return employeeService.addEmployee(employee);
    }

    @GetMapping
    public List<Employee> getAllEmployees() {

        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public Employee getEmployeeById(
            @PathVariable Long id) {

        return employeeService.getEmployeeById(id);
    }

    @PutMapping("/{id}")
    public Employee updateEmployee(
            @PathVariable Long id,
            @RequestBody Employee employee) {

        return employeeService
                .updateEmployee(id, employee);
    }

    @DeleteMapping("/{id}")
    public String deleteEmployee(
            @PathVariable Long id) {

        boolean deleted =
                employeeService.deleteEmployee(id);

        if (deleted) {
            return "Employee deleted successfully";
        }

        return "Employee not found";
    }
}
```

Now the REST APIs are:

```
```

```
POST    /employees
GET     /employees
GET     /employees/{id}
PUT     /employees/{id}
DELETE  /employees/{id}
```

---

---

# 8. Configure H2 Database

Open:

```
```

```
src/main/resources/application.properties
```

Add:

```
```

```
spring.application.name=jpademo

spring.datasource.url=jdbc:h2:mem:employeedb
spring.datasource.driver-class-name=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

spring.jpa.hibernate.ddl-auto=update

spring.jpa.show-sql=true

spring.h2.console.enabled=true
spring.h2.console.path=/h2-console
```

Important property:

```
```

```
spring.jpa.show-sql=true
```

This allows you to see Hibernate-generated SQL in the console.

---

---

# 9. Main Application Class

Spring Boot automatically creates a main class.

Example:

```
```

```
package com.example.jpademo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JpaDemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                JpaDemoApplication.class,
                args);
    }
}
```

This starts the complete application.

---

---

# 10. Why Main Class Should Be in Parent Package

Keep your main class here:

```
```

```
com.example.jpademo
```

And child packages:

```
```

```
com.example.jpademo.controller
com.example.jpademo.service
com.example.jpademo.repository
com.example.jpademo.entity
```

Because `@SpringBootApplication` scans child packages automatically.

Correct structure:

```
```

```
com.example.jpademo
│
├── JpaDemoApplication
├── controller
├── service
├── repository
└── entity
```

Avoid placing the main class deep inside another package.

---

---

# 11. Check `pom.xml`

Your `pom.xml` should contain dependencies similar to:

```
```

```
<dependencies>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-data-jpa</artifactId>
    </dependency>

    <dependency>
        <groupId>com.h2database</groupId>
        <artifactId>h2</artifactId>
        <scope>runtime</scope>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-test</artifactId>
        <scope>test</scope>
    </dependency>

</dependencies>
```

---

---

# 12. Run the Application

Run:

```
```

```
JpaDemoApplication.java
```

In IntelliJ:

```
```

```
Right click
    ↓
Run 'JpaDemoApplication'
```

In STS:

```
```

```
Right click project
    ↓
Run As
    ↓
Spring Boot App
```

You should see something similar:

```
```

```
Started JpaDemoApplication
Tomcat started on port 8080
```

---

---

# 13. Test CREATE Employee

Use Postman.

Request:

```
```

```
POST http://localhost:8080/employees
```

Body:

```
```

```
{
  "employeeName": "Arun",
  "age": 25,
  "salary": 50000,
  "department": "IT"
}
```

Select:

```
```

```
Body
↓
raw
↓
JSON
```

Response may be:

```
```

```
{
  "employeeId": 1,
  "employeeName": "Arun",
  "age": 25,
  "salary": 50000.0,
  "department": "IT"
}
```

---

---

# 14. What Happens Internally for POST

When you call:

```
```

```
POST /employees
```

the flow is:

```
```

```
Postman
   ↓
EmployeeController
   ↓
EmployeeService
   ↓
EmployeeRepository
   ↓
JpaRepository.save()
   ↓
Hibernate
   ↓
INSERT SQL
   ↓
H2 Database
```

Hibernate may generate something like:

```
```

```
insert into employees
(age, department, employee_name, salary)
values (?, ?, ?, ?)
```

---

---

# 15. Test GET All Employees

Request:

```
```

```
GET http://localhost:8080/employees
```

Response:

```
```

```
[
  {
    "employeeId": 1,
    "employeeName": "Arun",
    "age": 25,
    "salary": 50000.0,
    "department": "IT"
  }
]
```

Internally:

```
```

```
GET
 ↓
Controller
 ↓
Service
 ↓
repository.findAll()
 ↓
Hibernate
 ↓
SELECT
```

---

---

# 16. Test GET Employee By ID

Request:

```
```

```
GET http://localhost:8080/employees/1
```

Repository method:

```
```

```
employeeRepository.findById(1L);
```

Hibernate performs something similar to:

```
```

```
select *
from employees
where employee_id = 1;
```

---

---

# 17. Test UPDATE

Request:

```
```

```
PUT http://localhost:8080/employees/1
```

Body:

```
```

```
{
  "employeeName": "Arun Kumar",
  "age": 26,
  "salary": 65000,
  "department": "Development"
}
```

Flow:

```
```

```
Find employee
     ↓
Modify Java object
     ↓
save()
     ↓
Hibernate
     ↓
UPDATE
```

---

---

# 18. Test DELETE

Request:

```
```

```
DELETE http://localhost:8080/employees/1
```

Response:

```
```

```
Employee deleted successfully
```

Repository performs:

```
```

```
employeeRepository.deleteById(id);
```

---

---

# 19. View H2 Database

Open browser:

```
```

```
http://localhost:8080/h2-console
```

Enter:

```
```

```
JDBC URL:
jdbc:h2:mem:employeedb

User Name:
sa

Password:
leave blank
```

Click:

```
```

```
Connect
```

Then run:

```
```

```
SELECT * FROM EMPLOYEES;
```

You can see the actual database rows.

---

---

# 20. Full Layer Responsibility

## Entity

Represents database table.

```
```

```
Employee.java
```

Example responsibility:

```
```

```
Employee fields
Table mapping
Primary key
Column mapping
```

---

## Repository

Communicates with database.

```
```

```
EmployeeRepository.java
```

Contains database operations.

```
```

```
save
find
delete
```

---

## Service

Contains business logic.

```
```

```
EmployeeService.java
```

Example:

```
```

```
Salary validation
Employee existence check
Calculations
Rules
```

---

## Controller

Receives HTTP requests.

```
```

```
EmployeeController.java
```

Examples:

```
```

```
POST
GET
PUT
DELETE
```

---

---

# 21. Easy Analogy

Think about a restaurant.

```
```

```
Customer
   ↓
Waiter
   ↓
Manager / Chef
   ↓
Store Room
```

Mapping:

```
```

```
Customer
=
Postman / Frontend

Waiter
=
Controller

Manager / Chef
=
Service

Store Room Handler
=
Repository

Store Room
=
Database
```

Controller should not directly do all business/database work.

---

---

# 22. Full Architecture

```
```

```
                CLIENT
             Postman / UI
                  │
                  ▼
        EmployeeController
                  │
                  ▼
          EmployeeService
                  │
                  ▼
        EmployeeRepository
                  │
                  ▼
          Spring Data JPA
                  │
                  ▼
             Hibernate
                  │
                  ▼
                JDBC
                  │
                  ▼
            H2 Database
```

---

---

# 23. Package Structure Explained

```
```

```
com.example.jpademo
│
├── entity
│      Employee.java
│
├── repository
│      EmployeeRepository.java
│
├── service
│      EmployeeService.java
│
├── controller
│      EmployeeController.java
│
└── JpaDemoApplication.java
```

Remember:

```
```

```
Entity
↓
Repository
↓
Service
↓
Controller
```

But request flow is the reverse:

```
```

```
Controller
↓
Service
↓
Repository
↓
Database
```

---

---

# 24. Database Flow for CREATE

Java:

```
```

```
employeeRepository.save(employee);
```

JPA/Hibernate:

```
```

```
Employee Object
      ↓
JPA
      ↓
Hibernate
      ↓
INSERT SQL
      ↓
EMPLOYEES table
```

---

---

# 25. Database Flow for READ

Java:

```
```

```
employeeRepository.findAll();
```

Hibernate:

```
```

```
SELECT * FROM employees;
```

Then:

```
```

```
Database rows
     ↓
Hibernate
     ↓
Employee Objects
     ↓
Service
     ↓
Controller
     ↓
JSON Response
```

---

---

# 26. Database Flow for UPDATE

```
```

```
GET Employee
     ↓
Modify Employee Object
     ↓
repository.save()
     ↓
Hibernate
     ↓
UPDATE employees
```

---

---

# 27. Database Flow for DELETE

```
```

```
repository.deleteById(1L);
```

becomes approximately:

```
```

```
DELETE
FROM employees
WHERE employee_id = 1;
```

---

---

# 28. Most Important Files to Remember

For your first JPA project, remember these five files:

```
```

```
1. Employee.java
2. EmployeeRepository.java
3. EmployeeService.java
4. EmployeeController.java
5. application.properties
```

Plus the main class:

```
```

```
JpaDemoApplication.java
```

---

---

# 29. Final Fresher Memory Trick

Remember:

```
```

```
ENTITY
What data?

REPOSITORY
How do I access database?

SERVICE
What should application do?

CONTROLLER
How does client call application?

DATABASE
Where is data stored?
```

Full flow:

```
```

```
POSTMAN
  ↓
CONTROLLER
  ↓
SERVICE
  ↓
REPOSITORY
  ↓
JPA
  ↓
HIBERNATE
  ↓
DATABASE
```

This structure is the foundation for most real Spring Boot + JPA applications.

---

<div align="center">

## <span style="color:#059669;">✅ Final Memory Flow</span>

**POSTMAN / UI → CONTROLLER → SERVICE → REPOSITORY → JPA → HIBERNATE → DATABASE**

💡 *Remember: Entity = Data, Repository = DB Access, Service = Business Logic, Controller = API Entry Point.*

</div>
