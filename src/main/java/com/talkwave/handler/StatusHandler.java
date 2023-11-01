package com.talkwave.handler;

import com.talkwave.Env;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class StatusHandler {
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    Logger logger = Logger.getLogger(MessageHandler.class.getName());
    public StatusHandler() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(Env.DB_URL, Env.DB_USERNAME, Env.DB_PASSWORD);
    }
    public void setUserStatus(String id, String status) throws SQLException  {
        ps = con.prepareStatement("UPDATE users SET status=? WHERE user_id = ?");

        ps.setString(1, status);
        ps.setString(2, id);
        ps.execute();
    }

    public void setMsgStatus(String senderID, String receiverID) throws SQLException  {
        ps = con.prepareStatement("UPDATE messages SET read_receipt = 'read' WHERE sender_id = ? AND receiver_id = ? AND read_receipt = 'unread'");

        ps.setString(1, senderID);
        ps.setString(2, receiverID);
        ps.execute();
    }

    public List<String> getFriendsList(String id) throws SQLException {
        ps = con.prepareStatement("SELECT y_id FROM friends WHERE x_id = ?");
        ps.setString(1, id);
        rs = ps.executeQuery();
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString(1));
        }
        return list;
    }

    public List<String> getFriendsOnlineList(String id) throws SQLException {
        ps = con.prepareStatement("SELECT y_id, status FROM friends f JOIN users u on f.y_id = u.user_id WHERE x_id = ?");
        ps.setString(1, id);
        rs = ps.executeQuery();
        List<String> list = new ArrayList<>();
        while (rs.next()) {
            list.add(rs.getString(1)+":"+rs.getString(2));
        }
        return list;
    }

    public void close() throws SQLException {
        con.close();
    }
}
