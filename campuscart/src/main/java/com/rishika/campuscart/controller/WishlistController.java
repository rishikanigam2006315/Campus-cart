package com.rishika.campuscart.controller;

import com.rishika.campuscart.model.Wishlist;
import com.rishika.campuscart.repository.WishlistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
@RequiredArgsConstructor
@CrossOrigin
public class WishlistController {

    private final WishlistRepository wishlistRepository;

    // Add to wishlist
    @PostMapping
    public Wishlist addToWishlist(@RequestBody Wishlist wishlist){
        return wishlistRepository.save(wishlist);
    }

    // Get wishlist by user
    @GetMapping("/{userId}")
    public List<Wishlist> getWishlist(@PathVariable Long userId){
        return wishlistRepository.findByUserId(userId);
    }

    // Remove from wishlist
    @DeleteMapping
    public void removeFromWishlist(@RequestParam Long userId,
                                   @RequestParam Long productId){
        wishlistRepository.deleteByUserIdAndProductId(userId, productId);
    }
}