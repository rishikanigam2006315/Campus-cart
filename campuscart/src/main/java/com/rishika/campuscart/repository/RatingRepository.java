package com.rishika.campuscart.repository;

import com.rishika.campuscart.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    List<Rating> findBySellerId(Long sellerId);
}
