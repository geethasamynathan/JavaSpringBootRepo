package com.jdbc.jdbcdemo1;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

@SpringBootApplication
public class JdbcDemo1Application implements CommandLineRunner {

    private final JdbcTemplate jdbcTemplate;

    public JdbcDemo1Application(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static void main(String[] args) {
        SpringApplication.run(JdbcDemo1Application.class, args);
    }

    @Override
    public void run(String... args) {

        createTable();

        Scanner scanner = new Scanner(System.in);

        int choice;

        do {

            System.out.println("\n==============================");
            System.out.println("   EMPLOYEE CRUD APPLICATION");
            System.out.println("==============================");
            System.out.println("1. Insert Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Update Employee");
            System.out.println("4. Delete Employee");
            System.out.println("5. Exit");
            System.out.println("==============================");

            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    insertEmployee(scanner);
                    break;

                case 2:
                    viewEmployees();
                    break;

                case 3:
                    updateEmployee(scanner);
                    break;

                case 4:
                    deleteEmployee(scanner);
                    break;

                case 5:
                    System.out.println("Application closed.");
                    break;

                default:
                    System.out.println("Invalid choice. Please try again.");
            }

        } while (choice != 5);

        scanner.close();
    }

    // CREATE TABLE
    private void createTable() {

        jdbcTemplate.execute("""
                CREATE TABLE IF NOT EXISTS employee (
                    employee_id INT PRIMARY KEY,
                    employee_name VARCHAR(100),
                    age INT,
                    salary DOUBLE,
                    department VARCHAR(100),
                    permanent_employee BOOLEAN
                )
                """);

        System.out.println("EMPLOYEE table is ready.");
    }

    // INSERT
    private void insertEmployee(Scanner scanner) {

        System.out.println("\n--- Insert Employee ---");

        System.out.print("Enter Employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Enter Employee Name: ");
        String name = scanner.nextLine();

        System.out.print("Enter Age: ");
        int age = scanner.nextInt();

        System.out.print("Enter Salary: ");
        double salary = scanner.nextDouble();
        scanner.nextLine();

        System.out.print("Enter Department: ");
        String department = scanner.nextLine();

        System.out.print("Is Permanent Employee? (true/false): ");
        boolean permanentEmployee = scanner.nextBoolean();

        int rows = jdbcTemplate.update("""
                INSERT INTO employee
                (employee_id, employee_name, age, salary, department, permanent_employee)
                VALUES (?, ?, ?, ?, ?, ?)
                """,
                id,
                name,
                age,
                salary,
                department,
                permanentEmployee
        );

        if (rows > 0) {
            System.out.println("Employee inserted successfully.");
        }
    }

    // READ
    private void viewEmployees() {

        System.out.println("\n--- Employee List ---");

        List<Map<String, Object>> employees =
                jdbcTemplate.queryForList(
                        "SELECT * FROM employee"
                );

        if (employees.isEmpty()) {

            System.out.println("No employees found.");

            return;
        }

        for (Map<String, Object> employee : employees) {

            System.out.println("----------------------------");

            System.out.println(
                    "Employee ID : "
                            + employee.get("EMPLOYEE_ID")
            );

            System.out.println(
                    "Name        : "
                            + employee.get("EMPLOYEE_NAME")
            );

            System.out.println(
                    "Age         : "
                            + employee.get("AGE")
            );

            System.out.println(
                    "Salary      : "
                            + employee.get("SALARY")
            );

            System.out.println(
                    "Department  : "
                            + employee.get("DEPARTMENT")
            );

            System.out.println(
                    "Permanent   : "
                            + employee.get("PERMANENT_EMPLOYEE")
            );
        }

        System.out.println("----------------------------");
    }

    // UPDATE
    private void updateEmployee(Scanner scanner) {

        System.out.println("\n--- Update Employee ---");

        System.out.print(
                "Enter Employee ID to update: "
        );

        int id = scanner.nextInt();
        scanner.nextLine();

        System.out.print(
                "Enter New Employee Name: "
        );

        String name = scanner.nextLine();

        System.out.print(
                "Enter New Age: "
        );

        int age = scanner.nextInt();

        System.out.print(
                "Enter New Salary: "
        );

        double salary =
                scanner.nextDouble();

        scanner.nextLine();

        System.out.print(
                "Enter New Department: "
        );

        String department =
                scanner.nextLine();

        System.out.print(
                "Is Permanent Employee? (true/false): "
        );

        boolean permanentEmployee =
                scanner.nextBoolean();

        int rows = jdbcTemplate.update("""
                UPDATE employee
                SET employee_name = ?,
                    age = ?,
                    salary = ?,
                    department = ?,
                    permanent_employee = ?
                WHERE employee_id = ?
                """,
                name,
                age,
                salary,
                department,
                permanentEmployee,
                id
        );

        if (rows > 0) {

            System.out.println(
                    "Employee updated successfully."
            );

        } else {

            System.out.println(
                    "Employee ID not found."
            );
        }
    }

    // DELETE
    private void deleteEmployee(
            Scanner scanner) {

        System.out.println(
                "\n--- Delete Employee ---"
        );

        System.out.print(
                "Enter Employee ID to delete: "
        );

        int id = scanner.nextInt();

        int rows = jdbcTemplate.update(
                "DELETE FROM employee WHERE employee_id = ?",
                id
        );

        if (rows > 0) {

            System.out.println(
                    "Employee deleted successfully."
            );

        } else {

            System.out.println(
                    "Employee ID not found."
            );
        }
    }
}