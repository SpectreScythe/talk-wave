package com.talkwave.api;

import java.io.*;
import java.sql.*;
import java.util.logging.Logger;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.talkwave.Env;

@WebServlet(name = "addFriendServlet", value = "/api-add-friend")
public class AddFriendServlet extends HttpServlet {
    public void init() {}
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String senderID = request.getParameter("senderID");
        String receiverID = request.getParameter("receiverID");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Env.DB_URL, Env.DB_USERNAME, Env.DB_PASSWORD);
            PreparedStatement ps = con.prepareStatement("INSERT INTO friends VALUES (DEFAULT, ?, ?, ''), (DEFAULT, ?, ?, '')");
            ps.setString(1, senderID);
            ps.setString(2, receiverID);
            ps.setString(3, receiverID);
            ps.setString(4, senderID);
            ps.execute();

            out.println("{\"status\":\"OK\"}");
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("{\"error\":\""+e.getMessage()+"\"}");
        }
    }

    public void destroy() {
    }
}