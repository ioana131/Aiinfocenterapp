package org.example.aiinfocenter;

import org.example.aiinfocenter.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class UserConstructorTest {

    @Test
    void user_noargs_constructor() {
        User u = new User();
        assertNotNull(u);

        assertNull(u.getId());
        assertNull(u.getName());
        assertNull(u.getEmail());
        assertNull(u.getPassword());
        assertNull(u.getRole());
    }

    @Test
    void user_args_constructor() {
        User u = new User("Ana", "ana@mail.com", "1234", UserRole.STUDENT);

        assertEquals("Ana", u.getName());
        assertEquals("ana@mail.com", u.getEmail());
        assertEquals("1234", u.getPassword());
        assertEquals(UserRole.STUDENT, u.getRole());
    }
}
