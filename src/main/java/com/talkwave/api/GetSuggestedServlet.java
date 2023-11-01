package com.talkwave.api;

import java.io.*;
import java.sql.*;
import java.util.logging.Logger;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.talkwave.Env;

@WebServlet(name = "getSuggestedServlet", value = "/api-get-suggested")
public class GetSuggestedServlet extends HttpServlet {
    public void init() {}
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String senderID = request.getParameter("senderID");
        response.setContentType("application/json");
        PrintWriter out = response.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection(Env.DB_URL, Env.DB_USERNAME, Env.DB_PASSWORD);
            PreparedStatement ps = con.prepareStatement("SELECT user_id, username, password, profile_name, image from users WHERE user_id NOT IN (SELECT y_id FROM friends WHERE x_id = ?) AND user_id <> ? ORDER BY user_id DESC LIMIT 10");
            ps.setString(1, senderID);
            ps.setString(2, senderID);
            ResultSet rs = ps.executeQuery();

            ObjectMapper objectMapper = new ObjectMapper();
            ArrayNode arrayNode = objectMapper.createArrayNode();

            while (rs.next()) {
                ObjectNode userNode = objectMapper.createObjectNode();
                userNode.put("id", rs.getString(1));
                userNode.put("username", rs.getString(2));
                userNode.put("password", rs.getString(3));
                userNode.put("profileName", rs.getString(4));
                userNode.put("image", rs.getString(5));
                arrayNode.add(userNode);
            }

            String json = arrayNode.toString();
            out.println(json);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            out.println("{\"error\":\""+e.getMessage()+"\"}");
        }
    }
    public void destroy() {}
}