package com.rishika.campuscart.repository;

import com.rishika.campuscart.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product,Long> {

    List<Product> findByTitleContainingIgnoreCase(String keyword);

    List<Product> findByCategory(String category);

    List<Product> findBySellerId(Long sellerId);

    List<Product> findByPriceBetween(Double minPrice, Double maxPrice);

}