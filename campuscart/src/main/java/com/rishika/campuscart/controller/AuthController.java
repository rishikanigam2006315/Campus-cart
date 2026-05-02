package com.rishika.campuscart.controller;

import com.rishika.campuscart.model.User;
import com.rishika.campuscart.repository.UserRepository;
import com.rishika.campuscart.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@CrossOrigin
public class AuthController {

    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final PasswordEncoder passwordEncoder;

    // 🔥 SIGNUP
    @PostMapping("/signup")
    public User signup(@RequestBody User user) {

        // Email already exists check
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "Email already registered"
            );
        }

        user.setEnabled(true);
        user.setRole("ROLE_USER");

        // 🔐 Password encode (VERY IMPORTANT)
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);

        // 🔒 Hide password in response
        savedUser.setPassword(null);

        return savedUser;
    }

    // 🔥 LOGIN
    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody User user) {

        User existing = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED, "User not found"
                ));

        // 🔐 Check encoded password
        if (!passwordEncoder.matches(user.getPassword(), existing.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED, "Invalid password"
            );
        }

        // 🔥 Generate JWT token
        String token = jwtUtil.generateToken(existing.getEmail());

        // 🔥 IMPORTANT: Send userId also
        return Map.of(
                "token", token,
                "userId", existing.getId()
        );
    }
}