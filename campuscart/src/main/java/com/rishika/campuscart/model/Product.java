package com.rishika.campuscart.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private double price;

    private String imageUrl;

    private String location;

    private Long sellerId;

    private String category;

    private String createdAt;
}