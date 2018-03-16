package clientWeb.MessageCoders;

import clientWeb.MessageUtils.Message;
import com.google.gson.JsonObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MessageDecoderTest {

    MessageDecoder messageDecoder;

    @Before
    public void setUp() throws Exception {
       messageDecoder = mock(MessageDecoder.class);
    }

    @Test
    public void decode() throws Exception {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("from","from");
        jsonObject.addProperty("content","content");
        MessageDecoder messageDecoder = new MessageDecoder();
        Message message = new Message();
        message.setFrom("from");
        message.setContent("content");
        assertEquals(message,messageDecoder.decode(jsonObject.toString()));

    }

    @Test
    public void willDecode() throws Exception {
        when(messageDecoder.willDecode(anyString())).thenReturn(true);
        assertEquals(true,messageDecoder.willDecode(anyString()));
    }

    @Test
    public void init() throws Exception {
    }

    @Test
    public void destroy() throws Exception {
    }

}