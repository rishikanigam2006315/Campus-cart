package com.rishika.campuscart.service;

import com.rishika.campuscart.model.User;
import com.rishika.campuscart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User signup(User user){
        return userRepository.save(user);
    }

    public Optional<User> login(String email){
        return userRepository.findByEmail(email);
    }
}