const API_URL =  "http://localhost:8080/employees";

let employees = [];


// ==========================================
// LOAD EMPLOYEES WHEN PAGE OPENS
// ==========================================

document.addEventListener("DOMContentLoaded", function () {

    loadEmployees();

});


// ==========================================
// GET ALL EMPLOYEES
// ==========================================

async function loadEmployees() {

    try {

        const response = await fetch(API_URL);

        if (!response.ok) {
            throw new Error("Unable to fetch employees");
        }

        employees = await response.json();

        displayEmployees(employees);

        updateDashboard();

    }
    catch (error) {

        console.error(error);

        showMessage(
            "Unable to load employee data."
        );

    }

}


// ==========================================
// DISPLAY EMPLOYEES
// ==========================================

function displayEmployees(employeeList) {

    const tableBody =
        document.getElementById("employeeTableBody");

    tableBody.innerHTML = "";


    if (employeeList.length === 0) {

        tableBody.innerHTML = `

            <tr>

                <td colspan="7"
                    class="text-center py-5 text-muted">

                    <i class="bi bi-people fs-1"></i>

                    <p class="mt-2">
                        No employees found
                    </p>

                </td>

            </tr>

        `;

        return;

    }


    employeeList.forEach(employee => {


        const permanentBadge =
            employee.permanentEmployee

                ? `<span class="badge bg-success">
                        Yes
                   </span>`

                : `<span class="badge bg-secondary">
                        No
                   </span>`;


        const firstLetter =
            employee.employeeName
                ? employee.employeeName.charAt(0).toUpperCase()
                : "?";


        const row = `

            <tr>

                <td>
                    ${employee.employeeId}
                </td>


                <td>

                    <span class="employee-avatar">

                        ${firstLetter}

                    </span>

                    ${employee.employeeName}

                </td>


                <td>
                    ${employee.age}
                </td>


                <td>

                    ₹${Number(employee.salary)
                        .toLocaleString("en-IN")}

                </td>


                <td>

                    <span class="badge bg-primary-subtle text-primary">

                        ${employee.department}

                    </span>

                </td>


                <td>
                    ${permanentBadge}
                </td>


                <td class="text-center">


                    <button
                        class="btn btn-sm btn-outline-primary me-2"
                        onclick="editEmployee(${employee.employeeId})">

                        <i class="bi bi-pencil-square"></i>

                    </button>


                    <button
                        class="btn btn-sm btn-outline-danger"
                        onclick="deleteEmployee(${employee.employeeId})">

                        <i class="bi bi-trash"></i>

                    </button>


                </td>

            </tr>

        `;


        tableBody.innerHTML += row;

    });

}


// ==========================================
// OPEN ADD EMPLOYEE MODAL
// ==========================================

function openAddEmployeeModal() {

    document.getElementById("modalTitle")
        .innerText = "Add Employee";


    document.getElementById("employeeForm")
        .reset();


    document.getElementById("employeeId")
        .value = "";

}


// ==========================================
// SAVE EMPLOYEE
// POST OR PUT
// ==========================================

async function saveEmployee() {

    const employeeId =
        document.getElementById("employeeId").value;


    const employeeName =
        document.getElementById("employeeName")
            .value.trim();


    const age =
        document.getElementById("age").value;


    const salary =
        document.getElementById("salary").value;


    const department =
        document.getElementById("department").value;


    const permanentEmployee =
        document.getElementById(
            "permanentEmployee"
        ).checked;


    // Basic validation

    if (!employeeName ||
        !age ||
        !salary ||
        !department) {

        showMessage(
            "Please enter all employee details."
        );

        return;

    }


    const employee = {

        employeeName: employeeName,

        age: Number(age),

        salary: Number(salary),

        department: department,

        permanentEmployee: permanentEmployee

    };


    let url = API_URL;

    let method = "POST";


    // UPDATE employee

    if (employeeId) {

        url =
            `${API_URL}/${employeeId}`;

        method = "PUT";

    }


    try {

        const response =
            await fetch(
                url,
                {

                    method: method,

                    headers: {

                        "Content-Type":
                            "application/json"

                    },

                    body:
                        JSON.stringify(employee)

                }
            );


        if (!response.ok) {

            throw new Error(
                "Unable to save employee"
            );

        }


        // Close Bootstrap modal

        const modalElement =
            document.getElementById(
                "employeeModal"
            );


        const modal =
            bootstrap.Modal.getInstance(
                modalElement
            );


        modal.hide();


        if (employeeId) {

            showMessage(
                "Employee updated successfully."
            );

        }
        else {

            showMessage(
                "Employee added successfully."
            );

        }


        // Refresh table

        loadEmployees();


    }
    catch (error) {

        console.error(error);

        showMessage(
            "Something went wrong while saving employee."
        );

    }

}


