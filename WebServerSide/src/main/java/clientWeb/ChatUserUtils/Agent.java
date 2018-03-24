package clientWeb.ChatUserUtils;

import javax.websocket.Session;
import java.util.HashMap;

public class Agent extends ChatUser {

    private int numberOfSlots;

    private int freeSlots;

    private HashMap<String, Session> interlocutors;

    public Agent() {
        interlocutors = new HashMap<>();
    }

    public HashMap<String, Session> getInterlocutors() {

        return interlocutors;
    }

    public void setInterlocutors(HashMap<String, Session> interlocutors) {
        this.interlocutors = interlocutors;
    }

    public int getNumberOfSlots() {
        return numberOfSlots;
    }

    public void setNumberOfSlots(int numberOfSlots) {
        this.numberOfSlots = numberOfSlots;
    }

    public int getFreeSlots() {
        return freeSlots;
    }

    public void setFreeSlots(int freeSlots) {
        this.freeSlots = freeSlots;
    }
}
