package com.intent.BookStore.model;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "Orders")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = {
            CascadeType.REFRESH,
            CascadeType.PERSIST,
            CascadeType.MERGE,
            CascadeType.DETACH
    })
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "completed_at")
    private LocalDate completedAt;

    @Column(nullable = false)
    private boolean closed;

    @OneToMany(mappedBy = "order", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<OrderItem> orderItems;
}
