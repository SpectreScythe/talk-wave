package com.talkwave;

import com.talkwave.handler.UserHandler;

import java.io.IOException;
import java.sql.SQLException;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/jsp")
public class JSPServlet extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String auth = (String) req.getAttribute("authenticated");
        if (auth == null || auth.equals("")) {
            RequestDispatcher dispatcher = req.getRequestDispatcher("auth.jsp");
//            RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
            dispatcher.forward(req, resp);
            return;
        }
        else if (auth.equals("login")) {
            String username = (String) req.getAttribute("username");
            try {
                UserHandler userHandler = new UserHandler();
                String[] data = userHandler.getUserDetails(username);
                req.setAttribute("userID", data[0]);
                req.setAttribute("profileName", data[2]);
                req.setAttribute("image", data[3]);
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }

        RequestDispatcher dispatcher = req.getRequestDispatcher("index.jsp");
        dispatcher.forward(req, resp);
    }
}


