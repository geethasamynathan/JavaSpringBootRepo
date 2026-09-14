# <span style="color:#1565C0;">🔵 JDBC & H2 Database — Beginner-Friendly Step-by-Step Guide</span>

> **Goal:** Learn what JDBC is and how to connect a Java application to an H2 database from scratch, with CRUD examples.

---

## <span style="color:#2E7D32;">🟢 1. What is JDBC?</span>

**JDBC = Java Database Connectivity**

JDBC is the standard Java API used to connect Java applications with databases such as:

- H2
- MySQL
- PostgreSQL
- Oracle
- SQL Server

Using JDBC, a Java program can:

- Connect to a database
- Create tables
- Insert records
- Read records
- Update records
- Delete records
- Execute SQL queries

### Simple idea

```text
Java Application
      |
      | JDBC
      v
Database
```

For example, an Employee Management application may store:

```text
Employee ID
Employee Name
Age
Department
Salary
```

Without a database, data stored only in Java variables is lost when the program stops.

```java
int employeeId = 101;
String employeeName = "John";
```

With JDBC, the data can be permanently stored in a database.

---

## <span style="color:#8E24AA;">🟣 2. What is H2 Database?</span>

H2 is a lightweight relational database written in Java.

It is very useful for beginners because:

- No separate database server installation is required
- It is lightweight
- It supports SQL
- It works well with Java
- It supports file-based and in-memory databases
- It is commonly used for learning, testing, and development

For this guide, we will use a **file-based H2 database**.

---

## <span style="color:#EF6C00;">🟠 3. What Are We Going to Build?</span>

We will build a simple Java application that connects to H2 using JDBC.

```text
Java Application
      |
      | JDBC
      v
H2 Database
      |
      v
EMPLOYEE Table
```

The table will contain:

```text
employee_id
employee_name
age
salary
department
```

We will perform:

1. Connect to H2
2. Create a table
3. Insert employee data
4. Read employee data
5. Update employee data
6. Delete employee data

---

## <span style="color:#C62828;">🔴 4. Software Required</span>

You need:

```text
Java JDK 21
IntelliJ IDEA
Maven
H2 Database dependency
```

IntelliJ IDEA usually includes Maven support, so you normally do not need to install Maven separately.

---

## <span style="color:#00838F;">🔷 5. Create a Project in IntelliJ IDEA</span>

Open IntelliJ IDEA.

Go to:

```text
File
  ↓
New
  ↓
Project
```

Choose a **Maven Java Project**.

Use:

```text
Project Name: jdbc-h2-demo
JDK: Java 21
```

Then click **Create**.

---

## <span style="color:#6A1B9A;">🟪 6. Project Structure</span>

Your project should look similar to:

```text
jdbc-h2-demo
│
├── pom.xml
│
└── src
    └── main
        └── java
            └── com
                └── example
                    └── JdbcH2Demo.java
```

---

## <span style="color:#2E7D32;">🟩 7. Add the H2 JDBC Dependency</span>

Open:

```text
pom.xml
```

Use:

```xml
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         https://maven.apache.org/xsd/maven-4.0.0.xsd">

    <modelVersion>4.0.0</modelVersion>

    <groupId>com.example</groupId>
    <artifactId>jdbc-h2-demo</artifactId>
    <version>1.0-SNAPSHOT</version>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
    </properties>

    <dependencies>

        <dependency>
            <groupId>com.h2database</groupId>
            <artifactId>h2</artifactId>
            <version>2.3.232</version>
        </dependency>

    </dependencies>

</project>
```

After adding the dependency, click:

```text
Load Maven Changes
```

Maven downloads the H2 JDBC driver automatically.

---

## <span style="color:#1565C0;">🔵 8. Why Do We Need a JDBC Driver?</span>

Java provides the JDBC API, but each database communicates differently.

The JDBC driver acts as a translator.

```text
Java
 |
 | JDBC commands
 v
JDBC Driver
 |
 | Database-specific communication
 v
H2 Database
```

For H2, the driver comes from:

```text
com.h2database:h2
```

---

## <span style="color:#AD1457;">🩷 9. JDBC Connection URL</span>

We will use:

```java
jdbc:h2:./data/employeedb
```

Meaning:

```text
jdbc
 |
 +-- Java Database Connectivity

h2
 |
 +-- Database type

./data/employeedb
 |
 +-- File location/database name
```

