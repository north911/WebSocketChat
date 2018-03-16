package clientWeb.Endpoint;

import clientWeb.ChatUserUtils.ChatUser;
import clientWeb.ChatUserUtils.ChatUtils;
import clientWeb.MessageUtils.Message;
import clientWeb.MessageCoders.MessageDecoder;
import clientWeb.MessageCoders.MessageEncoder;
import clientWeb.MessageUtils.MessageTypeAnalyzer;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import javax.websocket.EncodeException;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(value = "/{userrole}/chat/{username}", decoders = MessageDecoder.class, encoders = MessageEncoder.class)
public class ChatEndpoint {

    private Logger logger = Logger.getRootLogger();
    private Session session;
    private ChatUser chatUser;
    private static HashMap<String, ChatUser> users = new HashMap<>();
    private ChatUtils chatUtils = new ChatUtils(users);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("userrole") String userrole) throws IOException, EncodeException {

        this.session = session;
        chatUser = chatUtils.registreChatUser(username, userrole, session);
        users.put(session.getId(), chatUser);
        Message message = new Message();
        message.setFrom(username);
        message.setContent("Connected!");
        chatUtils.sendMessage(message, chatUser);
        logger.log(Level.INFO, username + " connected to chat");
    }

    @OnMessage
    public void onMessage(Session session, Message message) throws IOException, EncodeException {
        switch (MessageTypeAnalyzer.typeOfMessage(message.getContent())) {
            case ORDINARY_MESSAGE:
                message.setFrom(users.get(session.getId()).getName());
                chatUtils.sendMessage(message, users.get(session.getId()));
                if (users.get(session.getId()).getUserToSession() == null &&
                        users.get(session.getId()).getRole().equals("client")) {
                    chatUtils.tryAssignAgent(users, users.get(session.getId()));
                }
                if (users.get(session.getId()).getUserToSession() != null)
                    chatUtils.sendMessage(message, users.get(users.get(session.getId()).getUserToSession().getId()));
                else {
                    message.setFrom("");
                    message.setContent("НЕТ СОБЕСЕДНИКА");
                    chatUtils.sendMessage(message, users.get(session.getId()));
                }
                break;

            case LEAVE_MESSAGE:
                message.setFrom("");
                if (users.get(session.getId()).getUserToSession() != null &&
                        users.get(session.getId()).getRole().equals("client")) {
                    message.setContent("disconnected");
                    chatUtils.sendMessage(message, users.get(session.getId()));
                    chatUtils.disconnectUsers(users.get(session.getId()));
                }

                break;
        }


    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {
        ChatUser user = users.get(session.getId());
        chatUtils.disconnectUsers(user);
        logger.log(Level.INFO, user.getName() + " connected to chat");
        users.remove(session.getId());
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.log(Level.INFO, "error");
    }

}