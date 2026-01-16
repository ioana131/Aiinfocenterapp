package org.example.aiinfocenter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.aiinfocenter.repo.*;
import org.example.aiinfocenter.service.*;
import org.example.aiinfocenter.util.InputValidation;
import org.example.aiinfocenter.util.ValidationUtil;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ServiceConstructorTests {

    private Object getField(Object obj, String name) {
        try {
            Field f = obj.getClass().getDeclaredField(name);
            f.setAccessible(true);
            return f.get(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void adminService_constructor() {
        UserRepository repo = mock(UserRepository.class);
        AdminService svc = new AdminService(repo);

        assertSame(repo, getField(svc, "users"));
    }

    @Test
    void authService_constructor() {
        AuthService svc = new AuthService(
                mock(UserRepository.class),
                mock(StudentProfileRepository.class),
                new InputValidation()
        );

        assertNotNull(svc);
    }

    @Test
    void requestService_constructor() {
        RequestService svc = new RequestService(
                mock(RequestRepository.class),
                mock(UserRepository.class),
                new InputValidation()
        );

        assertNotNull(svc);
    }

    @Test
    void conversationService_constructor() {
        ConversationService svc = new ConversationService(
                mock(ConversationThreadRepository.class),
                mock(ChatMessageRepository.class),
                mock(UserRepository.class),
                mock(MessageService.class),
                mock(InputValidation.class)
        );

        assertNotNull(svc);
    }

    @Test
    void messageService_constructor() {
        MessageService svc = new MessageService(
                mock(MessageLogRepository.class),
                new ObjectMapper()
        );

        assertNotNull(svc);
    }
}
