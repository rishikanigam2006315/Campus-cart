package com.rishika.campuscart.controller;

import com.rishika.campuscart.model.Rating;
import com.rishika.campuscart.repository.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@CrossOrigin
public class RatingController {

    private final RatingRepository ratingRepository;

    @PostMapping("/{id}/rate")
    public void rate(@PathVariable Long id,
                     @RequestBody Map<String, Integer> body) {

        Rating r = new Rating();
        r.setSellerId(id);
        r.setScore(body.get("score"));

        ratingRepository.save(r);
    }
}
