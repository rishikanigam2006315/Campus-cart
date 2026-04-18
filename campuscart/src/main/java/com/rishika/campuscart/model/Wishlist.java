package com.rishika.campuscart.model;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Wishlist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;
    private Long productId;
}