<div align="center">

# <span style="color:#7C3AED;">🌐 REST Principles for Freshers</span>

## <span style="color:#2563EB;">Spring Boot + IntelliJ IDEA + HTTP Methods</span>

### <span style="color:#059669;">Simple Hardcoded Employee REST API</span>

</div>

---

# <span style="color:#DC2626;">1. What is REST?</span>

**REST** stands for:

> **REpresentational State Transfer**

REST is an architectural style used to build **web services / APIs**.

A REST API allows two applications to communicate using **HTTP**.

```text
Frontend Application
        |
        | HTTP Request
        v
Spring Boot REST API
        |
        | Process Request
        v
Business Logic / Database
```

A client can be:

- Browser
- React Application
- Angular Application
- Mobile Application
- Postman
- Another Java Application

The Spring Boot application acts as the **server**.

---

# <span style="color:#F59E0B;">2. Simple Real-World Example</span>

Imagine an **Employee Management System**.

We may need to:

- View employees
- Add employees
- Update employees
- Delete employees

REST maps these operations to HTTP methods.

| Requirement | HTTP Method | Example URL |
|---|---|---|
| Get all employees | `GET` | `/employees` |
| Get one employee | `GET` | `/employees/101` |
| Create employee | `POST` | `/employees` |
| Update employee | `PUT` | `/employees/101` |
| Partially update employee | `PATCH` | `/employees/101` |
| Delete employee | `DELETE` | `/employees/101` |

---

# <span style="color:#8B5CF6;">3. What is a Resource?</span>

REST mainly works with **resources**.

Examples:

```text
Employee
Student
Customer
Product
Order
Account
```

For example:

```text
/employees
```

represents the **Employee resource**.

Instead of:

```text
/getEmployees
/createEmployee
/updateEmployee
/deleteEmployee
```

REST normally uses:

```text
GET     /employees
POST    /employees
PUT     /employees/101
DELETE  /employees/101
```

The **HTTP method tells us what action to perform**.

---

# <span style="color:#0EA5E9;">4. REST URL Design</span>

## Recommended

```text
/employees
/employees/101
/products
/products/200
/orders
```

## Avoid

```text
/getEmployees
/addEmployee
/updateEmployee
/deleteEmployee
```

Use **nouns** in REST URLs because the HTTP method already represents the action.

---

# <span style="color:#16A34A;">5. Important REST Principles</span>

## 5.1 Client-Server

Client and server are separate.

```text
Client
   |
   | HTTP
   v
Server
```

Example:

```text
React Application
       |
       v
Spring Boot REST API
```

---

## 5.2 Stateless

Each request should contain enough information for the server to process it.

Example:

```text
GET /employees/101
```

The server knows exactly which employee is requested.

---

## 5.3 Resource-Based URLs

Examples:

```text
/employees
/products
/customers
/orders
```

---

## 5.4 HTTP Methods

REST commonly uses:

```text
GET
POST
PUT
PATCH
DELETE
```

---

## 5.5 JSON Data

REST APIs commonly send and receive **JSON**.

Example:

```json
{
  "id": 101,
  "name": "Ravi",
  "department": "IT"
}
```

---

# <span style="color:#E11D48;">6. HTTP Methods</span>

| HTTP Method | Purpose |
|---|---|
| `GET` | Read data |
| `POST` | Create data |
| `PUT` | Full update / replacement |
| `PATCH` | Partial update |
| `DELETE` | Delete data |

### Easy Memory Trick

```text
GET     → Read
POST    → Create
PUT     → Full Update
PATCH   → Partial Update
DELETE  → Delete
```

---

# <span style="color:#2563EB;">7. Create the Project in IntelliJ IDEA</span>

Open IntelliJ IDEA.

```text
File
  ↓
New
  ↓
Project
```

Choose:

```text
Spring Boot
```

or, depending on the IntelliJ version:

```text
Spring Initializr
```

---

# <span style="color:#9333EA;">8. Project Configuration</span>

Use:

```text
Name:
rest-demo

Group:
com.example

Artifact:
rest-demo

Package:
com.example.restdemo

Language:
Java

Build System:
Maven

JDK:
Java 21

Packaging:
Jar
```

---

# <span style="color:#EA580C;">9. Dependency Required</span>

For this beginner REST project, select only:

## ✅ Spring Web

Spring Web gives us annotations such as:

```java
@RestController
@GetMapping
@PostMapping
@PutMapping
@PatchMapping
@DeleteMapping
@RequestMapping
@PathVariable
@RequestBody
@RequestParam
```

The Maven dependency is:

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

No database dependency is required for this example.

---

