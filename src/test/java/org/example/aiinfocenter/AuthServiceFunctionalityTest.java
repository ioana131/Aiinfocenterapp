package org.example.aiinfocenter;

import org.example.aiinfocenter.dto.*;
import org.example.aiinfocenter.model.*;
import org.example.aiinfocenter.repo.*;
import org.example.aiinfocenter.service.AuthService;
import org.example.aiinfocenter.util.ValidationUtil;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceFunctionalityTest {

    @Test
    void register_duplicate_email_fails() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        StudentProfileRepository profileRepo = Mockito.mock(StudentProfileRepository.class);

        Mockito.when(userRepo.findByEmail("a@mail.com"))
                .thenReturn(Optional.of(new User("x", "a@mail.com", "p", UserRole.STUDENT)));

        AuthService auth = new AuthService(userRepo, profileRepo, new ValidationUtil());

        RegisterRequest dto = new RegisterRequest();
        dto.name = "Ana";
        dto.email = "a@mail.com";
        dto.password = "1234";
        dto.role = "STUDENT";
        dto.faculty = "CS";
        dto.yearOfStudy = 2;

        assertThrows(IllegalArgumentException.class, () -> auth.register(dto));
    }

    @Test
    void login_wrong_password_fails() {
        UserRepository userRepo = Mockito.mock(UserRepository.class);
        StudentProfileRepository profileRepo = Mockito.mock(StudentProfileRepository.class);

        User u = new User("Ana", "ana@mail.com", "GOOD", UserRole.STUDENT);
        Mockito.when(userRepo.findByEmail("ana@mail.com")).thenReturn(Optional.of(u));

        AuthService auth = new AuthService(userRepo, profileRepo, new ValidationUtil());

        LoginRequest dto = new LoginRequest();
        dto.email = "ana@mail.com";
        dto.password = "BAD";

        assertThrows(IllegalArgumentException.class, () -> auth.login(dto));
    }
}
