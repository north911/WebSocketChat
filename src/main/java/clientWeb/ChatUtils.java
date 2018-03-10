package clientWeb;

import javax.websocket.Session;
import java.util.HashMap;

public class ChatUtils {

    public ChatUser registreChatUser(String name, String role, Session session){
        ChatUser chatUser = new ChatUser();
        chatUser.setName(name);
        chatUser.setSession(session);
        if("agent".equals(role)){
            chatUser.setAvailable(true);
            chatUser.setRole("agent");
        }
        else{
            chatUser.setAvailable(false);
            chatUser.setRole("client");
        }
        return chatUser;
    }

    public ChatUser findAvailableAgent(HashMap<String, ChatUser> users){
        for (ChatUser chatUser : users.values()) {
            if(chatUser.isAvailable()&&"agent".equals(chatUser.getRole()))
                return chatUser;
        }
        return null;
    }
}
