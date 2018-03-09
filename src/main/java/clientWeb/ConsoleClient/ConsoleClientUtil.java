package clientWeb.ConsoleClient;

import com.google.gson.JsonObject;
import org.java_websocket.client.WebSocketClient;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class ConsoleClientUtil {

    public WebSocketClient registerClient(){
        WebSocketClient client = null;
        System.out.println("What's your name?");
        Scanner scanner = new Scanner(System.in);
        String user = scanner.nextLine();
        try {
            client = new EmptyClient(new URI("ws://localhost:8080/chat/"+user));
        }
        catch (URISyntaxException e){
            e.printStackTrace();
        }

        client.connect();
        return client;
    }

    public void messageSending(WebSocketClient client){
        String message;

        // connect to server
        Scanner scanner = new Scanner(System.in);
        do {
            message = scanner.nextLine();
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("content", message);
            client.send(jsonObject.toString());
        } while (!message.equalsIgnoreCase("/quit"));
    }
}
