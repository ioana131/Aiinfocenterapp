package org.example.aiinfocenter;

import org.example.aiinfocenter.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class RequestConstructorTest {

    @Test
    void request_noargs_constructor() {
        Request r = new Request();
        assertNotNull(r);

        assertNull(r.getStudent());
        assertNull(r.getType());
        assertNull(r.getMessage());
        assertEquals(Request.Status.OPEN, r.getStatus());
        assertNotNull(r.getCreatedAt());
    }

    @Test
    void request_args_constructor() {
        User u = new User("Ana", "ana@mail.com", "1234", UserRole.STUDENT);
        Request r = new Request(u, "TYPE", "msg");

        assertEquals(u, r.getStudent());
        assertEquals("TYPE", r.getType());
        assertEquals("msg", r.getMessage());
        assertEquals(Request.Status.OPEN, r.getStatus());
    }
}
