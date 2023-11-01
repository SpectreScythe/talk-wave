package com.talkwave.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.talkwave.Env;
import com.talkwave.JSPServlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.logging.Logger;

@WebServlet(name = "getChatMsgServlet", value = "/api-get-chat-msg")
public class GetChatMsgServlet extends HttpServlet {
    Connection con = null;
    PreparedStatement ps;
    ResultSet rs;
    String jsonMsgData;
    Logger logger = Logger.getLogger(JSPServlet.class.getName());
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        String senderID = req.getParameter("senderID");
        String receiverID = req.getParameter("receiverID");
        PrintWriter out = resp.getWriter();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(Env.DB_URL, Env.DB_USERNAME, Env.DB_PASSWORD);
            ps = con.prepareStatement("SELECT * FROM messages WHERE (sender_id = ? AND receiver_id = ?) OR (sender_id = ? AND receiver_id = ?) ORDER BY message_id");

            ps.setInt(1, Integer.parseInt(senderID));
            ps.setInt(2, Integer.parseInt(receiverID));
            ps.setInt(3, Integer.parseInt(receiverID));
            ps.setInt(4, Integer.parseInt(senderID));

            rs = ps.executeQuery();

            ObjectMapper mapper = new ObjectMapper();
            ArrayNode arrayNode = mapper.createArrayNode();

            while (rs.next()) {
                ObjectNode userNode = mapper.createObjectNode();
                userNode.put("id", rs.getString(1));
                userNode.put("senderID", rs.getString(2));
                userNode.put("receiverID", rs.getString(3));
                userNode.put("content", rs.getString(4));
                userNode.put("timestamp", rs.getString(5));
                userNode.put("readReceipt", rs.getString(6));
                arrayNode.add(userNode);
            }

            jsonMsgData = arrayNode.toString();

            out.println(jsonMsgData);
        } catch (ClassNotFoundException | SQLException e) {
            logger.info("Error: " + e.getMessage());
        }

    }
}
