//package com.jdbc.jpademo;
//
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class JpademoApplication implements CommandLineRunner {
//
//    private final EmployeeRepository employeeRepository;
//
//    public JpademoApplication(
//            EmployeeRepository employeeRepository) {
//
//        this.employeeRepository = employeeRepository;
//    }
//
//    public static void main(String[] args) {
//
//        SpringApplication.run(
//                JpademoApplication.class,
//                args
//        );
//    }
//
//    @Override
//    public void run(String... args) {
//
//        Employee employee =
//                new Employee(
//                        101,
//                        "Ravi",
//                        30,
//                        55000,
//                        "IT",
//                        true
//                );
//
//        employeeRepository.save(employee);
//
//        System.out.println(
//                "Employee saved successfully"
//        );
//
//        System.out.println(
//                employeeRepository.findAll()
//        );
//    }
//}

package com.jdbc.jpademo;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
public class JpademoApplication implements CommandLineRunner {

    private final EmployeeRepository employeeRepository;

    public JpademoApplication(
            EmployeeRepository employeeRepository) {

        this.employeeRepository = employeeRepository;
    }

    public static void main(String[] args) {

        SpringApplication.run(
                JpademoApplication.class,
                args
        );
    }

    @Override
    public void run(String... args) {

        Scanner scanner = new Scanner(System.in);

        int choice;

        do {

            System.out.println();
            System.out.println("==============================");
            System.out.println("    EMPLOYEE CRUD USING JPA");
            System.out.println("==============================");
            System.out.println("1. Add Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. View Employee By ID");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Exit");
            System.out.println("==============================");

            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {

                case 1:
                    addEmployee(scanner);
                    break;

                case 2:
                    viewAllEmployees();
                    break;

                case 3:
                    viewEmployeeById(scanner);
                    break;

                case 4:
                    updateEmployee(scanner);
                    break;

                case 5:
                    deleteEmployee(scanner);
                    break;

                case 6:
                    System.out.println(
                            "Application closed."
                    );
                    break;

                default:
                    System.out.println(
                            "Invalid choice."
                    );
            }

        } while (choice != 6);

        scanner.close();
    }

    // CREATE
    private void addEmployee(
            Scanner scanner) {

        System.out.println();
        System.out.println("--- Add Employee ---");

        System.out.print("Enter Employee ID: ");
        int id = scanner.nextInt();
        scanner.nextLine();

        if (employeeRepository.existsById(id)) {

            System.out.println(
                    "Employee ID already exists."
            );

            return;
        }

        System.out.print(
                "Enter Employee Name: "
        );

        String name = scanner.nextLine();

        System.out.print(
                "Enter Age: "
        );

        int age = scanner.nextInt();

        System.out.print(
                "Enter Salary: "
        );

        double salary = scanner.nextDouble();
        scanner.nextLine();

        System.out.print(
                "Enter Department: "
        );

        String department =
                scanner.nextLine();

        System.out.print(
                "Is Permanent Employee? (true/false): "
        );

        boolean permanent =
                scanner.nextBoolean();

        Employee employee =
                new Employee(
                        id,
                        name,
                        age,
                        salary,
                        department,
                        permanent
                );

        employeeRepository.save(employee);

        System.out.println(
                "Employee added successfully."
        );
    }

    // READ ALL
    private void viewAllEmployees() {

        System.out.println();
        System.out.println(
                "--- Employee List ---"
        );

        List<Employee> employees =
                employeeRepository.findAll();

        if (employees.isEmpty()) {

            System.out.println(
                    "No employees found."
            );

            return;
        }

        for (Employee employee : employees) {

            System.out.println(employee);
        }
    }

    // READ BY ID
    private void viewEmployeeById(
            Scanner scanner) {

        System.out.print(
                "Enter Employee ID: "
        );

        int id = scanner.nextInt();

        Employee employee =
                employeeRepository
                        .findById(id)
                        .orElse(null);

        if (employee == null) {

            System.out.println(
                    "Employee not found."
            );

        } else {

            System.out.println(employee);
        }
    }

    // UPDATE
    private void updateEmployee(
            Scanner scanner) {

        System.out.println();
        System.out.println(
                "--- Update Employee ---"
        );

        System.out.print(
                "Enter Employee ID to update: "
        );

        int id = scanner.nextInt();
        scanner.nextLine();

        Employee employee =
                employeeRepository
                        .findById(id)
                        .orElse(null);

        if (employee == null) {

            System.out.println(
                    "Employee not found."
            );

            return;
        }

        System.out.println(
                "Current Employee:"
        );

        System.out.println(employee);

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

        boolean permanent =
                scanner.nextBoolean();

        employee.setEmployeeName(name);
        employee.setAge(age);
        employee.setSalary(salary);
        employee.setDepartment(department);
        employee.setPermanentEmployee(permanent);

        employeeRepository.save(employee);

        System.out.println(
                "Employee updated successfully."
        );
    }

    // DELETE
    private void deleteEmployee(
            Scanner scanner) {

        System.out.println();
        System.out.println(
                "--- Delete Employee ---"
        );

        System.out.print(
                "Enter Employee ID to delete: "
        );

        int id = scanner.nextInt();

        if (employeeRepository.existsById(id)) {

            employeeRepository.deleteById(id);

            System.out.println(
                    "Employee deleted successfully."
            );

        } else {

            System.out.println(
                    "Employee not found."
            );
        }
    }
}