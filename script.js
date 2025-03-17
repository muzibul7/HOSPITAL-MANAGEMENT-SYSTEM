document.addEventListener("DOMContentLoaded", function () {
    alert("Welcome to Medivance Hospital Management System!");

    // Function to add a new staff member
    function addStaffMember() {
        const tableBody = document.querySelector("table tbody");
        const newRow = document.createElement("tr");
        newRow.innerHTML = `
            <td>ST004</td>
            <td>New Staff Member</td>
            <td>Department</td>
            <td>Role</td>
            <td><span class="status-active">Active</span></td>
            <td>
                <button class="btn btn-secondary" onclick="editStaffMember(this)">Edit</button>
                <button class="btn btn-danger" onclick="removeStaffMember(this)">Delete</button>
            </td>
        `;
        tableBody.appendChild(newRow);
    }

    // Function to remove a staff member
    function removeStaffMember(button) {
        const row = button.closest("tr");
        row.remove();
    }

    // Attach event listener to the Add New Staff Member button
    const addButton = document.querySelector(".btn-primary");
    if (addButton) {
        addButton.addEventListener("click", addStaffMember);
    }

    // Placeholder function for editing a staff member
    window.editStaffMember = function(button) {
        alert("Edit functionality is not yet implemented.");
    };

    // Expose removeStaffMember to the global scope
    window.removeStaffMember = removeStaffMember;

    // Login functionality
    const loginForm = document.querySelector("form");
    if (loginForm) {
        loginForm.addEventListener("submit", function(event) {
            event.preventDefault();
            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;

            // Hardcoded credentials for demonstration
            const validUsername = "admin";
            const validPassword = "password123";

            if (username === validUsername && password === validPassword) {
                alert("Login successful!");
                window.location.href = "dashboard.html"; // Redirect to dashboard
            } else {
                alert("Invalid username or password.");
            }
        });
    }
});
