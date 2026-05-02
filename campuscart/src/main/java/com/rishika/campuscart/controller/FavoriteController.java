package com.rishika.campuscart.controller;

import com.rishika.campuscart.model.Favorite;
import com.rishika.campuscart.model.Product;
import com.rishika.campuscart.repository.FavoriteRepository;
import com.rishika.campuscart.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorites")
@RequiredArgsConstructor
public class FavoriteController {

    private final FavoriteRepository favoriteRepository;
    private final ProductService productService;

    @PostMapping("/{productId}")
    public void addFavorite(@PathVariable Long productId, @RequestParam Long userId) {

        if (favoriteRepository.findByUserIdAndProductId(userId, productId).isEmpty()) {
            Favorite fav = new Favorite();
            fav.setUserId(userId);
            fav.setProductId(productId);
            favoriteRepository.save(fav);
        }
    }

    @DeleteMapping("/{productId}")
    public void removeFavorite(@PathVariable Long productId, @RequestParam Long userId) {
        favoriteRepository.deleteByUserIdAndProductId(userId, productId);
    }

    @GetMapping
    public List<Product> getFavorites(@RequestParam Long userId) {
        List<Favorite> favs = favoriteRepository.findByUserId(userId);

        return favs.stream()
                .map(f -> productService.getProductById(f.getProductId()))
                .toList();
    }
}
