package com.rishika.campuscart.model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "product")
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private double price;

    @ElementCollection
    private List<String> imageUrls;

//    @Column(name = "image_url")
//    private String imageUrl;

    private String location;

    @Column(name = "seller_id")
    private Long sellerId;

    private String category;

    @Column(name = "created_at")
    private String createdAt;
}