// ==========================================
// EDIT EMPLOYEE
// ==========================================

async function editEmployee(id) {

    try {

        const response =
            await fetch(
                `${API_URL}/${id}`
            );


        if (!response.ok) {

            throw new Error(
                "Employee not found"
            );

        }


        const employee =
            await response.json();


        document.getElementById(
            "employeeId"
        ).value =
            employee.employeeId;


        document.getElementById(
            "employeeName"
        ).value =
            employee.employeeName;


        document.getElementById(
            "age"
        ).value =
            employee.age;


        document.getElementById(
            "salary"
        ).value =
            employee.salary;


        document.getElementById(
            "department"
        ).value =
            employee.department;


        document.getElementById(
            "permanentEmployee"
        ).checked =
            employee.permanentEmployee;


        document.getElementById(
            "modalTitle"
        ).innerText =
            "Edit Employee";


        const modal =
            new bootstrap.Modal(
                document.getElementById(
                    "employeeModal"
                )
            );


        modal.show();


    }
    catch (error) {

        console.error(error);

        showMessage(
            "Unable to load employee."
        );

    }

}


// ==========================================
// DELETE EMPLOYEE
// ==========================================

async function deleteEmployee(id) {

    const confirmation =
        confirm(
            "Are you sure you want to delete this employee?"
        );


    if (!confirmation) {
        return;
    }


    try {

        const response =
            await fetch(
                `${API_URL}/${id}`,
                {

                    method: "DELETE"

                }
            );


        if (!response.ok) {

            throw new Error(
                "Unable to delete employee"
            );

        }


        showMessage(
            "Employee deleted successfully."
        );


        loadEmployees();


    }
    catch (error) {

        console.error(error);

        showMessage(
            "Unable to delete employee."
        );

    }

}


// ==========================================
// SEARCH
// ==========================================

function searchEmployees() {

    const searchText =
        document.getElementById(
            "searchInput"
        )
        .value
        .toLowerCase();


    const filteredEmployees =
        employees.filter(employee =>

            employee.employeeName
                .toLowerCase()
                .includes(searchText)

            ||

            employee.department
                .toLowerCase()
                .includes(searchText)

        );


    displayEmployees(
        filteredEmployees
    );

}


// ==========================================
// DASHBOARD COUNTS
// ==========================================

function updateDashboard() {


    // Total employees

    document.getElementById(
        "totalEmployees"
    ).innerText =
        employees.length;



    // Permanent employee count

    const permanentCount =
        employees.filter(
            employee =>
                employee.permanentEmployee
        ).length;


    document.getElementById(
        "permanentEmployees"
    ).innerText =
        permanentCount;



    // Unique departments

    const departments =
        new Set(
            employees.map(
                employee =>
                    employee.department
            )
        );


    document.getElementById(
        "totalDepartments"
    ).innerText =
        departments.size;

}


// ==========================================
// TOAST MESSAGE
// ==========================================

function showMessage(message) {

    document.getElementById(
        "toastMessage"
    ).innerText =
        message;


    const toast =
        new bootstrap.Toast(
            document.getElementById(
                "messageToast"
            )
        );


    toast.show();

}