# <span style="color:#0891B2;">10. Project Structure</span>

```text
rest-demo
│
├── src
│   └── main
│       ├── java
│       │   └── com.example.restdemo
│       │       ├── RestDemoApplication.java
│       │       ├── Employee.java
│       │       └── EmployeeController.java
│       │
│       └── resources
│           └── application.properties
│
└── pom.xml
```

---

# <span style="color:#4F46E5;">11. Main Spring Boot Class</span>

```java
package com.example.restdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RestDemoApplication {

    public static void main(String[] args) {

        SpringApplication.run(RestDemoApplication.class, args);

    }
}
```

By default, Spring Boot runs on:

```text
http://localhost:8080
```

---

# <span style="color:#059669;">12. Create Employee Class</span>

Create:

```text
Employee.java
```

```java
package com.example.restdemo;

public class Employee {

    private int id;
    private String name;
    private String department;
    private double salary;

    public Employee() {
    }

    public Employee(int id,
                    String name,
                    String department,
                    double salary) {

        this.id = id;
        this.name = name;
        this.department = department;
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public double getSalary() {
        return salary;
    }

    public void setSalary(double salary) {
        this.salary = salary;
    }
}
```

---

# <span style="color:#D946EF;">13. Create REST Controller</span>

Create:

```text
EmployeeController.java
```

Start with hardcoded data:

```java
package com.example.restdemo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private List<Employee> employees = new ArrayList<>();

    public EmployeeController() {

        employees.add(
                new Employee(
                        101,
                        "Ravi",
                        "IT",
                        50000
                )
        );

        employees.add(
                new Employee(
                        102,
                        "Priya",
                        "HR",
                        45000
                )
        );

        employees.add(
                new Employee(
                        103,
                        "Arun",
                        "Finance",
                        55000
                )
        );
    }
}
```

For learning purposes, think of this `ArrayList` as a temporary database.

```text
101  Ravi   IT       50000
102  Priya  HR       45000
103  Arun   Finance  55000
```

---

# <span style="color:#22C55E;">14. GET Method – Read All Employees</span>

```java
@GetMapping
public List<Employee> getAllEmployees() {

    return employees;
}
```

Request:

```text
GET http://localhost:8080/employees
```

Example response:

```json
[
  {
    "id": 101,
    "name": "Ravi",
    "department": "IT",
    "salary": 50000.0
  },
  {
    "id": 102,
    "name": "Priya",
    "department": "HR",
    "salary": 45000.0
  },
  {
    "id": 103,
    "name": "Arun",
    "department": "Finance",
    "salary": 55000.0
  }
]
```

---

# <span style="color:#10B981;">15. GET Method – Read One Employee</span>

```java
@GetMapping("/{id}")
public Employee getEmployee(@PathVariable int id) {

    for (Employee employee : employees) {

        if (employee.getId() == id) {

            return employee;
        }
    }

    return null;
}
```

Request:

```text
GET http://localhost:8080/employees/101
```

Response:

```json
{
  "id": 101,
  "name": "Ravi",
  "department": "IT",
  "salary": 50000.0
}
```

`101` is taken from the URL using:

```java
@PathVariable
```

Flow:

```text
/employees/101
       |
       v
@PathVariable
       |
       v
int id = 101
```

---

# <span style="color:#F97316;">16. POST Method – Create Employee</span>

```java
@PostMapping
public Employee addEmployee(
        @RequestBody Employee employee) {

    employees.add(employee);

    return employee;
}
```

Request:

```text
POST http://localhost:8080/employees
```

Body:

```json
{
  "id": 104,
  "name": "Kumar",
  "department": "Sales",
  "salary": 60000
}
```

Spring converts the JSON into a Java object using:

```java
@RequestBody
```

Flow:

```text
JSON
  |
  v
@RequestBody
  |
  v
Employee Object
```

---

# <span style="color:#F59E0B;">17. PUT Method – Full Update</span>

```java
@PutMapping("/{id}")
public Employee updateEmployee(
        @PathVariable int id,
        @RequestBody Employee updatedEmployee) {

    for (Employee employee : employees) {

        if (employee.getId() == id) {

            employee.setName(updatedEmployee.getName());
            employee.setDepartment(updatedEmployee.getDepartment());
            employee.setSalary(updatedEmployee.getSalary());

            return employee;
        }
    }

    return null;
}
```

Request:

```text
PUT http://localhost:8080/employees/101
```

Body:

```json
{
  "name": "Ravi Kumar",
  "department": "Development",
  "salary": 65000
}
```

Before:

```text
101 Ravi IT 50000
```

After:

