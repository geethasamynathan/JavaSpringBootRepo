# Java OOP Example – Collecting Multiple Employee Records

## Case 1 – Single Employee

In the first example, one employee is created using the `Employee` class.

```java
Employee employee = new Employee(
        employeeId,
        employeeName,
        age,
        salary,
        department,
        permanentEmployee
);
```

This creates one object:

```text
Employee employee
      |
      v
+----------------------+
| Employee Object      |
| ID : 1001            |
| Name : Arun          |
| Age : 30             |
+----------------------+
```

---

# Case 2 – Multiple Employees Using List and Iteration

The next step is to collect multiple employees using a loop and store each employee object inside a `List<Employee>`.

## Complete Program

```java
package com.abc;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Employee {

    private int employeeId;
    private String employeeName;
    private int age;
    private double salary;
    private String department;
    private boolean permanentEmployee;

    public Employee(int employeeId,
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

    public void displayEmployeeDetails() {

        System.out.println("\n----- Employee Information -----");

        System.out.println("Employee ID        : " + employeeId);
        System.out.println("Employee Name      : " + employeeName);
        System.out.println("Age                : " + age);
        System.out.println("Salary             : " + salary);
        System.out.println("Department         : " + department);
        System.out.println("Permanent Employee : " + permanentEmployee);
    }
}


public class MultipleEmployeeApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // List to store multiple Employee objects
        List<Employee> employeeList = new ArrayList<>();

        System.out.print("How many employees do you want to enter? ");

        int numberOfEmployees = scanner.nextInt();
        scanner.nextLine();

        // Loop to collect employee information
        for (int i = 1; i <= numberOfEmployees; i++) {

            System.out.println("\n===== Enter Employee " + i + " Details =====");

            System.out.print("Enter Employee ID: ");
            int employeeId = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Enter Employee Name: ");
            String employeeName = scanner.nextLine();

            System.out.print("Enter Employee Age: ");
            int age = scanner.nextInt();

            System.out.print("Enter Employee Salary: ");
            double salary = scanner.nextDouble();
            scanner.nextLine();

            System.out.print("Enter Department: ");
            String department = scanner.nextLine();

            System.out.print("Is Permanent Employee? (true/false): ");
            boolean permanentEmployee = scanner.nextBoolean();
            scanner.nextLine();

            // Create Employee object
            Employee employee = new Employee(
                    employeeId,
                    employeeName,
                    age,
                    salary,
                    department,
                    permanentEmployee
            );

            // Store Employee object inside the list
            employeeList.add(employee);
        }

        System.out.println("\n=================================");
        System.out.println("      ALL EMPLOYEE DETAILS");
        System.out.println("=================================");

        // Iterate through the list
        for (Employee employee : employeeList) {

            employee.displayEmployeeDetails();
        }

        scanner.close();
    }
}
```

---

## Step 1 – Create the List

```java
List<Employee> employeeList = new ArrayList<>();
```

Here:

```text
List<Employee>
     |
     +---- Employee means
           this list can store
           Employee objects
```

`ArrayList` is the actual object that stores the employee objects.

```text
Employee

means one employee.

List<Employee>

means a collection of employees.
```

---

## Step 2 – Ask How Many Employees to Enter

```java
System.out.print("How many employees do you want to enter? ");

int numberOfEmployees = scanner.nextInt();
```

Suppose the user enters:

```text
3
```

Then the loop runs three times.

---

## Step 3 – Use a for Loop

```java
for (int i = 1; i <= numberOfEmployees; i++) {
```

If:

```text
numberOfEmployees = 3
```

the loop behaves like this:

```text
i = 1
Collect Employee 1

i = 2
Collect Employee 2

i = 3
Collect Employee 3

i = 4
Condition becomes false
Loop stops
```

### Flow

```text
          Start
            |
            v
          i = 1
            |
            v
      i <= numberOfEmployees?
         /          \
       Yes           No
        |             |
        v             v
Collect Employee     Stop
        |
        v
Create Employee Object
        |
        v
Add object to List
        |
        v
      i++
        |
        +-----------> Repeat
```

---

## Step 4 – Create an Employee Object in Each Iteration

