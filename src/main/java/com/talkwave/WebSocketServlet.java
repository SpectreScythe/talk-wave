package com.talkwave;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.talkwave.handler.MessageHandler;
import com.talkwave.handler.StatusHandler;

import javax.servlet.ServletException;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Logger;

@ServerEndpoint("/websocket")
public class WebSocketServlet {
    Logger logger = Logger.getLogger(WebSocketServlet.class.getName());
    private static final Map<String, Session> sessions = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
    }

    @OnMessage
    public void onMessage(String message, Session session) throws IOException, ServletException, SQLException, ClassNotFoundException {
        if (message.contains("$id:")) {
            String id = message.substring(4);
            sessions.put(id, session);
            handleStatus(id, "online");
            StatusHandler sh = new StatusHandler();
            List<String> lis = sh.getFriendsOnlineList(id);
            return;
        }

        if (message.contains("$offline:")) {
            String id = message.substring(9);
            handleStatus(id, "offline");
            return;
        }

        if (message.contains("$chat-active:")) {
            String[] chatID = message.substring(13).split("&");
            StatusHandler statusHandler = new StatusHandler();
            /* 0: Sender; 1: Receiver */
            statusHandler.setMsgStatus(chatID[0], chatID[1]);
            Session msgSenderSession = sessions.get(chatID[0]);
            try {
                msgSenderSession.getAsyncRemote().sendText(message);
            } catch (NullPointerException e) {
                session.getAsyncRemote().sendText("NullPointerException: " + e.getMessage());
            }
            return;
        }

        if (message.contains("$chat-inactive:")) {
            String[] chatID = message.substring(15).split("&");
            /* 0: ChatUser; 1: ChatViewer */
            Session chatUserSession = sessions.get(chatID[0]);
            try {
                chatUserSession.getAsyncRemote().sendText(message);
            } catch (NullPointerException e) {
                logger.info("NullPointerException: " + e.getMessage());
                session.getAsyncRemote().sendText("NullPointerException: " + e.getMessage());
            }
            return;
        }

        MessageHandler msgHandler = new MessageHandler();
        msgHandler.insertMessage(message);
        msgHandler.updateLastMessage(message);
        msgHandler.close();

        ObjectMapper mapper = new ObjectMapper();

        JsonNode msg = mapper.readTree(message);
        String receiverID = msg.get("receiverID").toString().replace("\"", "");

        Session recipientSession = sessions.get(receiverID);

        try {
            recipientSession.getAsyncRemote().sendText(message);
        } catch (NullPointerException e) {
            e.printStackTrace();
            session.getAsyncRemote().sendText("RecipientNullException: " + e);
        }

    }

    @OnClose
    public void onClose(Session session) {
        String closedSessionId = null;
        for (Map.Entry<String, Session> entry : sessions.entrySet()) {
            if (entry.getValue().equals(session)) {
                closedSessionId = entry.getKey();
                break;
            }
        }

        if (closedSessionId != null)
            sessions.remove(closedSessionId);
    }

    public void handleStatus(String id, String status) throws SQLException, ClassNotFoundException, IOException {
        StatusHandler statusHandler = new StatusHandler();
        statusHandler.setUserStatus(id, status);
        List<String> list = statusHandler.getFriendsList(id);
        for (String item : list) {
            Session s = sessions.get(item);
            if (s != null) {
                s.getBasicRemote().sendText("$" + status + ":" + id);
            }
        }
        statusHandler.close();
    }

}
