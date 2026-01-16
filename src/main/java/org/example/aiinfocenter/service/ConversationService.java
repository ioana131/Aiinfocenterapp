package org.example.aiinfocenter.service;

import org.example.aiinfocenter.model.ChatMessage;
import org.example.aiinfocenter.model.ConversationThread;
import org.example.aiinfocenter.model.User;
import org.example.aiinfocenter.model.UserRole;
import org.example.aiinfocenter.repo.ChatMessageRepository;
import org.example.aiinfocenter.repo.ConversationThreadRepository;
import org.example.aiinfocenter.repo.UserRepository;
import org.example.aiinfocenter.util.InputValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ConversationService {

    private final ConversationThreadRepository threads;
    private final ChatMessageRepository messages;
    private final UserRepository users;
    private final MessageService messageService; // n8n ai
    private final InputValidation validation;

    public ConversationService(ConversationThreadRepository threads,
                               ChatMessageRepository messages,
                               UserRepository users,
                               MessageService messageService,
                               InputValidation validation) {
        this.threads = threads;
        this.messages = messages;
        this.users = users;
        this.messageService = messageService;
        this.validation = validation;
    }

    private User requireStudent(Long studentId) {
        User u = users.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("student not found"));
        if (u.getRole() != UserRole.STUDENT) {
            throw new IllegalArgumentException("only STUDENT can use conversations");
        }
        return u;
    }

    private ConversationThread requireThreadOfStudent(Long studentId, Long threadId) {
        ConversationThread t = threads.findById(threadId)
                .orElseThrow(() -> new IllegalArgumentException("conversation not found"));
        if (!t.getStudent().getId().equals(studentId)) {
            throw new IllegalArgumentException("not your conversation");
        }
        return t;
    }

    // ---------------- CONVERSATIONS ----------------

    @Transactional
    public ConversationThread createConversation(Long studentId, String title) {
        User student = requireStudent(studentId);

        // instead of manual if
        validation.validateTitle(title);

        ConversationThread t = new ConversationThread(student, title.trim());
        return threads.save(t);
    }

    public List<ConversationThread> listConversations(Long studentId) {
        requireStudent(studentId);
        return threads.findByStudentIdOrderByCreatedAtDesc(studentId);
    }

    @Transactional
    public void deleteConversation(Long studentId, Long threadId) {
        requireStudent(studentId);
        requireThreadOfStudent(studentId, threadId);

        messages.deleteByThreadId(threadId);
        threads.deleteById(threadId);
    }

    public List<ChatMessage> listMessages(Long studentId, Long threadId) {
        requireStudent(studentId);
        requireThreadOfStudent(studentId, threadId);
        return messages.findByThreadIdOrderByCreatedAtAsc(threadId);
    }

    // ---------------- MESSAGES ----------------

    @Transactional
    public ChatMessage sendStudentMessage(Long studentId, Long threadId, String text) {
        requireStudent(studentId);
        ConversationThread t = requireThreadOfStudent(studentId, threadId);

        // instead of manual if
        validation.validateMessage(text);

        // 1) save student message
        ChatMessage m1 = new ChatMessage(t, ChatMessage.Sender.STUDENT, text.trim());
        messages.save(m1);

        // 2) call n8n
        String aiText;
        try {
            aiText = messageService.askAiText(text.trim());
        } catch (Exception ex) {
            aiText = "AI error: " + ex.getMessage();
        }

        // 3) save ai message
        ChatMessage m2 = new ChatMessage(t, ChatMessage.Sender.AI, aiText);
        messages.save(m2);

        return m2;
    }
}
