package clientWeb.MessageUtils;

public class MessageTypeAnalyzer {

    public static MessageTypes typeOfMessage(String message) {
        switch (message) {
            case "/leave":
                return MessageTypes.LEAVE_MESSAGE;

            default:
                return MessageTypes.ORDINARY_MESSAGE;

        }
    }
}
