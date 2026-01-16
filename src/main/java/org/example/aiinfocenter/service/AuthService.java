package org.example.aiinfocenter.service;

import org.example.aiinfocenter.dto.LoginRequest;
import org.example.aiinfocenter.dto.RegisterRequest;
import org.example.aiinfocenter.model.StudentProfile;
import org.example.aiinfocenter.model.User;
import org.example.aiinfocenter.model.UserRole;
import org.example.aiinfocenter.repo.StudentProfileRepository;
import org.example.aiinfocenter.repo.UserRepository;
import org.example.aiinfocenter.util.InputValidation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final UserRepository users;
    private final StudentProfileRepository profiles;
    private final InputValidation validation;

    public AuthService(UserRepository users, StudentProfileRepository profiles, InputValidation validation) {
        this.users = users;
        this.profiles = profiles;
        this.validation = validation;
    }

    @Transactional
    public User register(RegisterRequest dto) {
        // role validation + parse
        UserRole role = validation.parseRole(dto.role);

        // basic checks
        validation.requireNotBlank(dto.name, "name");
        validation.requireNotBlank(dto.email, "email");
        validation.validatePassword(dto.password);

        if (users.findByEmail(dto.email.trim()).isPresent()) {
            throw new IllegalArgumentException("email already exists");
        }

        User user = users.save(new User(
                dto.name.trim(),
                dto.email.trim(),
                dto.password,
                role
        ));

        if (role == UserRole.STUDENT) {
            validation.validateStudentFields(dto.faculty, dto.yearOfStudy);
            profiles.save(new StudentProfile(user, dto.faculty.trim(), dto.yearOfStudy));
        }

        return user;
    }

    public User login(LoginRequest dto) {
        validation.requireNotBlank(dto.email, "email");
        validation.requireNotBlank(dto.password, "password");

        User u = users.findByEmail(dto.email.trim())
                .orElseThrow(() -> new IllegalArgumentException("The account doesn't exist"));

        if (!u.getPassword().equals(dto.password)) {
            throw new IllegalArgumentException("Incorrect password");
        }

        return u;
    }
}