H2 creates the database inside the project folder.

---

## <span style="color:#EF6C00;">🟠 10. Username and Password</span>

Use:

```java
String username = "sa";
String password = "";
```

`sa` is the default H2 administrator username.

For this simple local example, the password is empty.

---

# <span style="color:#1565C0;">🔵 11. First JDBC Program — Connect to H2</span>

Create:

```text
JdbcH2Demo.java
```

Use:

```java
package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JdbcH2Demo {

    public static void main(String[] args) {

        String url = "jdbc:h2:./data/employeedb";
        String username = "sa";
        String password = "";

        try {

            Connection connection =
                    DriverManager.getConnection(
                            url,
                            username,
                            password
                    );

            System.out.println("Database connected successfully!");

            connection.close();

        } catch (SQLException e) {

            System.out.println("Database connection failed.");
            e.printStackTrace();
        }
    }
}
```

Expected output:

```text
Database connected successfully!
```

---

## <span style="color:#2E7D32;">🟢 12. Understanding DriverManager and Connection</span>

This line creates the connection:

```java
Connection connection =
        DriverManager.getConnection(url, username, password);
```

### DriverManager

`DriverManager` helps Java locate the appropriate JDBC driver and create the connection.

### Connection

`Connection` represents the active connection between the Java program and database.

```text
DriverManager
     |
     | getConnection()
     v
Connection
     |
     v
H2 Database
```

---

# <span style="color:#8E24AA;">🟣 13. Create the Employee Table</span>

Import:

```java
import java.sql.Statement;
```

Then use:

```java
package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class JdbcH2Demo {

    public static void main(String[] args) {

        String url = "jdbc:h2:./data/employeedb";
        String username = "sa";
        String password = "";

        try {

            Connection connection =
                    DriverManager.getConnection(
                            url,
                            username,
                            password
                    );

            System.out.println("Database connected successfully.");

            Statement statement =
                    connection.createStatement();

            String sql = """
                    CREATE TABLE IF NOT EXISTS employee (
                        employee_id INT PRIMARY KEY,
                        employee_name VARCHAR(100),
                        age INT,
                        salary DOUBLE,
                        department VARCHAR(100)
                    )
                    """;

            statement.execute(sql);

            System.out.println("Employee table created successfully.");

            statement.close();
            connection.close();

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
```

Expected output:

```text
Database connected successfully.
Employee table created successfully.
```

---

## <span style="color:#00838F;">🔷 14. What is Statement?</span>

A `Statement` object sends SQL commands from Java to the database.

```java
Statement statement = connection.createStatement();
```

Execute SQL:

```java
statement.execute(sql);
```

Flow:

```text
Java
 |
 | SQL
 v
Statement
 |
 v
JDBC Driver
 |
 v
H2 Database
```

---

# <span style="color:#EF6C00;">🟠 15. Insert Employee Data</span>

Example SQL:

```sql
INSERT INTO employee
(employee_id, employee_name, age, salary, department)
VALUES
(101, 'Arun', 25, 50000, 'IT');
```

Java:

```java
String insertSql = """
        INSERT INTO employee
        (employee_id, employee_name, age, salary, department)
        VALUES
        (101, 'Arun', 25, 50000, 'IT')
        """;

statement.executeUpdate(insertSql);

System.out.println("Employee inserted successfully.");
```

If the program runs again with ID `101`, the database may show a primary key violation because employee ID `101` already exists.

For this reason, real applications normally use `PreparedStatement`.

---

# <span style="color:#C62828;">🔴 16. What is PreparedStatement?</span>

A `PreparedStatement` is the preferred JDBC approach when SQL contains values.

Instead of:

```sql
INSERT INTO employee VALUES (101, 'Arun', ...)
```

we use placeholders:

```sql
INSERT INTO employee VALUES (?, ?, ?, ?, ?)
```

The `?` symbols represent values that will be supplied later.

---

## <span style="color:#2E7D32;">🟢 17. Insert Using PreparedStatement</span>

Import:

```java
import java.sql.PreparedStatement;
```

Code:

```java
String insertSql = """
        INSERT INTO employee
        (employee_id, employee_name, age, salary, department)
        VALUES (?, ?, ?, ?, ?)
        """;

PreparedStatement preparedStatement =
        connection.prepareStatement(insertSql);

preparedStatement.setInt(1, 101);
preparedStatement.setString(2, "Arun");
preparedStatement.setInt(3, 25);
preparedStatement.setDouble(4, 50000);
preparedStatement.setString(5, "IT");

preparedStatement.executeUpdate();

System.out.println("Employee inserted successfully.");
```

