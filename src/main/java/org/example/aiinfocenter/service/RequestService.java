package org.example.aiinfocenter.service;

import org.example.aiinfocenter.model.Request;
import org.example.aiinfocenter.model.User;
import org.example.aiinfocenter.model.UserRole;
import org.example.aiinfocenter.repo.RequestRepository;
import org.example.aiinfocenter.repo.UserRepository;
import org.example.aiinfocenter.util.InputValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RequestService {

    private final RequestRepository requests;
    private final UserRepository users;
    private final InputValidation validation;

    public RequestService(RequestRepository requests, UserRepository users, InputValidation validation) {
        this.requests = requests;
        this.users = users;
        this.validation = validation;
    }

    // ================= INTERNAL HELPERS =================

    private User requireStudent(Long studentId) {
        User u = users.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("student not found"));

        if (u.getRole() != UserRole.STUDENT) {
            throw new IllegalArgumentException("only STUDENT can submit requests");
        }
        return u;
    }

    // ================= STUDENT =================

    @Transactional
    public Request create(Long studentId, String message) {
        // centralized validation (instead of manual if)
        validation.validateMessage(message);

        User student = requireStudent(studentId);

        Request r = new Request();
        r.setStudent(student);
        r.setType("GENERAL");
        r.setMessage(message.trim());
        r.setStatus(Request.Status.OPEN);

        return requests.save(r);
    }

    public List<Request> listForStudent(Long studentId) {
        requireStudent(studentId);
        return requests.findByStudentIdOrderByIdDesc(studentId);
    }

    // ================= ADMIN =================

    public List<Request> allRequestsAdmin() {
        return requests.findAllByOrderByIdDesc();
    }

    public List<Request> requestsForStudentAdmin(Long studentId) {
        User u = users.findById(studentId)
                .orElseThrow(() -> new IllegalArgumentException("student not found"));

        if (u.getRole() != UserRole.STUDENT) {
            throw new IllegalArgumentException("only STUDENT can have requests");
        }

        return requests.findByStudentIdOrderByIdDesc(studentId);
    }

    @Transactional
    public Request respond(Long requestId, String response, Request.Status status) {
        Request r = requests.findById(requestId)
                .orElseThrow(() -> new IllegalArgumentException("request not found"));



        r.setAdminResponse(response);
        r.setStatus(status);
        return requests.save(r);
    }

    // ================= SHARED (UPLOAD / DOWNLOAD) =================

    public Request getById(Long id) {
        return requests.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("request not found"));
    }

    @Transactional
    public Request save(Request r) {
        return requests.save(r);
    }
}
