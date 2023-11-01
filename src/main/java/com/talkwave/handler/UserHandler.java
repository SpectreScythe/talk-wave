package com.talkwave.handler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talkwave.Env;

import java.sql.*;
import java.util.logging.Logger;

public class UserHandler {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    public UserHandler() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(Env.DB_URL, Env.DB_USERNAME, Env.DB_PASSWORD);
    }

    public String[] getUserDetails(String username) {
        String[] data = new String[4];
        try {
            ps = con.prepareStatement("SELECT * FROM users WHERE user_id = ?");
            ps.setString(1, username);
            rs = ps.executeQuery();
            while (rs.next()) {
                data[0] = rs.getString(1);
                data[1] = rs.getString(2);
                data[2] = rs.getString(4);
                data[3] = rs.getString(5);
            }
            return data;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void close() throws SQLException {
        con.close();
    }
}
