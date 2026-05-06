//package com.rishika.campuscart.service;
//
//import com.rishika.campuscart.model.User;
//import com.rishika.campuscart.repository.UserRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//@RequiredArgsConstructor
//public class UserService {
//
//    private final UserRepository userRepository;
//
//    public User signup(User user){
//        return userRepository.save(user);
//    }
//
//    public Optional<User> login(String email){
//        return userRepository.findByEmail(email);
//    }
//}

package com.rishika.campuscart.service;

import com.rishika.campuscart.model.User;
import com.rishika.campuscart.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public User signup(User user){

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        return userRepository.save(user);
    }

    public Optional<User> login(String email){
        return userRepository.findByEmail(email);
    }

    // CHANGE PASSWORD
    public String changePassword(Long userId,
                                 String currentPassword,
                                 String newPassword){

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        // old password verify
        if(!passwordEncoder.matches(currentPassword, user.getPassword())){
            throw new RuntimeException("Current password incorrect");
        }

        // new password encode
        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        return "Password updated successfully";
    }

    // DELETE ACCOUNT
    public String deleteAccount(Long userId){

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new RuntimeException("User not found"));

        userRepository.delete(user);

        return "Account deleted successfully";
    }
}