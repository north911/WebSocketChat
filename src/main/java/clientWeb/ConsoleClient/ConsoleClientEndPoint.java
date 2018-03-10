package clientWeb.ConsoleClient;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.ByteBuffer;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft;
import org.java_websocket.handshake.ServerHandshake;


public class ConsoleClientEndPoint extends WebSocketClient {

    private static ConsoleClientUtil consoleClientUtil = new ConsoleClientUtil();

    public ConsoleClientEndPoint(URI serverUri, Draft draft) {
        super(serverUri, draft);
    }

    public ConsoleClientEndPoint(URI serverURI) {
        super(serverURI);
    }

    @Override
    public void onOpen(ServerHandshake handshakedata) {
        System.out.println("new connection opened");
    }

    @Override
    public void onClose(int code, String reason, boolean remote) {
        System.out.println("closed with exit code " + code + " additional info: " + reason);
    }

    @Override
    public void onMessage(String message) {
        System.out.println(consoleClientUtil.jsonParser(message));
    }


    @Override
    public void onMessage(ByteBuffer message) {
        System.out.println("received ByteBuffer");
    }

    @Override
    public void onError(Exception ex) {
        System.err.println("an error occurred:" + ex);
    }

    public static void main(String[] args) throws URISyntaxException {
        WebSocketClient client = consoleClientUtil.registerClient();
        consoleClientUtil.messageSending(client);
    }
}