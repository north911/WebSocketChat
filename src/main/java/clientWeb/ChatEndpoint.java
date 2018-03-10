package clientWeb;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;

import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/chat/{username}/{userrole}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class ChatEndpoint {

    private ChatUtils chatUtils = new ChatUtils();
    private Session session;
    private ChatUser chatUser;
    //private static final Set<ChatEndpoint> SERVER_ENDPOINTS = new CopyOnWriteArraySet<>();
    private static HashMap<String, ChatUser> users = new HashMap<>();

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("userrole") String userrole) throws IOException, EncodeException {

        this.session = session;
        //SERVER_ENDPOINTS.add(this);
        chatUser = chatUtils.registreChatUser(username, userrole, session);
        users.put(session.getId(), chatUser);
        Message message = new Message();
        message.setFrom(username);
        message.setContent("Connected!");
        sendMessage(message, chatUser);
    }

    @OnMessage
    public void onMessage(Session session, Message message) throws IOException, EncodeException {
        message.setFrom(users.get(session.getId()).getName());
        sendMessage(message,chatUser);
    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
       // SERVER_ENDPOINTS.remove(this);
        Message message = new Message();
        message.setFrom(users.get(session.getId()).getName());
        message.setContent("Disconnected!");
        sendMessage(message,users.get(session.getId()));
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        // Do error handling here
    }

    private void sendMessage(Message message, ChatUser chatUser) throws IOException, EncodeException{
        chatUser.getSession().getBasicRemote().sendObject(message);
        if(chatUser.getUserToSession()==null && chatUser.getRole().equals("client")) {
            ChatUser agent = chatUtils.findAvailableAgent(users);
            agent.getSession().getBasicRemote().sendObject(message);
            if(agent!=null)
                agent.getSession().getBasicRemote().sendObject(message);
        }

    }

}