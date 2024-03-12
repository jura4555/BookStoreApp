package com.intent.BookStore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OrderDTO orderDTO = (OrderDTO) o;

        if (quantity != orderDTO.quantity) return false;
        if (closed != orderDTO.closed) return false;
        if (!id.equals(orderDTO.id)) return false;
        if (!userId.equals(orderDTO.userId)) return false;
        if (!totalPrice.equals(orderDTO.totalPrice)) return false;
        return completedAt != null ? completedAt.equals(orderDTO.completedAt) : orderDTO.completedAt == null;
    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + userId.hashCode();
        result = 31 * result + quantity;
        result = 31 * result + totalPrice.hashCode();
        result = 31 * result + (completedAt != null ? completedAt.hashCode() : 0);
        result = 31 * result + (closed ? 1 : 0);
        return result;
    }
}
