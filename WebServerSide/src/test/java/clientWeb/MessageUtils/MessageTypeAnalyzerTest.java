package clientWeb.MessageUtils;

import org.junit.Test;

import static org.junit.Assert.*;

public class MessageTypeAnalyzerTest {

    @Test
    public void typeOfMessage() {
        assertEquals(MessageTypeAnalyzer.typeOfMessage("/leave"),MessageTypes.LEAVE_MESSAGE);
        assertEquals(MessageTypeAnalyzer.typeOfMessage("anystring"),MessageTypes.ORDINARY_MESSAGE);
    }
}