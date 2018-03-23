package clientWeb.ChatUserUtils;

public class TypeUserAnalyzer {

    public static ChatUserTypes typeOfUser(String role) {

        switch (role) {
            case "consoleAgent":
                return ChatUserTypes.CONSOLE_AGENT;
            case "webagent":
                return ChatUserTypes.WEB_AGENT;
            default:
                return ChatUserTypes.CLIENT;
        }
    }
}
