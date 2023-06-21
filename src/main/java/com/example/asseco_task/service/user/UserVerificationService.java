package com.example.asseco_task.service.user;

import com.example.asseco_task.entity.User;

import java.util.Optional;

public interface UserVerificationService {
    Optional<String> verifyUser(User userExisting);
}