```text
101 Ravi Kumar Development 65000
```

---

# <span style="color:#A855F7;">18. PATCH Method – Partial Update</span>

Here we update only salary.

```java
@PatchMapping("/{id}/salary")
public Employee updateSalary(
        @PathVariable int id,
        @RequestParam double salary) {

    for (Employee employee : employees) {

        if (employee.getId() == id) {

            employee.setSalary(salary);

            return employee;
        }
    }

    return null;
}
```

Request:

```text
PATCH http://localhost:8080/employees/101/salary?salary=70000
```

Here:

```text
salary=70000
```

is a request parameter read using:

```java
@RequestParam
```

---

# <span style="color:#EF4444;">19. DELETE Method – Delete Employee</span>

```java
@DeleteMapping("/{id}")
public String deleteEmployee(
        @PathVariable int id) {

    for (Employee employee : employees) {

        if (employee.getId() == id) {

            employees.remove(employee);

            return "Employee deleted successfully";
        }
    }

    return "Employee not found";
}
```

Request:

```text
DELETE http://localhost:8080/employees/101
```

Response:

```text
Employee deleted successfully
```

---

# <span style="color:#2563EB;">20. Complete EmployeeController Code</span>

```java
package com.example.restdemo;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private List<Employee> employees = new ArrayList<>();


    public EmployeeController() {

        employees.add(
                new Employee(
                        101,
                        "Ravi",
                        "IT",
                        50000
                )
        );

        employees.add(
                new Employee(
                        102,
                        "Priya",
                        "HR",
                        45000
                )
        );

        employees.add(
                new Employee(
                        103,
                        "Arun",
                        "Finance",
                        55000
                )
        );
    }


    // GET ALL EMPLOYEES

    @GetMapping
    public List<Employee> getAllEmployees() {

        return employees;
    }


    // GET ONE EMPLOYEE

    @GetMapping("/{id}")
    public Employee getEmployee(
            @PathVariable int id) {

        for (Employee employee : employees) {

            if (employee.getId() == id) {

                return employee;
            }
        }

        return null;
    }


    // POST - CREATE EMPLOYEE

    @PostMapping
    public Employee addEmployee(
            @RequestBody Employee employee) {

        employees.add(employee);

        return employee;
    }


    // PUT - UPDATE EMPLOYEE

    @PutMapping("/{id}")
    public Employee updateEmployee(
            @PathVariable int id,
            @RequestBody Employee updatedEmployee) {

        for (Employee employee : employees) {

            if (employee.getId() == id) {

                employee.setName(
                        updatedEmployee.getName()
                );

                employee.setDepartment(
                        updatedEmployee.getDepartment()
                );

                employee.setSalary(
                        updatedEmployee.getSalary()
                );

                return employee;
            }
        }

        return null;
    }


    // PATCH - UPDATE ONLY SALARY

    @PatchMapping("/{id}/salary")
    public Employee updateSalary(
            @PathVariable int id,
            @RequestParam double salary) {

        for (Employee employee : employees) {

            if (employee.getId() == id) {

                employee.setSalary(salary);

                return employee;
            }
        }

        return null;
    }


    // DELETE EMPLOYEE

    @DeleteMapping("/{id}")
    public String deleteEmployee(
            @PathVariable int id) {

        for (Employee employee : employees) {

            if (employee.getId() == id) {

                employees.remove(employee);

                return "Employee deleted successfully";
            }
        }

        return "Employee not found";
    }
}
```

---

# <span style="color:#0891B2;">21. Important REST Annotations</span>

| Annotation | Purpose |
|---|---|
| `@RestController` | Defines a REST controller |
| `@RequestMapping` | Defines a common URL path |
| `@GetMapping` | Handles GET request |
| `@PostMapping` | Handles POST request |
| `@PutMapping` | Handles PUT request |
| `@PatchMapping` | Handles PATCH request |
| `@DeleteMapping` | Handles DELETE request |
| `@PathVariable` | Reads a value from URL path |
| `@RequestBody` | Converts JSON request body to Java object |
| `@RequestParam` | Reads query parameters |

---

# <span style="color:#16A34A;">22. How to Run</span>

Open:

```text
RestDemoApplication.java
```

Click:

```text
▶ Run
```

You should see output similar to:

```text
Started RestDemoApplication
```

and:

```text
Tomcat started on port 8080
```

Your application is now available at:

```text
http://localhost:8080
```

---

# <span style="color:#F97316;">23. Test GET in Browser</span>

Open:

```text
http://localhost:8080/employees
```

The browser is convenient mainly for **GET** requests.

For POST, PUT, PATCH, and DELETE, use:

