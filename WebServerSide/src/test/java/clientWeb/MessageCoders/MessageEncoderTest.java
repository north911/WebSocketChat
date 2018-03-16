package clientWeb.MessageCoders;

import clientWeb.MessageUtils.Message;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;

public class MessageEncoderTest {

    MessageEncoder messageEncoder;

    @Before
    public void setUp() throws Exception {
        messageEncoder = mock(MessageEncoder.class);
    }

    @Test
    public void encode() throws Exception {
        messageEncoder = new MessageEncoder();
        Message message = new Message();
        message.setFrom("from");
        message.setContent("content");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("from", "from");
        jsonObject.put("content","content");
        assertEquals(messageEncoder.encode(message), jsonObject.toString());
    }

    @Test
    public void init() throws Exception {
    }

    @Test
    public void destroy() throws Exception {
    }

}