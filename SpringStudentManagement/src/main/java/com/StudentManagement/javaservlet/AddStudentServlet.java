package com.StudentManagement.javaservlet;

import com.StudentManagement.dao.StudentDAO;
import com.StudentManagement.model.Student;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class AddStudentServlet extends HttpServlet {
    
    private StudentDAO studentDAO;

    @Override
    public void init() {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        studentDAO = (StudentDAO) context.getBean("studentDAO");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Student student = new Student();
        student.setName(request.getParameter("name"));
        student.setStudentClass(request.getParameter("class"));
        student.setMarks(Integer.parseInt(request.getParameter("marks")));
        student.setGender(request.getParameter("gender"));

        studentDAO.addStudent(student);

        response.sendRedirect("AdminDashboardServlet");
    }
}
