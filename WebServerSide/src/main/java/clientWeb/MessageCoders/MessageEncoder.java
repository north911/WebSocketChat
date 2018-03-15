package clientWeb.MessageCoders;

import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

import clientWeb.MessageUtils.Message;
import com.google.gson.Gson;

public class MessageEncoder implements Encoder.Text<Message> {

    private static Gson gson = new Gson();


    public String encode(Message message) throws EncodeException {
        String json = gson.toJson(message);
        return json;
    }


    public void init(EndpointConfig endpointConfig) {
        // Custom initialization logic
    }


    public void destroy() {
        // Close resources
    }
}