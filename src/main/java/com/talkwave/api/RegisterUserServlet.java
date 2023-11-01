package com.talkwave.api;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.talkwave.Env;
import com.talkwave.JSPServlet;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;

@WebServlet(name = "registerUserServlet", value = "/api-register-user")
public class RegisterUserServlet extends HttpServlet {
    Connection con = null;
    PreparedStatement ps;
    ResultSet rs;
    String jsonMsgData;
    Logger logger = Logger.getLogger(JSPServlet.class.getName());
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
        ObjectMapper objectMapper = new ObjectMapper();

        JsonNode jsonNode = objectMapper.readTree(req.getInputStream());

        String username = jsonNode.get("username").asText();
        String password = jsonNode.get("password").asText();
        String profileName = jsonNode.get("profileName").asText();
        String image = jsonNode.get("image").asText();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(Env.DB_URL, Env.DB_USERNAME, Env.DB_PASSWORD);
            ps = con.prepareStatement("INSERT INTO users (username, password, profile_name, image) VALUES (?,?,?,?)");

            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, profileName);
            ps.setString(4, image);

            ps.execute();

            out.println("{\"status\":\"OK\"}");
        } catch (ClassNotFoundException | SQLException e) {
            logger.info("Error: " + e.getMessage());
            out.println("{\"status\":\"Error\"}");
        }

    }
}
