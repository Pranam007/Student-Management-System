package com.StudentManagement.javaservlet;

import com.StudentManagement.dao.StudentDAO;
import com.StudentManagement.model.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

public class AdminDashboardServlet extends HttpServlet {

    private StudentDAO studentDAO;

    @Override
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        studentDAO = (StudentDAO) context.getBean("studentDAO");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("admin") == null) {
            response.sendRedirect("index.html");
            return;
        }

        List<Student> students = studentDAO.getAllStudents();

        request.setAttribute("students", students);
        request.setAttribute("studentCount", students.size());  

        request.getRequestDispatcher("adminDashboard.jsp").forward(request, response);
    }
}
