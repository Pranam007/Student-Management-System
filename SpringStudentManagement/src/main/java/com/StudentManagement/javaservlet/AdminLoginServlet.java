package com.StudentManagement.javaservlet;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import java.io.IOException;

public class AdminLoginServlet extends HttpServlet {


    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");

        if ((username.equals("anil") && password.equals("anil"))||
                (username.equals("pranam") && password.equals("pranam"))) {
            HttpSession session = request.getSession();
            session.setAttribute("admin", "loggedIn");

            response.sendRedirect("AdminDashboardServlet");
        } else {
            response.sendRedirect("index.html");
        }
    }
}