Inside the loop:

```java
Employee employee = new Employee(
        employeeId,
        employeeName,
        age,
        salary,
        department,
        permanentEmployee
);
```

Suppose the first employee enters:

```text
1001
Arun
30
55000
IT
true
```

Java creates:

```text
Employee Object

employeeId        = 1001
employeeName      = Arun
age               = 30
salary            = 55000
department        = IT
permanentEmployee = true
```

Then this line:

```java
employeeList.add(employee);
```

stores the object inside the list.

After the first iteration:

```text
employeeList

[0] ---> Employee 1001
```

After the second iteration:

```text
employeeList

[0] ---> Employee 1001 - Arun
[1] ---> Employee 1002 - Priya
```

After the third iteration:

```text
employeeList

[0] ---> Employee 1001 - Arun
[1] ---> Employee 1002 - Priya
[2] ---> Employee 1003 - John
```

The key statement is:

```java
employeeList.add(employee);
```

Meaning:

> Store the newly created Employee object in the employee collection.

---

## Step 5 – Iterate Through the List

After collecting all employees, we use an enhanced `for` loop:

```java
for (Employee employee : employeeList) {

    employee.displayEmployeeDetails();
}
```

This is also called a **for-each loop**.

```text
employeeList
      |
      v

+----------+
| Employee | ---> employee ---> displayEmployeeDetails()
+----------+

+----------+
| Employee | ---> employee ---> displayEmployeeDetails()
+----------+

+----------+
| Employee | ---> employee ---> displayEmployeeDetails()
+----------+
```

The meaning of:

```java
for (Employee employee : employeeList)
```

is:

```text
For every Employee object
available inside employeeList,

take one Employee at a time
and store its reference in

employee
```

Then:

```java
employee.displayEmployeeDetails();
```

calls the method for that employee.

---

# Sample Input

```text
How many employees do you want to enter? 3

===== Enter Employee 1 Details =====
Enter Employee ID: 1001
Enter Employee Name: Arun Kumar
Enter Employee Age: 30
Enter Employee Salary: 55000
Enter Department: IT
Is Permanent Employee? (true/false): true

===== Enter Employee 2 Details =====
Enter Employee ID: 1002
Enter Employee Name: Priya
Enter Employee Age: 27
Enter Employee Salary: 45000
Enter Department: HR
Is Permanent Employee? (true/false): true

===== Enter Employee 3 Details =====
Enter Employee ID: 1003
Enter Employee Name: John
Enter Employee Age: 25
Enter Employee Salary: 38000
Enter Department: Support
Is Permanent Employee? (true/false): false
```

---

# Internal List Representation

```text
employeeList
    |
    +---- [0] Employee
    |         ID: 1001
    |         Name: Arun Kumar
    |
    +---- [1] Employee
    |         ID: 1002
    |         Name: Priya
    |
    +---- [2] Employee
              ID: 1003
              Name: John
```

---

# Sample Output

```text
=================================
      ALL EMPLOYEE DETAILS
=================================

----- Employee Information -----
Employee ID        : 1001
Employee Name      : Arun Kumar
Age                : 30
Salary             : 55000.0
Department         : IT
Permanent Employee : true

----- Employee Information -----
Employee ID        : 1002
Employee Name      : Priya
Age                : 27
Salary             : 45000.0
Department         : HR
Permanent Employee : true

----- Employee Information -----
Employee ID        : 1003
Employee Name      : John
Age                : 25
Salary             : 38000.0
Department         : Support
Permanent Employee : false
```

---

# Concept Progression

```text
CASE 1
Single Employee
      |
      v
Class
      |
      v
Object
      |
      v
Employee employee


CASE 2
Multiple Employees
      |
      v
Loop
      |
      v
Create Employee Object
      |
      v
List<Employee>
      |
      v
employeeList.add(employee)
      |
      v
Repeat
      |
      v
Iterate List
      |
      v
Display all Employees
```

---

# Concepts Covered

- Class
- Object
- Constructor
- Encapsulation
- Scanner input
- `for` loop
- `List<Employee>`
- `ArrayList`
- `add()`
- Enhanced `for` loop
- Storing multiple objects in a collection
