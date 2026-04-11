package com.rishika.campuscart.repository;

import com.rishika.campuscart.model.Wishlist;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;

import java.util.List;

public interface WishlistRepository extends JpaRepository<Wishlist, Long> {

    List<Wishlist> findByUserId(Long userId);

    @Transactional
    @Modifying
    void deleteByUserIdAndProductId(Long userId, Long productId);
}