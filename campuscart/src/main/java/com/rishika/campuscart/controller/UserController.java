package com.rishika.campuscart.controller;

import com.rishika.campuscart.model.Rating;
import com.rishika.campuscart.model.User;
import com.rishika.campuscart.repository.RatingRepository;
import com.rishika.campuscart.repository.UserRepository;
import com.rishika.campuscart.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin
public class UserController {

    private final UserRepository userRepository;
    private final RatingRepository ratingRepository;
    private final UserService userService;

    @GetMapping("/profile/{id}")
    public Map<String, Object> getUserProfile(@PathVariable Long id){

        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"
                ));

        List<Rating> ratings = ratingRepository.findBySellerId(id);

        double avgRating = ratings.stream()
                .mapToInt(Rating::getScore)
                .average()
                .orElse(0.0);

        return Map.of(
                "id", user.getId(),
                "name", user.getName(),
                "email", user.getEmail(),
                "college", user.getCollege(),
                "averageRating", avgRating,
                "profileImageUrl", user.getProfileImageUrl() != null ? user.getProfileImageUrl() : ""
        );
    }

    @PostMapping("/profile-image")
    public Map<String, String> saveProfileImage(
            @RequestParam Long userId,
            @RequestBody Map<String, String> body){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"
                ));

        String imageUrl = body.get("imageUrl");

        if (imageUrl == null || imageUrl.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Image URL required");
        }

        user.setProfileImageUrl(imageUrl);
        userRepository.save(user);

        return Map.of("message", "Profile image updated");
    }

    @PostMapping("/fcm-token")
    public void saveFcmToken(@RequestParam Long userId,
                             @RequestBody Map<String, String> body){

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"
                ));

        user.setFcmToken(body.get("token"));
        userRepository.save(user);
    }

    @PostMapping("/change-password")
    public Map<String, String> changePassword(
            @RequestBody Map<String, String> body){

        Long userId = Long.parseLong(body.get("userId"));

        String currentPassword = body.get("currentPassword");

        String newPassword = body.get("newPassword");

        String message = userService.changePassword(
                userId,
                currentPassword,
                newPassword
        );

        return Map.of("message", message);
    }

    @DeleteMapping("/delete-account/{id}")
    public Map<String, String> deleteAccount(
            @PathVariable Long id){

        String message = userService.deleteAccount(id);

        return Map.of("message", message);
    }
}