package clientWeb.ConsoleClient;

import com.google.gson.JsonObject;
import org.java_websocket.client.WebSocketClient;
import org.json.JSONObject;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Scanner;

public class ConsoleClientUtil {

    public WebSocketClient registerClient() {
        WebSocketClient client = null;
        String role = null;
        System.out.println("What's your name?");
        Scanner scanner = new Scanner(System.in);
        String user = scanner.nextLine();
        System.out.println("What's your role(client or agent)?");
        while (!"agent".equals(role) && !"client".equals(role)) {
            role = scanner.nextLine();
            System.out.println("only client or agent");
        }
        try {
            client = new ConsoleClientEndPoint(new URI("ws://localhost:8080/" + role + "/chat/" + user + "/"));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        client.connect();
        return client;
    }

    public void messageSending(WebSocketClient client) {
        String message;

        Scanner scanner = new Scanner(System.in);
        while (true) {
            message = scanner.nextLine();
            if (message.equalsIgnoreCase("/exit")) {
                client.close();
                break;
            }

            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("content", message);
            client.send(jsonObject.toString());
        }
    }

    public String jsonParser(String message) {
        String msg;
        JSONObject jsonObject = new JSONObject(message);
        msg = jsonObject.getString("from") + ":" + jsonObject.getString("content");
        return msg;

    }
}
