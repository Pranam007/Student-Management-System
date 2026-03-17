package com.StudentManagement.javaservlet;

import com.StudentManagement.dao.StudentDAO;
import com.StudentManagement.model.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class UpdateStudentServlet extends HttpServlet {

    private StudentDAO studentDAO;

    @Override
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        studentDAO = (StudentDAO) context.getBean("studentDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String studentIdParam = request.getParameter("student_id");

            if (studentIdParam == null || studentIdParam.isEmpty()) {
                response.sendRedirect("AdminDashboardServlet?error=Student ID is missing");
                return;
            }

            int studentId = Integer.parseInt(studentIdParam);
            Student student = studentDAO.getStudentById(studentId);

            if (student != null) {
                request.setAttribute("studentId", student.getId());
                request.setAttribute("name", student.getName());
                request.setAttribute("class1", student.getStudentClass());
                request.setAttribute("marks", student.getMarks());
                request.setAttribute("gender", student.getGender());

                request.getRequestDispatcher("updateStudent.jsp").forward(request, response);
            } else {
                response.sendRedirect("AdminDashboardServlet?error=Student not found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("AdminDashboardServlet?error=Invalid student ID format");
        } catch (ServletException | IOException e) {
            response.sendRedirect("AdminDashboardServlet?error=Something went wrong");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            int studentId = Integer.parseInt(request.getParameter("student_id"));
            String name = request.getParameter("name");
            String class1 = request.getParameter("class");
            int marks = Integer.parseInt(request.getParameter("marks"));
            String gender = request.getParameter("gender");

            Student student = studentDAO.getStudentById(studentId);

            if (student != null) {
                student.setName(name);
                student.setStudentClass(class1);
                student.setMarks(marks);
                student.setGender(gender);

                boolean isUpdated = studentDAO.updateStudent(student);

                if (isUpdated) {
                    response.sendRedirect("AdminDashboardServlet?message=Student Updated Successfully");
                } else {
                    response.sendRedirect("AdminDashboardServlet?error=Failed to update student");
                }
            } else {
                response.sendRedirect("AdminDashboardServlet?error=Student not found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("AdminDashboardServlet?error=Invalid input format");
        } catch (IOException e) {
            response.sendRedirect("AdminDashboardServlet?error=Something went wrong");
        }
    }
}
