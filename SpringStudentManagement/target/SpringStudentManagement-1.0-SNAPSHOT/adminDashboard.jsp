<%@ page import="java.util.ArrayList" %>
<%@ page import="com.StudentManagement.model.Student" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Admin Dashboard</title>
    <style>
        body { font-family: Arial, sans-serif; background-color: #e0f7fa; margin: 0; }
        .navbar { 
            background-color: #4CAF50; 
            padding: 15px; 
            color: white; 
            text-align: center; 
            display: flex; 
            justify-content: space-between; /* Aligns logo and logout button */
            align-items: center; /* Vertically center content */
        }
        .navbar h2 { margin: 0; }
        .navbar .logout-btn {
            background-color: #d9534f; 
            color: white; 
            padding: 8px 16px; 
            border: none; 
            border-radius: 5px; 
            cursor: pointer; 
            font-size: 16px;
            text-decoration: none;
        }
        .navbar .logout-btn:hover {
            background-color: #c9302c;
        }
        .container { padding: 20px; }
        .button { 
            background-color: #4CAF50; 
            color: white; 
            padding: 12px 20px; 
            border: none; 
            border-radius: 8px; 
            cursor: pointer; 
            font-size: 16px;
            transition: background-color 0.3s ease, transform 0.2s ease;
        }
        .button:hover { 
            background-color: #45a049; 
            transform: scale(1.05);
        }
        .button-container {
            display: flex;
            justify-content: space-around;
            gap: 15px;
            margin-top: 20px;
            flex-wrap: wrap; /* Ensures buttons are responsive */
        }
        table { width: 100%; border-collapse: collapse; margin-top: 20px; background: white; }
        table, th, td { border: 1px solid #ddd; }
        th, td { padding: 8px; text-align: left; }

        .logout-container {
            display: flex;
            justify-content: center;
            margin-top: 30px;
        }
    </style>
</head>
<body>
    <div class="navbar">
        <h2>Admin Dashboard</h2>
        <a href="LogoutServlet" class="logout-btn">Logout</a> <!-- Logout button added to navbar -->
    </div>

    <div class="container">
        <h3>Student Data</h3>
        
        <%
            Integer studentCount = (Integer) request.getAttribute("studentCount");
        %>
        <p><strong>Total Students: <%= (studentCount != null) ? studentCount : 0 %></strong></p>

        <table>
            <thead>
                <tr>
                    <th>Student ID</th>
                    <th>Name</th>
                    <th>Class</th>
                    <th>Marks</th>
                    <th>Gender</th>
                    <th>Actions</th>
                </tr>
            </thead>
            <tbody>
                <%
                    Object studentsObj = request.getAttribute("students");
                    ArrayList<Student> students = null;

                    if (studentsObj instanceof ArrayList<?>) {
                        students = (ArrayList<Student>) studentsObj;
                    }

                    if (students != null && !students.isEmpty()) {
                        for (Student student : students) {
                %>
                <tr>
                    <td><%= student.getId() %></td>
                    <td><%= student.getName() %></td>
                    <td><%= student.getStudentClass() %></td>
                    <td><%= student.getMarks() %></td>
                    <td><%= student.getGender() %></td>
                    <td>
                        <a href="UpdateStudentServlet?student_id=<%= student.getId() %>" class="button">Update</a>
                        <form action="DeleteStudentServlet" method="post" style="display: inline;">
                            <input type="hidden" name="student_id" value="<%= student.getId() %>">
                            <button type="submit" class="button">Delete</button>
                        </form>
                    </td>
                </tr>
                <%
                        }
                    } else {
                %>
                <tr>
                    <td colspan="6" style="text-align: center; color: red;">No students found.</td>
                </tr>
                <% } %>
            </tbody>
        </table>
        
        <!-- Button Container for Add, Delete, Update buttons -->
        <div class="button-container">
            <button class="button" onclick="window.location.href='addStudent.html'">Add Student</button>
            <button class="button" onclick="window.location.href='deleteStudent.html'">Delete Student using ID</button>
        </div>
    </div>
</body>
</html>
