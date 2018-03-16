package clientWeb;

import clientWeb.ConsoleClient.ConsoleClientUtil;
import com.google.gson.JsonObject;
import org.java_websocket.client.WebSocketClient;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class ConsoleClientUtilTest {

    private ConsoleClientUtil consoleClientUtil;

    @Before
    public void setUp() throws Exception {
        consoleClientUtil = mock(ConsoleClientUtil.class);
    }

    @Test
    public void registerClient() throws Exception {

        WebSocketClient webSocketClient = mock(WebSocketClient.class);
        when(consoleClientUtil.registerClient()).thenReturn(webSocketClient);
        WebSocketClient webSocketClient1 = consoleClientUtil.registerClient();
        assertEquals(webSocketClient, webSocketClient1);

    }

    @Test
    public void messageSending() throws Exception {
        WebSocketClient webSocketClient = mock(WebSocketClient.class);
        consoleClientUtil.messageSending(webSocketClient);
        verify(webSocketClient, never()).close();
    }

    @Test
    public void jsonParser() throws Exception {
        //consoleClientUtil = new ConsoleClientUtil();
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("from", "from");
        jsonObject.addProperty("content", "content");
        String resultString = "from:content";
        when(consoleClientUtil.jsonParser(jsonObject.toString())).thenReturn(resultString);
        assertEquals(consoleClientUtil.jsonParser(jsonObject.toString()), resultString);
    }

}