# 🌱 Spring Boot CRUD with MySQL Without JPA

## 🎯 Objective

In this example, we will build a simple **Employee CRUD REST API** using:

- Java 21
- Spring Boot
- IntelliJ IDEA
- MySQL
- Spring JDBC
- `JdbcTemplate`
- Postman

We will **not use JPA, Hibernate, Entity, Repository, or JpaRepository**.

The purpose is to first understand the difficulties of manually working with SQL and JDBC. After that, students can clearly understand how JPA simplifies database programming.

---

# 🏗️ Application Architecture

```text
Postman / Browser
       |
       v
@RestController
       |
       v
JdbcTemplate
       |
       v
MySQL Database
```

We will implement the following CRUD operations:

| Operation | HTTP Method | URL |
|---|---|---|
| Create Employee | POST | `/employees` |
| Read All Employees | GET | `/employees` |
| Read Employee by ID | GET | `/employees/{id}` |
| Update Employee | PUT | `/employees/{id}` |
| Delete Employee | DELETE | `/employees/{id}` |

---

# 1️⃣ Required Software

Install or verify:

- Java 21
- IntelliJ IDEA
- MySQL Server
- MySQL Workbench
- Postman
- Maven

Check Java:

```bash
java -version
```

---

# 2️⃣ Create MySQL Database

Open **MySQL Workbench** and run:

```sql
CREATE DATABASE employee_db;
USE employee_db;

CREATE TABLE employee (
    employee_id INT PRIMARY KEY AUTO_INCREMENT,
    employee_name VARCHAR(100) NOT NULL,
    age INT,
    salary DOUBLE,
    department VARCHAR(100),
    permanent_employee BOOLEAN
);

SELECT * FROM employee;
```

---

# 3️⃣ Create Spring Boot Project in IntelliJ IDEA

Go to:

```text
File
 ↓
New
 ↓
Project
 ↓
Spring Boot
```

Enter:

```text
Name: employee-crud-jdbc
Language: Java
Build System: Maven
JDK: 21
Java: 21
Packaging: Jar

Group: com.example
Artifact: employee-crud-jdbc
Package: com.example.employeecrud
```

---

# 4️⃣ Add Dependencies

Select:

- Spring Web
- Spring JDBC
- MySQL Driver

Do **not** select:

- Spring Data JPA
- Hibernate
- H2

---

# 5️⃣ pom.xml Dependencies

```xml
<dependencies>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jdbc</artifactId>
    </dependency>

    <dependency>
        <groupId>com.mysql</groupId>
        <artifactId>mysql-connector-j</artifactId>
        <scope>runtime</scope>
    </dependency>

</dependencies>
```

### Why Spring Web?

It allows us to create REST APIs using annotations such as:

```java
@RestController
@GetMapping
@PostMapping
@PutMapping
@DeleteMapping
```

### Why Spring JDBC?

It provides:

```java
JdbcTemplate
```

### Why MySQL Connector?

```text
Java Application
       |
       v
MySQL JDBC Driver
       |
       v
MySQL Database
```

---

# 6️⃣ Project Structure

```text
employee-crud-jdbc
│
├── src
│   └── main
│       ├── java
│       │   └── com.example.employeecrud
│       │       ├── EmployeeCrudApplication.java
│       │       ├── Employee.java
│       │       └── EmployeeController.java
│       │
│       └── resources
│           └── application.properties
│
└── pom.xml
```

---

# 7️⃣ Configure MySQL Connection

Open:

```text
src/main/resources/application.properties
```

Add:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_db
spring.datasource.username=root
spring.datasource.password=yourpassword
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
server.port=8080
```

Replace `yourpassword` with your MySQL password.

---

# 8️⃣ Understand the JDBC URL

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/employee_db
```

```text
jdbc:mysql://localhost:3306/employee_db
 |       |       |        |
 |       |       |        └── Database name
 |       |       └────────── MySQL port
 |       └────────────────── MySQL server
 └────────────────────────── JDBC connection
```

---

# 9️⃣ Create Employee Model Class

Create `Employee.java`:

```java
package com.example.employeecrud;

public class Employee {

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
}
```

There is no:

```java
@Entity
@Id
@GeneratedValue
@Table
@Column
```

because we are not using JPA.

---

# 🔟 Create EmployeeController

```java
package com.example.employeecrud;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeController(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
}
```

---

# 1️⃣1️⃣ What is JdbcTemplate?

Traditional JDBC often requires:

