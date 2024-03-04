package com.intent.BookStore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderItemDTO {
    private Long id;

    @NotNull(message = "orderId is required")
    @Positive(message = "orderId must be positive or zero")
    private Long orderId;

    @NotNull(message = "bookId is required")
    @Positive(message = "bookId must be positive or zero")
    private Long bookId;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity must be positive or zero")
    private int quantity;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity must be positive or zero")
    private BigDecimal totalPrice;
}
