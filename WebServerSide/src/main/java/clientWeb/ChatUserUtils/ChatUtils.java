package clientWeb.ChatUserUtils;

import clientWeb.ChatUserUtils.ChatUser;
import clientWeb.MessageUtils.Message;

import javax.websocket.EncodeException;
import javax.websocket.Session;
import java.io.IOException;
import java.util.HashMap;

public class ChatUtils {

    private HashMap<String, ChatUser> users;

    public ChatUtils(HashMap<String, ChatUser> users) {
        this.users = users;
    }

    public ChatUser registreChatUser(String name, String role, Session session) {
        ChatUser chatUser = new ChatUser();
        chatUser.setName(name);
        chatUser.setSession(session);
        if ("agent".equals(role)) {
            chatUser.setAvailable(true);
            chatUser.setRole("agent");
        } else {
            chatUser.setAvailable(false);
            chatUser.setRole("client");
        }
        return chatUser;
    }

    private ChatUser findAvailableAgent(HashMap<String, ChatUser> users) {
        for (ChatUser chatUser : users.values()) {
            if (chatUser.isAvailable() && "agent".equals(chatUser.getRole()))
                return chatUser;
        }
        return null;
    }

    public boolean tryAssignAgent(HashMap<String, ChatUser> users, ChatUser chatUser) throws IOException, EncodeException{

        ChatUser agent = findAvailableAgent(users);
        if (agent != null) {
            agent.setAvailable(false);
            agent.setUserToSession(chatUser.getSession());
            chatUser.setUserToSession(agent.getSession());
            Message message = new Message();
            message.setFrom(chatUser.getName());
            message.setContent("connected to the chat");
            sendMessage(message,agent);
            message.setFrom(" ");
            message.setContent("connected");
            sendMessage(message,chatUser);
            return true;
        }
        return false;
    }

    public void disconnectUsers(ChatUser chatUser) throws IOException, EncodeException {
        Message message = new Message();
        message.setFrom(chatUser.getName());
        message.setContent("Disconnected!");
        if (chatUser.getUserToSession() != null) {
            sendMessage(message, users.get(chatUser.getUserToSession().getId()));
            users.get(chatUser.getUserToSession().getId()).setUserToSession(null);
            users.get(chatUser.getUserToSession().getId()).setAvailable(true);
        }
        chatUser.setUserToSession(null);

    }

    public void sendMessage(Message message, ChatUser chatUser) throws IOException, EncodeException {
        chatUser.getSession().getBasicRemote().sendObject(message);

    }
}