```java
Connection connection = DriverManager.getConnection(...);

PreparedStatement statement =
        connection.prepareStatement(...);

ResultSet resultSet =
        statement.executeQuery();
```

Spring provides `JdbcTemplate` to reduce this boilerplate.

However, we still manually:

- Write SQL
- Map database columns to Java objects
- Write INSERT statements
- Write UPDATE statements
- Write DELETE statements

---

# 1️⃣2️⃣ CREATE Operation

Add this inside `EmployeeController`:

```java
@PostMapping
public String createEmployee(
        @RequestBody Employee employee) {

    String sql = """
            INSERT INTO employee
            (
                employee_name,
                age,
                salary,
                department,
                permanent_employee
            )
            VALUES (?, ?, ?, ?, ?)
            """;

    jdbcTemplate.update(
            sql,
            employee.getEmployeeName(),
            employee.getAge(),
            employee.getSalary(),
            employee.getDepartment(),
            employee.isPermanentEmployee()
    );

    return "Employee created successfully";
}
```

---

# 1️⃣3️⃣ Test CREATE

Method:

```text
POST
```

URL:

```text
http://localhost:8080/employees
```

Body:

```json
{
    "employeeName": "Ravi",
    "age": 28,
    "salary": 45000,
    "department": "IT",
    "permanentEmployee": true
}
```

Expected response:

```text
Employee created successfully
```

Verify:

```sql
SELECT * FROM employee;
```

---

# 1️⃣4️⃣ READ All Employees

```java
@GetMapping
public List<Employee> getAllEmployees() {

    String sql = "SELECT * FROM employee";

    return jdbcTemplate.query(
            sql,
            (rs, rowNum) -> {

                Employee employee = new Employee();

                employee.setEmployeeId(
                        rs.getInt("employee_id")
                );

                employee.setEmployeeName(
                        rs.getString("employee_name")
                );

                employee.setAge(
                        rs.getInt("age")
                );

                employee.setSalary(
                        rs.getDouble("salary")
                );

                employee.setDepartment(
                        rs.getString("department")
                );

                employee.setPermanentEmployee(
                        rs.getBoolean("permanent_employee")
                );

                return employee;
            }
    );
}
```

Add:

```java
import java.util.List;
```

---

# 1️⃣5️⃣ ResultSet Mapping

```java
employee.setEmployeeId(
        rs.getInt("employee_id")
);

employee.setEmployeeName(
        rs.getString("employee_name")
);
```

This manually maps:

```text
employee_id
     ↓
employeeId
```

and:

```text
employee_name
     ↓
employeeName
```

This is called **ResultSet mapping**.

---

# 1️⃣6️⃣ Test GET All

Method:

```text
GET
```

URL:

```text
http://localhost:8080/employees
```

Example:

```json
[
    {
        "employeeId": 1,
        "employeeName": "Ravi",
        "age": 28,
        "salary": 45000.0,
        "department": "IT",
        "permanentEmployee": true
    }
]
```

---

# 1️⃣7️⃣ READ Employee by ID

```java
@GetMapping("/{id}")
public Employee getEmployeeById(
        @PathVariable int id) {

    String sql =
            "SELECT * FROM employee WHERE employee_id = ?";

    return jdbcTemplate.queryForObject(
            sql,
            (rs, rowNum) -> {

                Employee employee = new Employee();

                employee.setEmployeeId(
                        rs.getInt("employee_id")
                );

                employee.setEmployeeName(
                        rs.getString("employee_name")
                );

                employee.setAge(
                        rs.getInt("age")
                );

                employee.setSalary(
                        rs.getDouble("salary")
                );

                employee.setDepartment(
                        rs.getString("department")
                );

                employee.setPermanentEmployee(
                        rs.getBoolean("permanent_employee")
                );

                return employee;
            },
            id
    );
}
```

Test:

```text
GET http://localhost:8080/employees/1
```

---

# 1️⃣8️⃣ UPDATE Operation

```java
@PutMapping("/{id}")
public String updateEmployee(
        @PathVariable int id,
        @RequestBody Employee employee) {

    String sql = """
            UPDATE employee
            SET
                employee_name = ?,
                age = ?,
                salary = ?,
                department = ?,
                permanent_employee = ?
            WHERE employee_id = ?
            """;

    int rowsUpdated = jdbcTemplate.update(
            sql,
            employee.getEmployeeName(),
            employee.getAge(),
            employee.getSalary(),
            employee.getDepartment(),
            employee.isPermanentEmployee(),
            id
    );

    if (rowsUpdated > 0) {
        return "Employee updated successfully";
    }

    return "Employee not found";
}
```