---

## <span style="color:#1565C0;">🔵 18. Understanding the Placeholder Numbers</span>

SQL:

```sql
VALUES (?, ?, ?, ?, ?)
```

Positions:

```text
1 → employee_id
2 → employee_name
3 → age
4 → salary
5 → department
```

Example:

```java
preparedStatement.setInt(1, 101);
```

means:

```text
First ? = 101
```

And:

```java
preparedStatement.setString(2, "Arun");
```

means:

```text
Second ? = Arun
```

---

## <span style="color:#8E24AA;">🟣 19. Why PreparedStatement is Better</span>

`PreparedStatement` is preferred because it:

- Handles values safely
- Improves readability
- Helps prevent SQL injection
- Handles data types correctly
- Is convenient for repeated queries

---

# <span style="color:#00838F;">🔷 20. Read Employee Data</span>

To retrieve data, JDBC uses:

```java
ResultSet
```

Import:

```java
import java.sql.ResultSet;
```

Code:

```java
String selectSql =
        "SELECT * FROM employee";

PreparedStatement selectStatement =
        connection.prepareStatement(selectSql);

ResultSet resultSet =
        selectStatement.executeQuery();

while (resultSet.next()) {

    int id =
            resultSet.getInt("employee_id");

    String name =
            resultSet.getString("employee_name");

    int age =
            resultSet.getInt("age");

    double salary =
            resultSet.getDouble("salary");

    String department =
            resultSet.getString("department");

    System.out.println(
            id + " | " +
            name + " | " +
            age + " | " +
            salary + " | " +
            department
    );
}
```

Expected output:

```text
101 | Arun | 25 | 50000.0 | IT
```

---

## <span style="color:#AD1457;">🩷 21. What is ResultSet?</span>

When a `SELECT` query returns rows, JDBC stores them inside a `ResultSet`.

Example:

```text
101 Arun 25 50000 IT
102 Priya 28 60000 HR
103 Kumar 30 70000 Finance
```

Use:

```java
resultSet.next();
```

to move row by row.

Conceptually:

```text
ResultSet

        ID    NAME     AGE
        ------------------
 -->    101   Arun     25
        102   Priya    28
        103   Kumar    30
```

---

# <span style="color:#EF6C00;">🟠 22. Update Employee</span>

SQL:

```sql
UPDATE employee
SET salary = ?
WHERE employee_id = ?;
```

Java:

```java
String updateSql = """
        UPDATE employee
        SET salary = ?
        WHERE employee_id = ?
        """;

PreparedStatement updateStatement =
        connection.prepareStatement(updateSql);

updateStatement.setDouble(1, 65000);
updateStatement.setInt(2, 101);

int rowsUpdated =
        updateStatement.executeUpdate();

System.out.println(
        rowsUpdated + " employee updated."
);
```

---

# <span style="color:#C62828;">🔴 23. Delete Employee</span>

SQL:

```sql
DELETE FROM employee
WHERE employee_id = ?;
```

Java:

```java
String deleteSql = """
        DELETE FROM employee
        WHERE employee_id = ?
        """;

PreparedStatement deleteStatement =
        connection.prepareStatement(deleteSql);

deleteStatement.setInt(1, 101);

int rowsDeleted =
        deleteStatement.executeUpdate();

System.out.println(
        rowsDeleted + " employee deleted."
);
```

---

# <span style="color:#1565C0;">🔵 24. Complete JDBC CRUD Program</span>

