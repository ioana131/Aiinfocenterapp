package org.example.aiinfocenter;

import org.example.aiinfocenter.model.*;
import org.example.aiinfocenter.repo.*;
import org.example.aiinfocenter.service.RequestService;
import org.example.aiinfocenter.util.InputValidation;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

public class RequestServiceFunctionalityTest {

    @Test
    void create_request_saves_correctly() {
        RequestRepository reqRepo = Mockito.mock(RequestRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        InputValidation validation = new InputValidation(); // ✅ real validation

        User student = new User("Ana", "ana@mail.com", "1234", UserRole.STUDENT);

        Mockito.when(userRepo.findById(1L)).thenReturn(Optional.of(student));
        Mockito.when(reqRepo.save(any(Request.class))).thenAnswer(i -> i.getArgument(0));

        RequestService service = new RequestService(reqRepo, userRepo, validation);

        Request r = service.create(1L, "need paper");

        assertEquals(student, r.getStudent());
        assertEquals("GENERAL", r.getType());
        assertEquals("need paper", r.getMessage());
        assertEquals(Request.Status.OPEN, r.getStatus());
    }

    @Test
    void create_request_fails_if_message_blank() {
        RequestRepository reqRepo = Mockito.mock(RequestRepository.class);
        UserRepository userRepo = Mockito.mock(UserRepository.class);

        InputValidation validation = new InputValidation(); //

        RequestService service = new RequestService(reqRepo, userRepo, validation);

        assertThrows(IllegalArgumentException.class,
                () -> service.create(1L, "   "));
    }
}
