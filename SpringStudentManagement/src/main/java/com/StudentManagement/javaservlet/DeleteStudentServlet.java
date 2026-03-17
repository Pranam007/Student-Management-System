package com.StudentManagement.javaservlet;

import com.StudentManagement.dao.StudentDAO;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class DeleteStudentServlet extends HttpServlet {

    private StudentDAO studentDAO;

    @Override
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        studentDAO = (StudentDAO) context.getBean("studentDAO");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String studentIdParam = request.getParameter("student_id");

            if (studentIdParam == null || studentIdParam.isEmpty()) {
                response.sendRedirect("AdminDashboardServlet?error=Student ID is missing");
                return;
            }

            int studentId = Integer.parseInt(studentIdParam);

            boolean isDeleted = studentDAO.deleteStudent(studentId);

            if (isDeleted) {
                response.sendRedirect("AdminDashboardServlet?message=Student Deleted Successfully");
            } else {
                response.sendRedirect("AdminDashboardServlet?error=Student not found");
            }
        } catch (NumberFormatException e) {
            response.sendRedirect("AdminDashboardServlet?error=Invalid student ID format");
        } catch (IOException e) {
            response.sendRedirect("AdminDashboardServlet?error=Something went wrong");
        }
    }
}
