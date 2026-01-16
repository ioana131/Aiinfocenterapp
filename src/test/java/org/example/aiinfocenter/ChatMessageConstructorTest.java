package org.example.aiinfocenter;

import org.example.aiinfocenter.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ChatMessageConstructorTest {

    @Test
    void chatMessage_noargs_constructor() {
        ChatMessage m = new ChatMessage();
        assertNotNull(m);

        assertNull(m.getThread());
        assertNull(m.getSender());
        assertNull(m.getText());
        assertNotNull(m.getCreatedAt());
    }

    @Test
    void chatMessage_args_constructor() {
        User u = new User("Ana", "ana@mail.com", "1234", UserRole.STUDENT);
        ConversationThread t = new ConversationThread(u, "t");

        ChatMessage m = new ChatMessage(t, ChatMessage.Sender.AI, "hello");

        assertEquals(t, m.getThread());
        assertEquals(ChatMessage.Sender.AI, m.getSender());
        assertEquals("hello", m.getText());
    }
}
