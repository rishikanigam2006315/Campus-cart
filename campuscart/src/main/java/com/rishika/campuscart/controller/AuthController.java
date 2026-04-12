package com.rishika.campuscart.controller;

import com.rishika.campuscart.model.User;
import com.rishika.campuscart.repository.UserRepository;
import com.rishika.campuscart.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;

//    @PostMapping("/signup")
//    public User signup(@RequestBody User user) {
//        return userRepository.save(user);
//    }

    @PostMapping("/signup")
    public User signup(@RequestBody User user) {

        if(userRepository.findByEmail(user.getEmail()).isPresent()){
            throw new RuntimeException("Email already registered");
        }

        user.setEnabled(true);
        user.setRole("ROLE_USER");

        return userRepository.save(user);
    }

//    @PostMapping("/login")
//    public String login(@RequestBody User user) {
//
//        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
//
//        if (existingUser.isEmpty()) {
//            throw new RuntimeException("User not found");
//        }
//
//        User existing = existingUser.get();
//
//        if (!existing.getPassword().equals(user.getPassword())) {
//            throw new RuntimeException("Invalid password");
//        }
//
//        return jwtUtil.generateToken(existing.getEmail());
//    }

    @PostMapping("/login")
    public String login(@RequestBody User user) {

        User existing = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (existing.getPassword() == null || !existing.getPassword().equals(user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        return jwtUtil.generateToken(existing.getEmail());
    }
}