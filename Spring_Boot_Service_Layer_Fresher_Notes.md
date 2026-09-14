<div align="center">

# <span style="color:#7C3AED;">🧩 Spring Boot Service Layer for Freshers</span>

## <span style="color:#2563EB;">Controller → Service Request Flow</span>

### <span style="color:#059669;">Why We Need Service Classes and How to Implement Them</span>

</div>

---

# <span style="color:#DC2626;">1. Where Are We Continuing From?</span>

Previously, our REST application flow was:

```text
Client
   |
   v
Controller
   |
   v
ArrayList
```

Now we improve the application to:

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
ArrayList
```

Later, when a database is introduced:

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
Database
```

---

# <span style="color:#2563EB;">2. What is a Service Class?</span>

A **Service class** contains the **business logic** of the application.

Examples of business operations:

```text
Get all employees
Find an employee by ID
Add an employee
Update employee details
Update salary
Delete employee
Validate salary
Calculate bonus
Check eligibility
Apply business rules
```

The Controller should mainly handle:

```text
Receive HTTP request
        ↓
Read request data
        ↓
Call Service
        ↓
Return response
```

The Service should mainly handle:

```text
Business logic
       ↓
Processing data
       ↓
Applying rules
       ↓
Returning result
```

---

# <span style="color:#16A34A;">3. Why Do We Need a Service Layer?</span>

In the previous version, the Controller did everything.

Example:

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

The Controller is doing two jobs:

```text
1. Handling HTTP request

2. Searching employee data
```

This is manageable for a tiny project, but becomes difficult in larger applications.

We want to separate responsibilities.

---

# <span style="color:#F59E0B;">4. Controller vs Service – Restaurant Analogy</span>

Think about a restaurant.

```text
Customer
   |
   v
Waiter
   |
   v
Chef
```

Mapping to Spring Boot:

```text
Customer = Client
Waiter   = Controller
Chef     = Service
```

The waiter should not cook.

The waiter:

```text
Takes customer order
       ↓
Sends order to kitchen
       ↓
Receives prepared food
       ↓
Returns it to customer
```

Similarly:

```text
Client
   |
   | HTTP Request
   v
Controller
   |
   | Method Call
   v
Service
   |
   | Business Logic
   v
Result
```

---

# <span style="color:#0EA5E9;">5. What Should a Controller Do?</span>

A Controller should mainly handle **HTTP-related responsibilities**.

Examples:

```java
@GetMapping
@PostMapping
@PutMapping
@PatchMapping
@DeleteMapping
```

It should receive values using:

```java
@PathVariable
@RequestParam
@RequestBody
```

Then it should call the Service.

Example:

```java
@GetMapping
public List<Employee> getEmployees() {

    return employeeService.getAllEmployees();
}
```

The Controller is not searching or manipulating the list.

It delegates the work.

---

# <span style="color:#9333EA;">6. What Should a Service Do?</span>

The Service contains application and business logic.

Example:

```java
public List<Employee> getAllEmployees() {

    return employees;
}
```

Example of searching:

```java
public Employee getEmployeeById(int id) {

    for (Employee employee : employees) {

        if (employee.getId() == id) {
            return employee;
        }
    }

    return null;
}
```

The Service does not care whether the request came from:

```text
Browser
Postman
React
Angular
Mobile App
Another Java Application
```

It only performs the required operation.

---

# <span style="color:#E11D48;">7. Why Not Put Everything Inside Controller?</span>

A Controller may become like this:

```text
Controller
   |
   +-- HTTP handling
   +-- Employee search
   +-- Validation
   +-- Salary calculation
   +-- Business rules
   +-- Database logic
```

As the project grows, the Controller can become very large.

Possible problems:

```text
Difficult to understand
Difficult to maintain
Difficult to test
Difficult to reuse
Difficult to debug
Difficult to modify
```

The Service layer solves this by separating responsibilities.

---

# <span style="color:#7C3AED;">8. Separation of Responsibilities</span>

A better architecture is:

```text
Controller
    |
    | Responsible for HTTP
    v
Service
    |
    | Responsible for business logic
    v
Repository
    |
    | Responsible for database operations
    v
Database
```

