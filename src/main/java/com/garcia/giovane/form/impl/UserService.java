package com.garcia.giovane.form.impl;

import com.garcia.giovane.form.impl.model.UserModel;
import com.garcia.giovane.form.impl.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserModel save(UserModel user) {
        return userRepository.save(user);
    }
}
