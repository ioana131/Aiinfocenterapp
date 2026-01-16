package org.example.aiinfocenter;

import org.example.aiinfocenter.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ConversationThreadConstructorTest {

    @Test
    void conversationThread_noargs_constructor() {
        ConversationThread t = new ConversationThread();
        assertNotNull(t);

        assertNull(t.getStudent());
        assertNull(t.getTitle());
        assertNotNull(t.getCreatedAt());
    }

    @Test
    void conversationThread_args_constructor() {
        User u = new User("Ana", "ana@mail.com", "1234", UserRole.STUDENT);
        ConversationThread t = new ConversationThread(u, "title");

        assertEquals(u, t.getStudent());
        assertEquals("title", t.getTitle());
    }
}
