package clientWeb.MessageUtils;

import clientWeb.ChatUserUtils.Client;

import javax.websocket.Session;
import java.util.HashMap;

public class MessageTypeAnalyzer {

    public static MessageTypes typeOfMessage(String message, Session session, HashMap<String, Client> clients) {
        if("/leave".equals(message))
            return MessageTypes.LEAVE_MESSAGE;
        if(clients.containsKey(session.getId()))
            return MessageTypes.CLIENT_MESSAGE;
        else
            return MessageTypes.AGENT_MESSAGE;
    }
}
