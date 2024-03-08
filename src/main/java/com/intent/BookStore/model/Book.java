package com.intent.BookStore.model;

import lombok.*;

import jakarta.persistence.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@Entity
@Table(name = "Books")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String title;

    @Column(name = "author_name", nullable = false)
    private String authorName;

    @Column(nullable = false)
    private String genre;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    private int quantity;

    @Column(nullable = false)
    private String description;

    @Column(name = "image_url")
    private String imageURL;
}
