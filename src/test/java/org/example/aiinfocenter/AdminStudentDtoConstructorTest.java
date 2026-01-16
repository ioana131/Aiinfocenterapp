package org.example.aiinfocenter;

import org.example.aiinfocenter.dto.AdminStudentDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class AdminStudentDtoConstructorTest {

    @Test
    void adminStudentDto_args_constructor() {
        AdminStudentDto dto = new AdminStudentDto(1L, "Ana", "ana@mail.com");

        assertEquals(1L, dto.id);
        assertEquals("Ana", dto.name);
        assertEquals("ana@mail.com", dto.email);
    }
}