- IntelliJ HTTP Client
- Postman

---

# <span style="color:#7C3AED;">24. Test with IntelliJ HTTP Client</span>

Create a file:

```text
requests.http
```

Paste:

```http
### GET ALL EMPLOYEES

GET http://localhost:8080/employees


### GET ONE EMPLOYEE

GET http://localhost:8080/employees/101


### CREATE EMPLOYEE

POST http://localhost:8080/employees
Content-Type: application/json

{
  "id": 104,
  "name": "Kumar",
  "department": "Sales",
  "salary": 60000
}


### UPDATE EMPLOYEE

PUT http://localhost:8080/employees/104
Content-Type: application/json

{
  "name": "Kumar Raj",
  "department": "Marketing",
  "salary": 65000
}


### UPDATE ONLY SALARY

PATCH http://localhost:8080/employees/104/salary?salary=70000


### DELETE EMPLOYEE

DELETE http://localhost:8080/employees/104
```

Click the green arrow near each request to execute it.

---

# <span style="color:#DC2626;">25. Complete REST Flow</span>

```text
          CLIENT
 Browser / Postman / IntelliJ
              |
              | HTTP Request
              v
+-------------------------------+
| EmployeeController            |
|                               |
| GET     /employees            |
| GET     /employees/{id}       |
| POST    /employees            |
| PUT     /employees/{id}       |
| PATCH   /employees/{id}/...   |
| DELETE  /employees/{id}       |
+---------------+---------------+
                |
                v
        Employee Objects
                |
                v
        Hardcoded ArrayList
```

Later, the `ArrayList` can be replaced by:

```text
H2
MySQL
PostgreSQL
Oracle
SQL Server
```

---

# <span style="color:#0EA5E9;">26. HTTP Methods Quick Reference</span>

| Method | Purpose | Example |
|---|---|---|
| `GET` | Read all | `GET /employees` |
| `GET` | Read one | `GET /employees/101` |
| `POST` | Create | `POST /employees` |
| `PUT` | Full update | `PUT /employees/101` |
| `PATCH` | Partial update | `PATCH /employees/101/salary` |
| `DELETE` | Delete | `DELETE /employees/101` |

---

# <span style="color:#D946EF;">27. POST vs PUT vs PATCH</span>

## POST

Used to create new data.

```text
POST /employees
```

```json
{
  "id": 105,
  "name": "Anu",
  "department": "HR",
  "salary": 50000
}
```

---

## PUT

Used for a complete update or replacement.

```text
PUT /employees/105
```

```json
{
  "name": "Anu Kumar",
  "department": "Finance",
  "salary": 60000
}
```

Think:

> **PUT = Complete Update**

---

## PATCH

Used to modify only selected fields.

```text
PATCH /employees/105/salary?salary=65000
```

Think:

> **PATCH = Partial Update**

---

# <span style="color:#059669;">28. CRUD and HTTP Mapping</span>

```text
CRUD                     HTTP

Create        -------->  POST

Read          -------->  GET

Update        -------->  PUT / PATCH

Delete        -------->  DELETE
```

This is important for interviews.

---

# <span style="color:#F59E0B;">29. Beginner REST Architecture</span>

For this first project:

```text
Client
   |
   v
Controller
   |
   v
ArrayList
```

Later, a real-world Spring Boot project normally becomes:

```text
Client
   |
   v
Controller
   |
   v
Service
   |
   v
Repository
   |
   v
JPA / Hibernate
   |
   v
Database
```

Example:

```text
Postman
   |
   v
EmployeeController
   |
   v
EmployeeService
   |
   v
EmployeeRepository
   |
   v
JPA / Hibernate
   |
   v
H2 / MySQL
```

---

# <span style="color:#7C3AED;">30. Fresher REST Cheat Sheet</span>

```text
REST
 |
 +-- Resource
 |      |
 |      +-- Employee
 |
 +-- URL
 |      |
 |      +-- /employees
 |
 +-- HTTP Methods
        |
        +-- GET     -> Read
        |
        +-- POST    -> Create
        |
        +-- PUT     -> Full Update
        |
        +-- PATCH   -> Partial Update
        |
        +-- DELETE  -> Delete
```

---

<div align="center">

# <span style="color:#16A34A;">✅ Key Takeaway</span>

For your **first IntelliJ REST project**, use:

### <span style="color:#2563EB;">Java 21 + Maven + Spring Web</span>

Start with the hardcoded `ArrayList` example.

Once REST and HTTP methods are clear, move to:

### <span style="color:#9333EA;">Controller → Service → Repository → JPA → H2 Database</span>

</div>