```java
package com.example;

import java.sql.*;

public class JdbcH2Demo {

    private static final String URL =
            "jdbc:h2:./data/employeedb";

    private static final String USERNAME =
            "sa";

    private static final String PASSWORD =
            "";

    public static void main(String[] args) {

        try (
                Connection connection =
                        DriverManager.getConnection(
                                URL,
                                USERNAME,
                                PASSWORD
                        )
        ) {

            System.out.println("Connected to H2 database.");

            // CREATE TABLE

            String createTableSql = """
                    CREATE TABLE IF NOT EXISTS employee (
                        employee_id INT PRIMARY KEY,
                        employee_name VARCHAR(100),
                        age INT,
                        salary DOUBLE,
                        department VARCHAR(100)
                    )
                    """;

            try (
                    Statement statement =
                            connection.createStatement()
            ) {

                statement.execute(createTableSql);

                System.out.println("Employee table ready.");
            }

            // INSERT

            String insertSql = """
                    INSERT INTO employee
                    (employee_id,
                     employee_name,
                     age,
                     salary,
                     department)
                    VALUES (?, ?, ?, ?, ?)
                    """;

            try (
                    PreparedStatement ps =
                            connection.prepareStatement(insertSql)
            ) {

                ps.setInt(1, 101);
                ps.setString(2, "Arun");
                ps.setInt(3, 25);
                ps.setDouble(4, 50000);
                ps.setString(5, "IT");

                ps.executeUpdate();

                System.out.println("Employee inserted.");
            }

            // READ

            String selectSql =
                    "SELECT * FROM employee";

            try (
                    PreparedStatement ps =
                            connection.prepareStatement(selectSql);

                    ResultSet resultSet =
                            ps.executeQuery()
            ) {

                System.out.println("\nEmployee Details");

                while (resultSet.next()) {

                    System.out.println(
                            "ID: "
                                    + resultSet.getInt("employee_id")
                    );

                    System.out.println(
                            "Name: "
                                    + resultSet.getString("employee_name")
                    );

                    System.out.println(
                            "Age: "
                                    + resultSet.getInt("age")
                    );

                    System.out.println(
                            "Salary: "
                                    + resultSet.getDouble("salary")
                    );

                    System.out.println(
                            "Department: "
                                    + resultSet.getString("department")
                    );

                    System.out.println("----------------------");
                }
            }

            // UPDATE

            String updateSql = """
                    UPDATE employee
                    SET salary = ?
                    WHERE employee_id = ?
                    """;

            try (
                    PreparedStatement ps =
                            connection.prepareStatement(updateSql)
            ) {

                ps.setDouble(1, 65000);
                ps.setInt(2, 101);

                int count = ps.executeUpdate();

                System.out.println(
                        count + " employee updated."
                );
            }

            // DELETE

            String deleteSql = """
                    DELETE FROM employee
                    WHERE employee_id = ?
                    """;

            try (
                    PreparedStatement ps =
                            connection.prepareStatement(deleteSql)
            ) {

                ps.setInt(1, 101);

                int count = ps.executeUpdate();

                System.out.println(
                        count + " employee deleted."
                );
            }

        } catch (SQLException e) {

            e.printStackTrace();
        }
    }
}
```

> **Note:** If you run this exact program multiple times, the second run may fail at INSERT because employee ID `101` already exists. For repeated practice, delete the existing record first, change the ID, or implement an existence check.

---

# <span style="color:#2E7D32;">🟢 25. JDBC Architecture</span>

```text
+------------------------+
| Java Application       |
| Employee Program       |
+-----------+------------+
            |
            | JDBC API
            v
+------------------------+
| JDBC DriverManager     |
+-----------+------------+
            |
            v
+------------------------+
| H2 JDBC Driver         |
+-----------+------------+
            |
            v
+------------------------+
| H2 Database            |
| EMPLOYEE TABLE         |
+------------------------+
```

---

# <span style="color:#8E24AA;">🟣 26. Important JDBC Classes</span>

| JDBC Class | Purpose |
|---|---|
| `DriverManager` | Creates the database connection |
| `Connection` | Represents a database connection |
| `Statement` | Executes simple/static SQL |
| `PreparedStatement` | Executes parameterized SQL |
| `ResultSet` | Stores SELECT query results |
| `SQLException` | Handles database-related errors |

Easy sequence to remember:

```text
DriverManager
     ↓
Connection
     ↓
PreparedStatement
     ↓
Execute SQL
     ↓
ResultSet
```

---

# <span style="color:#EF6C00;">🟠 27. execute(), executeUpdate(), executeQuery()</span>

### `executeQuery()`

Used mainly for:

```sql
SELECT
```

Example:

```java
ResultSet rs =
        statement.executeQuery(
                "SELECT * FROM employee"
        );
```

It returns a:

```text
ResultSet
```

### `executeUpdate()`

Used mainly for:

```text
INSERT
UPDATE
DELETE
```

Example:

```java
int rows =
        statement.executeUpdate(sql);
```

It returns the number of affected rows.

### `execute()`

Useful for general SQL operations such as:

```sql
CREATE TABLE
```