Easy rule:

```text
Controller → HTTP

Service    → Business Logic

Repository → Database
```

---

# <span style="color:#2563EB;">9. Updated Project Structure</span>

```text
rest-demo
│
└── src
    └── main
        └── java
            └── com.example.restdemo
                │
                ├── RestDemoApplication.java
                ├── Employee.java
                ├── EmployeeController.java
                └── EmployeeService.java
```

We are adding:

```text
EmployeeService.java
```

---

# <span style="color:#059669;">10. Create EmployeeService in IntelliJ</span>

In IntelliJ:

```text
Right-click package

com.example.restdemo

        ↓

New

        ↓

Java Class
```

Enter:

```text
EmployeeService
```

---

# <span style="color:#EA580C;">11. Add @Service Annotation</span>

```java
package com.example.restdemo;

import org.springframework.stereotype.Service;

@Service
public class EmployeeService {

}
```

The key annotation is:

```java
@Service
```

---

# <span style="color:#8B5CF6;">12. What Does @Service Mean?</span>

`@Service` tells Spring:

> Create and manage an object of this class because this class contains business logic.

Example:

```java
@Service
public class EmployeeService {
}
```

Conceptually:

```text
Spring Boot starts
       |
       v
Spring sees @Service
       |
       v
Creates EmployeeService object
       |
       v
Stores it inside Spring Container
```

Normally, you do not manually write:

```java
EmployeeService service =
        new EmployeeService();
```

Spring manages the object for us.

---

# <span style="color:#16A34A;">13. Move Hardcoded Data to Service</span>

Previously, the employee list was inside the Controller.

Now move it into the Service.

```java
package com.example.restdemo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private List<Employee> employees = new ArrayList<>();

    public EmployeeService() {

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

The employee data is now managed by the Service.

---

# <span style="color:#22C55E;">14. GET All Employees in Service</span>

Add:

```java
public List<Employee> getAllEmployees() {

    return employees;
}
```

Service:

```java
@Service
public class EmployeeService {

    private List<Employee> employees = new ArrayList<>();

    public EmployeeService() {

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

    public List<Employee> getAllEmployees() {

        return employees;
    }
}
```

---

# <span style="color:#0891B2;">15. Connect Controller to Service</span>

Modify:

```text
EmployeeController.java
```

```java
package com.example.restdemo;

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

    @GetMapping
    public List<Employee> getAllEmployees() {

        return employeeService.getAllEmployees();
    }
}
```

---

# <span style="color:#D946EF;">16. Who Passes EmployeeService to the Controller?</span>

Look at:

```java
private final EmployeeService employeeService;
```

and:

```java
public EmployeeController(
        EmployeeService employeeService) {

    this.employeeService = employeeService;
}
```

Spring automatically provides the `EmployeeService` object.

This is called:

# <span style="color:#D946EF;">Dependency Injection</span>

Flow:

```text
Spring creates

EmployeeService object

        ↓

Spring creates

EmployeeController object

        ↓

Spring injects EmployeeService

        ↓

EmployeeController can use EmployeeService
```

We do not write:

```java
new EmployeeService();
```

---

# <span style="color:#F97316;">17. Request Flow for GET</span>

Request:

```text
GET http://localhost:8080/employees
```

Flow:

```text
Client
   |
   | GET /employees
   v
EmployeeController
   |
   | employeeService.getAllEmployees()
   v
EmployeeService
   |
   | return employees
   v
EmployeeController
   |
   v
