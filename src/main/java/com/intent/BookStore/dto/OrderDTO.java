package com.intent.BookStore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private Long id;

    @NotNull(message = "userId is required")
    @Positive(message = "userId must be positive or zero")
    private Long userId;

    private int quantity;

    private BigDecimal totalPrice;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "The date must be in the format yyyy-MM-dd")
    @NotNull(message = "Completed date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate completedAt;

    private boolean closed;

    private Set<OrderItemDTO> orderItemDTOs;
}