Example:

```java
statement.execute(
        "CREATE TABLE ..."
);
```

---

# <span style="color:#C62828;">🔴 28. CRUD Mapping</span>

```text
CRUD
│
├── C → Create
│      INSERT
│
├── R → Read
│      SELECT
│
├── U → Update
│      UPDATE
│
└── D → Delete
       DELETE
```

For Employee:

```text
Create Employee → INSERT
View Employee   → SELECT
Modify Employee → UPDATE
Remove Employee → DELETE
```

---

# <span style="color:#00838F;">🔷 29. Real-World Flow</span>

Suppose a user enters:

```text
Employee ID: 101
Employee Name: Arun
Age: 25
Salary: 50000
Department: IT
```

The flow is:

```text
User
 |
 v
Java Application
 |
 v
PreparedStatement
 |
 v
JDBC Driver
 |
 v
H2 Database
 |
 v
EMPLOYEE TABLE
```

SQL:

```sql
INSERT INTO employee
VALUES (?, ?, ?, ?, ?);
```

Database result:

```text
+-----+------+-----+--------+------------+
| ID  | Name | Age | Salary | Department |
+-----+------+-----+--------+------------+
| 101 | Arun | 25  | 50000  | IT         |
+-----+------+-----+--------+------------+
```

---

# <span style="color:#AD1457;">🩷 30. Why JDBC is Important</span>

JDBC is the foundation of database programming in Java.

Even when you later use:

- Spring JDBC
- Hibernate
- JPA
- Spring Data JPA

the underlying database communication still depends heavily on JDBC concepts and JDBC drivers.

Conceptually:

```text
Application
     |
     +-----------------------+
     |                       |
     v                       v
Direct JDBC              JPA / Hibernate
                             |
                             v
                           JDBC
                             |
                             v
                         Database
```

Learning JDBC first makes higher-level frameworks easier to understand.

---

# <span style="color:#1565C0;">🔵 31. JDBC vs JPA</span>

With JDBC, developers normally write SQL manually.

Example:

```java
String sql =
        "SELECT * FROM employee WHERE employee_id = ?";

PreparedStatement ps =
        connection.prepareStatement(sql);
```

With JPA, you may write:

```java
Employee employee =
        entityManager.find(
                Employee.class,
                101
        );
```

JPA reduces much of the repetitive JDBC code, but JDBC helps you understand what happens underneath.

---

# <span style="color:#2E7D32;">🟢 32. Recommended Learning Order</span>

```text
Java
 ↓
SQL Basics
 ↓
Database Concepts
 ↓
JDBC
 ↓
H2 Database
 ↓
CRUD
 ↓
PreparedStatement
 ↓
Transactions
 ↓
Spring JDBC
 ↓
JPA
 ↓
Hibernate
 ↓
Spring Data JPA
```

---

# <span style="color:#EF6C00;">🟠 33. Five JDBC Steps to Remember</span>

```text
1. Get Connection
       ↓
2. Create SQL
       ↓
3. Create Statement / PreparedStatement
       ↓
4. Execute SQL
       ↓
5. Process Result and Close Resources
```

Example:

```java
Connection connection =
        DriverManager.getConnection(...);

PreparedStatement ps =
        connection.prepareStatement(...);

ResultSet rs =
        ps.executeQuery();

while (rs.next()) {

    System.out.println(
            rs.getString("employee_name")
    );
}
```

---

# <span style="color:#6A1B9A;">🟣 Quick Revision</span>

| Concept | Simple Meaning |
|---|---|
| JDBC | Java API for database communication |
| H2 | Lightweight Java database |
| DriverManager | Helps create the DB connection |
| Connection | Active connection with the DB |
| Statement | Executes simple SQL |
| PreparedStatement | Executes parameterized SQL |
| ResultSet | Holds SELECT query results |
| CRUD | Create, Read, Update, Delete |
| `executeQuery()` | Used for SELECT |
| `executeUpdate()` | Used for INSERT, UPDATE, DELETE |

---

# <span style="color:#1565C0;">🎯 Final Learning Goal</span>

After completing this example, you should understand this flow:

```text
Java Program
    ↓
JDBC API
    ↓
JDBC Driver
    ↓
H2 Database
    ↓
SQL / CRUD Operations
```

This is the foundation for moving next into **Spring JDBC, JPA, Hibernate, and Spring Data JPA**.
