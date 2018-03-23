package clientWeb.Endpoint;

import clientWeb.ChatUserUtils.*;
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
    private static HashMap<String, Agent> agents = new HashMap<>();
    private static HashMap<String, Client> clients = new HashMap<>();
    private ChatUtils chatUtils = new ChatUtils(agents, clients);

    @OnOpen
    public void onOpen(Session session, @PathParam("username") String username, @PathParam("userrole") String userrole) throws IOException, EncodeException {
        ChatUser chatUser;
        switch (TypeUserAnalyzer.typeOfUser(userrole)) {
            case CLIENT:
                chatUser = chatUtils.registreChatUser(username, userrole, session);
                clients.put(session.getId(), (Client) chatUser);
                break;
            default:
                chatUser = chatUtils.registreChatUser(username, userrole, session);
                agents.put(session.getId(), (Agent) chatUser);
                break;
        }

        Message message = new Message();
        message.setFrom(username);
        message.setContent("Connected!");
        chatUtils.sendMessage(message, chatUser);
        logger.log(Level.INFO, username + " connected to chat");
    }

    @OnMessage
    public void onMessage(Session session, Message message) throws IOException, EncodeException {
        switch (MessageTypeAnalyzer.typeOfMessage(message.getContent(), session, clients)) {
            case CLIENT_MESSAGE:
                message.setFrom(clients.get(session.getId()).getName());
                chatUtils.sendMessage(message, clients.get(session.getId()));
                if (clients.get(session.getId()).getUserToSession() == null) {
                    chatUtils.tryAssignAgent(agents, clients.get(session.getId()));
                }
                if (clients.get(session.getId()).getUserToSession() != null)
                    chatUtils.sendMessage(message, agents.get(clients.get(session.getId()).getUserToSession().getId()));
                else {
                    message.setFrom("");
                    message.setContent("НЕТ СОБЕСЕДНИКА");
                    chatUtils.sendMessage(message, clients.get(session.getId()));
                }
                break;

            case AGENT_MESSAGE:
                message.setFrom(agents.get(session.getId()).getName());
                chatUtils.sendMessage(message, agents.get(session.getId()));
                if (agents.get(session.getId()).getUserToSession() != null)
                    chatUtils.sendMessage(message, clients.get(agents.get(session.getId()).getUserToSession().getId()));
                else {
                    message.setFrom("");
                    message.setContent("НЕТ СОБЕСЕДНИКА");
                    chatUtils.sendMessage(message, agents.get(session.getId()));
                }
                break;


            case LEAVE_MESSAGE:
                message.setFrom("");
                if (clients.get(session.getId()).getUserToSession() != null) {
                    message.setContent("disconnected");
                    chatUtils.sendMessage(message, clients.get(session.getId()));
                    chatUtils.disconnectUsers(clients.get(session.getId()));
                }

                break;
        }


    }

    @OnClose
    public void onClose(Session session) throws IOException, EncodeException {

        ChatUser user;
        if(clients.containsKey(session.getId())){
        user = clients.get(session.getId());
        chatUtils.disconnectUsers(user);
        clients.remove(session.getId());}
        else
        {
            user = agents.get(session.getId());
            chatUtils.disconnectUsers(user);
            agents.remove(session.getId());

        }
        logger.log(Level.INFO, user.getName() + " disconnected from chat");
    }

    @OnError
    public void onError(Session session, Throwable throwable) {
        logger.log(Level.INFO, "error");
    }

}