JSON Response
```

This is our new request flow:

```text
Controller → Service
```

---

# <span style="color:#10B981;">18. GET Employee by ID</span>

## Service

```java
public Employee getEmployeeById(int id) {

    for (Employee employee : employees) {

        if (employee.getId() == id) {

            return employee;
        }
    }

    return null;
}
```

## Controller

```java
@GetMapping("/{id}")
public Employee getEmployee(
        @PathVariable int id) {

    return employeeService.getEmployeeById(id);
}
```

The Controller is now very simple.

---

# <span style="color:#F59E0B;">19. POST – Add Employee Through Service</span>

## Service

```java
public Employee addEmployee(Employee employee) {

    employees.add(employee);

    return employee;
}
```

## Controller

```java
@PostMapping
public Employee addEmployee(
        @RequestBody Employee employee) {

    return employeeService.addEmployee(employee);
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

Flow:

```text
POST /employees
       |
       v
Controller
       |
       | Employee object
       v
Service
       |
       | employees.add(employee)
       v
Controller
       |
       v
JSON Response
```

---

# <span style="color:#A855F7;">20. PUT – Update Employee Through Service</span>

## Service

```java
public Employee updateEmployee(
        int id,
        Employee updatedEmployee) {

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
```

## Controller

```java
@PutMapping("/{id}")
public Employee updateEmployee(
        @PathVariable int id,
        @RequestBody Employee employee) {

    return employeeService.updateEmployee(
            id,
            employee
    );
}
```

---

# <span style="color:#E11D48;">21. PATCH – Update Salary Through Service</span>

## Service

```java
public Employee updateSalary(
        int id,
        double salary) {

    for (Employee employee : employees) {

        if (employee.getId() == id) {

            employee.setSalary(salary);

            return employee;
        }
    }

    return null;
}
```

## Controller

```java
@PatchMapping("/{id}/salary")
public Employee updateSalary(
        @PathVariable int id,
        @RequestParam double salary) {

    return employeeService.updateSalary(
            id,
            salary
    );
}
```

---

# <span style="color:#EF4444;">22. DELETE – Delete Through Service</span>

## Service

```java
public boolean deleteEmployee(int id) {

    for (Employee employee : employees) {

        if (employee.getId() == id) {

            employees.remove(employee);

            return true;
        }
    }

    return false;
}
```

## Controller

```java
@DeleteMapping("/{id}")
public String deleteEmployee(
        @PathVariable int id) {

    boolean deleted =
            employeeService.deleteEmployee(id);

    if (deleted) {

        return "Employee deleted successfully";
    }

    return "Employee not found";
}
```

---

# <span style="color:#2563EB;">23. Complete EmployeeService.java</span>

```java
package com.example.restdemo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmployeeService {

    private final List<Employee> employees =
            new ArrayList<>();


    public EmployeeService() {

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


    // GET ALL

    public List<Employee> getAllEmployees() {

        return employees;
    }


    // GET BY ID

    public Employee getEmployeeById(int id) {

        for (Employee employee : employees) {

            if (employee.getId() == id) {

                return employee;
            }
        }

        return null;
    }


    // CREATE

    public Employee addEmployee(
            Employee employee) {

        employees.add(employee);

        return employee;
    }


    // FULL UPDATE

    public Employee updateEmployee(
            int id,
            Employee updatedEmployee) {

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


    // PARTIAL UPDATE

    public Employee updateSalary(
            int id,
            double salary) {

        for (Employee employee : employees) {

            if (employee.getId() == id) {

                employee.setSalary(salary);

                return employee;
            }
        }

        return null;
    }


    // DELETE

    public boolean deleteEmployee(int id) {

        for (Employee employee : employees) {

            if (employee.getId() == id) {

                employees.remove(employee);

                return true;
            }
        }

        return false;
    }
}
```

---

# <span style="color:#7C3AED;">24. Complete EmployeeController.java</span>

```java
package com.example.restdemo;

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


    // GET ALL

    @GetMapping
    public List<Employee> getAllEmployees() {

        return employeeService.getAllEmployees();
    }


    // GET BY ID

    @GetMapping("/{id}")
    public Employee getEmployee(
            @PathVariable int id) {

        return employeeService.getEmployeeById(id);
    }


    // CREATE

    @PostMapping
    public Employee addEmployee(
            @RequestBody Employee employee) {

        return employeeService.addEmployee(employee);
    }


    // FULL UPDATE

    @PutMapping("/{id}")
    public Employee updateEmployee(
            @PathVariable int id,
            @RequestBody Employee employee) {

        return employeeService.updateEmployee(
                id,
                employee
        );
    }


    // PARTIAL UPDATE

    @PatchMapping("/{id}/salary")
    public Employee updateSalary(
            @PathVariable int id,
            @RequestParam double salary) {

        return employeeService.updateSalary(
                id,
                salary
        );
    }


    // DELETE

    @DeleteMapping("/{id}")
    public String deleteEmployee(
            @PathVariable int id) {

        boolean deleted =
                employeeService.deleteEmployee(id);

        if (deleted) {

            return "Employee deleted successfully";
        }

        return "Employee not found";
    }
}
```

---

# <span style="color:#059669;">25. Employee.java Remains the Same</span>

```java
package com.example.restdemo;

public class Employee {

    private int id;
    private String name;
    private String department;
    private double salary;


    public Employee() {
    }


    public Employee(
            int id,
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


    public void setDepartment(
            String department) {

        this.department = department;
    }


    public double getSalary() {
        return salary;
    }


    public void setSalary(
            double salary) {

        this.salary = salary;
    }
}
```

---

# <span style="color:#0EA5E9;">26. Complete Application Flow</span>

Consider:

```text
GET /employees/101
```

The complete flow is:

```text
Browser / Postman
       |
       |
       | GET /employees/101
       v
+------------------------------+
| EmployeeController           |
|                              |
| getEmployee(101)             |
+--------------+---------------+
               |
               |
               | employeeService
               | .getEmployeeById(101)
               v
+------------------------------+
| EmployeeService              |
|                              |
| Search employee list         |
| Apply business logic         |
+--------------+---------------+
               |
               |
               | Employee object
               v
+------------------------------+
| EmployeeController           |
+--------------+---------------+
               |
               |
               v
          JSON Response
```

Example response:

```json
{
  "id": 101,
  "name": "Ravi",
  "department": "IT",
  "salary": 50000.0
}
```

---

# <span style="color:#F97316;">27. Real Business Logic Example</span>

Suppose the company rule is:

> Employee salary should not be less than ₹20,000.

This should be written inside the **Service**, not inside the Controller.

```java
public Employee addEmployee(
        Employee employee) {

    if (employee.getSalary() < 20000) {

        throw new RuntimeException(
                "Salary must be at least 20000"
        );
    }

    employees.add(employee);

    return employee;
}
```

Why?

Because salary validation is **business logic**.

---

# <span style="color:#F59E0B;">28. Bonus Calculation Example</span>

Suppose we want to calculate a 10% bonus.

Service:

```java
public double calculateBonus(int id) {

    Employee employee =
            getEmployeeById(id);

    if (employee == null) {
        return 0;
    }

    return employee.getSalary() * 0.10;
}
```

Controller:

```java
@GetMapping("/{id}/bonus")
public double getBonus(
        @PathVariable int id) {

    return employeeService.calculateBonus(id);
}
```

The Controller does not calculate anything.

It only calls the Service.

---

# <span style="color:#9333EA;">29. Advantages of Service Layer</span>

| Without Service | With Service |
|---|---|
| Controller becomes large | Controller remains small |
| Business logic mixed with HTTP | Business logic separated |
| Difficult to test | Easier to test |
| Difficult to reuse logic | Logic can be reused |
| Difficult to maintain | Easier to maintain |
| Tight coupling | Better separation |
| Poor scalability | Better architecture |

---

# <span style="color:#DC2626;">30. Can One Service Be Used by Multiple Controllers?</span>

Yes.

```text
EmployeeController
           \
            \
             > EmployeeService
            /
           /
AdminController
```

Multiple Controllers can reuse the same business logic.

This is another reason not to place business logic inside a Controller.

---

# <span style="color:#16A34A;">31. Controller vs Service – Simple Rule</span>

Ask:

> Is this related to HTTP?

If yes:

```text
Controller
```

Examples:

```java
@GetMapping
@PostMapping
@RequestBody
@PathVariable
@RequestParam
```

Ask:

> Is this related to application/business rules?

If yes:

```text
Service
```

Examples:

```text
Calculate salary
Validate employee
Search employee
Check eligibility
Calculate bonus
Process payment
Generate total
Apply discount
```

---

# <span style="color:#2563EB;">32. What is Dependency Injection?</span>

`EmployeeController` needs `EmployeeService`.

Therefore:

```text
EmployeeService
```

is a dependency of:

```text
EmployeeController
```

Spring injects it using constructor injection:

```java
public EmployeeController(
        EmployeeService employeeService) {

    this.employeeService = employeeService;
}
```

This is called:

```text
Dependency Injection
```

---

# <span style="color:#A855F7;">33. Why Constructor Injection?</span>

You may see older examples like:

```java
@Autowired
private EmployeeService employeeService;
```

A better modern style is constructor injection:

```java
private final EmployeeService employeeService;

public EmployeeController(
        EmployeeService employeeService) {

    this.employeeService = employeeService;
}
```

Advantages:

```text
Dependency is clearly visible
Easy to test
Works well with final fields
Object gets required dependency during creation
No need for field injection
```

If the class has only one constructor, Spring can inject it automatically.

You do not need:

```java
@Autowired
```

on that constructor.

---

# <span style="color:#0891B2;">34. What Happens When Spring Boot Starts?</span>

Spring scans the application.

It sees:

```java
@RestController
```

and creates:

```text
EmployeeController object
```

It sees:

```java
@Service
```

and creates:

```text
EmployeeService object
```

Then Spring injects the Service into the Controller.

```text
Spring Boot Starts
        |
        v
Scans Classes
        |
        +-------------------+
        |                   |
        v                   v
@RestController          @Service
        |                   |
        v                   v
EmployeeController    EmployeeService
        |                   ^
        |                   |
        +------ Inject -----+
```

---

# <span style="color:#E11D48;">35. Test the Application</span>

The REST URLs remain the same.

## GET All

```http
GET http://localhost:8080/employees
```

## GET One

```http
GET http://localhost:8080/employees/101
```

## POST

```http
POST http://localhost:8080/employees
Content-Type: application/json

{
  "id": 104,
  "name": "Kumar",
  "department": "Sales",
  "salary": 60000
}
```

## PUT

```http
PUT http://localhost:8080/employees/104
Content-Type: application/json

{
  "name": "Kumar Raj",
  "department": "Marketing",
  "salary": 65000
}
```

## PATCH

```http
PATCH http://localhost:8080/employees/104/salary?salary=70000
```

## DELETE

```http
DELETE http://localhost:8080/employees/104
```

Important:

> The REST URLs did not change.

Only the internal architecture became better.

---

# <span style="color:#059669;">36. Before and After Service Layer</span>

## Before

```text
HTTP Request
     |
     v
Controller
     |
     +-- HTTP Handling
     +-- Search
     +-- Validation
     +-- Update
     +-- Delete
     +-- Business Logic
```

## After

```text
HTTP Request
     |
     v
Controller
     |
     | Delegate
     v
Service
     |
     +-- Search
     +-- Validation
     +-- Update
     +-- Delete
     +-- Business Logic
```

The application becomes much cleaner.

---

# <span style="color:#F59E0B;">37. Real-World Spring Boot Architecture</span>

Learning step 1:

```text
Controller
   |
   v
Hardcoded List
```

Learning step 2:

```text
Controller
   |
   v
Service
   |
   v
Hardcoded List
```

Next step:

```text
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
H2 Database
```

Later:

```text
Controller
   |
   v
Service
   |
   v
Repository
   |
   v
MySQL / PostgreSQL / Oracle / SQL Server
```

---

# <span style="color:#7C3AED;">38. Fresher Cheat Sheet</span>

```text
CONTROLLER
   |
   | Handles HTTP
   |
   | @GetMapping
   | @PostMapping
   | @PutMapping
   | @PatchMapping
   | @DeleteMapping
   |
   v

SERVICE
   |
   | Handles Business Logic
   |
   | Validation
   | Calculation
   | Processing
   | Rules
   |
   v

REPOSITORY
   |
   | Handles Database
   |
   v

DATABASE
```

Remember:

```text
Controller = Request / Response

Service    = Business Logic

Repository = Database Logic

Entity     = Data Structure
```

---

<div align="center">

# <span style="color:#16A34A;">✅ Key Takeaway</span>

The Controller should not contain all application logic.

The Controller should receive the request and delegate the work.

### <span style="color:#2563EB;">Controller → Service</span>

The Service performs the business logic.

The next natural step is:

### <span style="color:#9333EA;">Controller → Service → Repository → JPA → H2 Database</span>

</div>
