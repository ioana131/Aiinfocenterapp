package org.example.aiinfocenter;

import org.example.aiinfocenter.model.MessageLog;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MessageLogConstructorTest {

    @Test
    void messageLog_args_constructor() {
        MessageLog log = new MessageLog("hi");

        assertEquals("hi", log.getMessage());
        assertNull(log.getResponseJson());
        assertNotNull(log.getCreatedAt());
    }
}
