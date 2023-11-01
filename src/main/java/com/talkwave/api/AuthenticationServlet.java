package com.talkwave.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.talkwave.Env;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;

@WebServlet(name = "authenticationServlet", value = "/api-authenticate")
public class AuthenticationServlet extends HttpServlet {
    Connection con = null;
    PreparedStatement ps;
    ResultSet rs;
    String jsonMsgData;
    Logger logger = Logger.getLogger(AuthenticationServlet.class.getName());
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String username = req.getParameter("username");
        String password = req.getParameter("password");
        PrintWriter out = resp.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();
        int rowCount = 0;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(Env.DB_URL, Env.DB_USERNAME, Env.DB_PASSWORD);
            if (password == null) {
                ps = con.prepareStatement("SELECT * FROM users WHERE username = ?");
                ps.setString(1, username);
                rs = ps.executeQuery();
                while (rs.next()) {
                    rowCount++;
                }
                jsonMsgData = "{\"isValid\":\"true\"}";
            } else {
                ps = con.prepareStatement("SELECT * FROM users WHERE username = ? AND password = ?");
                ps.setString(1, username);
                ps.setString(2, password);

                rs = ps.executeQuery();

                while (rs.next()) {
                    rowCount++;
                    ObjectNode userNode = objectMapper.createObjectNode();
                    userNode.put("id", rs.getString(1));
                    userNode.put("username", rs.getString(2));
                    userNode.put("profileName", rs.getString(4));
                    userNode.put("image", rs.getString(6));
                    userNode.put("isValid", "true");
                    jsonMsgData = userNode.toString();
                }
            }

            if (rowCount < 1) {
                jsonMsgData = "{\"isValid\":\"false\"}";
            }

            out.println(jsonMsgData);
        } catch (ClassNotFoundException | SQLException e) {
            logger.info("Error: " + e.getMessage());
        }

    }
}
