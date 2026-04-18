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

    @PostMapping
    public Wishlist addToWishlist(@RequestBody Wishlist wishlist){
        return wishlistRepository.save(wishlist);
    }

    @GetMapping("/{userId}")
    public List<Wishlist> getWishlist(@PathVariable Long userId){
        return wishlistRepository.findByUserId(userId);
    }

    @DeleteMapping("/{id}")
    public String remove(@PathVariable Long id){
        wishlistRepository.deleteById(id);
        return "Removed from wishlist";
    }
}