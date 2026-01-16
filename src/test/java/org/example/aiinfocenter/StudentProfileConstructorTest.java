package org.example.aiinfocenter;

import org.example.aiinfocenter.model.*;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StudentProfileConstructorTest {

    @Test
    void studentProfile_noargs_constructor() {
        StudentProfile sp = new StudentProfile();
        assertNotNull(sp);

        assertNull(sp.getUser());
        assertNull(sp.getFaculty());
        assertEquals(0, sp.getYearOfStudy());
        assertNull(sp.getUserId());
    }

    @Test
    void studentProfile_args_constructor() {
        User u = new User("Ana", "ana@mail.com", "1234", UserRole.STUDENT);
        StudentProfile sp = new StudentProfile(u, "CS", 2);

        assertEquals(u, sp.getUser());
        assertEquals("CS", sp.getFaculty());
        assertEquals(2, sp.getYearOfStudy());
    }
}