Test:

```text
PUT http://localhost:8080/employees/1
```

Body:

```json
{
    "employeeName": "Ravi Kumar",
    "age": 29,
    "salary": 55000,
    "department": "Development",
    "permanentEmployee": true
}
```

---

# 1️⃣9️⃣ DELETE Operation

```java
@DeleteMapping("/{id}")
public String deleteEmployee(
        @PathVariable int id) {

    String sql =
            "DELETE FROM employee WHERE employee_id = ?";

    int rowsDeleted =
            jdbcTemplate.update(sql, id);

    if (rowsDeleted > 0) {
        return "Employee deleted successfully";
    }

    return "Employee not found";
}
```

Test:

```text
DELETE http://localhost:8080/employees/1
```

---

# 2️⃣0️⃣ Complete EmployeeController

```java
package com.example.employeecrud;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
public class EmployeeController {

    private final JdbcTemplate jdbcTemplate;

    public EmployeeController(
            JdbcTemplate jdbcTemplate) {

        this.jdbcTemplate = jdbcTemplate;
    }

    // CREATE

    @PostMapping
    public String createEmployee(
            @RequestBody Employee employee) {

        String sql = """
                INSERT INTO employee
                (
                    employee_name,
                    age,
                    salary,
                    department,
                    permanent_employee
                )
                VALUES (?, ?, ?, ?, ?)
                """;

        jdbcTemplate.update(
                sql,
                employee.getEmployeeName(),
                employee.getAge(),
                employee.getSalary(),
                employee.getDepartment(),
                employee.isPermanentEmployee()
        );

        return "Employee created successfully";
    }

    // READ ALL

    @GetMapping
    public List<Employee> getAllEmployees() {

        String sql = "SELECT * FROM employee";

        return jdbcTemplate.query(
                sql,
                (rs, rowNum) -> {

                    Employee employee =
                            new Employee();

                    employee.setEmployeeId(
                            rs.getInt("employee_id"));

                    employee.setEmployeeName(
                            rs.getString("employee_name"));

                    employee.setAge(
                            rs.getInt("age"));

                    employee.setSalary(
                            rs.getDouble("salary"));

                    employee.setDepartment(
                            rs.getString("department"));

                    employee.setPermanentEmployee(
                            rs.getBoolean(
                                    "permanent_employee"));

                    return employee;
                }
        );
    }

    // READ BY ID

    @GetMapping("/{id}")
    public Employee getEmployeeById(
            @PathVariable int id) {

        String sql =
                "SELECT * FROM employee "
                        + "WHERE employee_id = ?";

        return jdbcTemplate.queryForObject(
                sql,
                (rs, rowNum) -> {

                    Employee employee =
                            new Employee();

                    employee.setEmployeeId(
                            rs.getInt("employee_id"));

                    employee.setEmployeeName(
                            rs.getString("employee_name"));

                    employee.setAge(
                            rs.getInt("age"));

                    employee.setSalary(
                            rs.getDouble("salary"));

                    employee.setDepartment(
                            rs.getString("department"));

                    employee.setPermanentEmployee(
                            rs.getBoolean(
                                    "permanent_employee"));

                    return employee;
                },
                id
        );
    }

    // UPDATE

    @PutMapping("/{id}")
    public String updateEmployee(
            @PathVariable int id,
            @RequestBody Employee employee) {

        String sql = """
                UPDATE employee
                SET
                    employee_name = ?,
                    age = ?,
                    salary = ?,
                    department = ?,
                    permanent_employee = ?
                WHERE employee_id = ?
                """;

        int rowsUpdated =
                jdbcTemplate.update(
                        sql,
                        employee.getEmployeeName(),
                        employee.getAge(),
                        employee.getSalary(),
                        employee.getDepartment(),
                        employee.isPermanentEmployee(),
                        id
                );

        if (rowsUpdated > 0) {
            return "Employee updated successfully";
        }

        return "Employee not found";
    }

    // DELETE

    @DeleteMapping("/{id}")
    public String deleteEmployee(
            @PathVariable int id) {

        String sql =
                "DELETE FROM employee "
                        + "WHERE employee_id = ?";

        int rowsDeleted =
                jdbcTemplate.update(sql, id);

        if (rowsDeleted > 0) {
            return "Employee deleted successfully";
        }

        return "Employee not found";
    }
}
```

