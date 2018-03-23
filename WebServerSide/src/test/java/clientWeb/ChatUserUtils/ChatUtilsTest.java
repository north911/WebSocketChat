package clientWeb.ChatUserUtils;

import static org.junit.Assert.*;

import clientWeb.MessageUtils.Message;
import org.junit.Before;
import org.junit.Test;

import javax.websocket.Session;

import java.util.HashMap;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ChatUtilsTest {

    private ChatUtils chatUtils;

    HashMap<String, ChatUser> users;

    @Before
    public void setUp() throws Exception {
        chatUtils = mock(ChatUtils.class);
        users = mock(HashMap.class);

    }

    @Test
    public void registreChatUser() throws Exception {
        ChatUser chatUser = mock(ChatUser.class);
        Session session = null;
        when(chatUtils.registreChatUser(anyString(), anyString(), eq(session))).thenReturn(chatUser);
        assertEquals(chatUser, chatUtils.registreChatUser(anyString(), anyString(), eq(session)));
    }

    @Test
    public void tryAssignAgent() throws Exception {
       /* ChatUser chatUser = mock(ChatUser.class);
        when(chatUtils.tryAssignAgent(users,chatUser)).thenReturn(true);
        assertEquals(true,chatUtils.tryAssignAgent(users,chatUser));*/
    }

    @Test
    public void disconnectUsers() throws Exception {

        /*ChatUser chatUser = mock(ChatUser.class);
        Session session = mock(Session.class);
        chatUser.setUserToSession(session);
        new ChatUtils(users).disconnectUsers(chatUser);
        verify(chatUser).setUserToSession(null);
        verify(chatUser).getUserToSession();*/
    }

    @Test
    public void sendMessage() throws Exception {

        ChatUser chatUser = mock(ChatUser.class);
        Message message = mock(Message.class);
        Session session = mock(Session.class);
        chatUser.setSession(session);
        chatUtils.sendMessage(message,chatUser);
        verify(chatUtils).sendMessage(message,chatUser);
    }
}