---

# 2️⃣1️⃣ Main Application Class

```java
package com.example.employeecrud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class EmployeeCrudApplication {

    public static void main(String[] args) {

        SpringApplication.run(
                EmployeeCrudApplication.class,
                args
        );
    }
}
```

Run this class.

You should see output similar to:

```text
Started EmployeeCrudApplication
Tomcat started on port 8080
```

---

# 2️⃣2️⃣ Recommended Testing Sequence

```text
1. POST employee
       ↓
2. GET all employees
       ↓
3. GET employee by ID
       ↓
4. PUT employee
       ↓
5. GET employee again
       ↓
6. DELETE employee
       ↓
7. GET all employees
```

---

# 2️⃣3️⃣ CRUD and HTTP Methods

| CRUD | SQL | HTTP |
|---|---|---|
| Create | INSERT | POST |
| Read | SELECT | GET |
| Update | UPDATE | PUT |
| Delete | DELETE | DELETE |

---

# 2️⃣4️⃣ Complete Request Flow

```text
Postman
   |
   | JSON
   v
EmployeeController
   |
   | @RequestBody
   v
Employee Java Object
   |
   v
JdbcTemplate
   |
   | SQL
   v
MySQL JDBC Driver
   |
   v
MySQL Database
   |
   v
employee table
```

---

# ⚠️ Difficulties Without JPA

These difficulties are useful to demonstrate before introducing JPA.

## 1. Manual SQL

We manually write:

```sql
INSERT INTO employee ...
SELECT * FROM employee
UPDATE employee ...
DELETE FROM employee ...
```

Every table requires SQL for CRUD operations.

---

## 2. Manual ResultSet Mapping

```java
employee.setEmployeeId(
        rs.getInt("employee_id")
);

employee.setEmployeeName(
        rs.getString("employee_name")
);
```

For a table with many columns, this becomes repetitive.

---

## 3. Database Column Dependency

If:

```text
employee_name
```

changes to:

```text
name
```

this code must also change:

```java
rs.getString("employee_name")
```

---

## 4. Database-Specific SQL

An application may later move from:

```text
MySQL
```

to:

```text
PostgreSQL
Oracle
SQL Server
```

Certain database-specific SQL may require changes.

---

## 5. Duplicate Mapping Code

Mapping logic is repeated in methods such as:

```text
getAllEmployees()
getEmployeeById()
```

---

## 6. Relationships Become More Difficult

Real applications may have:

```text
Employee
   |
   └── Department
```

or:

```text
Student
   |
   └── Courses
```

Without JPA, JOIN queries and object construction are usually handled manually.

JPA provides:

```java
@OneToOne
@OneToMany
@ManyToOne
@ManyToMany
```

---

# 🚀 Why Teach This Before JPA?

Without JPA:

```text
Database Row
      ↓
ResultSet
      ↓
Manual Mapping
      ↓
Java Object
```

JPA introduces **ORM**:

```text
Object Relational Mapping
```

With JPA:

```text
Employee Java Object
       |
       | save()
       v
JPA / Hibernate
       |
       | generates SQL
       v
MySQL
```

---

# 🆚 JDBC vs JPA Preview

### Without JPA — INSERT

```java
String sql = """
        INSERT INTO employee
        (
            employee_name,
            age,
            salary,
            department,
            permanent_employee
        )
        VALUES (?, ?, ?, ?, ?)
        """;

jdbcTemplate.update(
        sql,
        employee.getEmployeeName(),
        employee.getAge(),
        employee.getSalary(),
        employee.getDepartment(),
        employee.isPermanentEmployee()
);
```

### With JPA

```java
employeeRepository.save(employee);
```

### Without JPA — SELECT

```java
String sql = "SELECT * FROM employee";
```

plus ResultSet mapping.

### With JPA

```java
employeeRepository.findAll();
```

### Without JPA — DELETE

```java
String sql =
        "DELETE FROM employee WHERE employee_id = ?";
```

### With JPA

```java
employeeRepository.deleteById(id);
```

---

# 🎓 Recommended Teaching Sequence

```text
Database
   ↓
SQL
   ↓
JDBC
   ↓
JdbcTemplate
   ↓
RestController
   ↓
CRUD API
   ↓
Observe JDBC Difficulties
   ↓
Introduce JPA
   ↓
Hibernate
   ↓
JpaRepository
```

> **JPA becomes much easier for students to appreciate after they first experience writing SQL and performing manual Java-to-database mapping using JDBC.**
