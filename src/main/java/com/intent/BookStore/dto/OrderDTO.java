package com.intent.BookStore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.intent.BookStore.model.User;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {
    private Long id;

    @Valid
    private UserDTO userDTO;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity must be positive or zero")
    private int quantity;

    @NotNull(message = "Quantity is required")
    @PositiveOrZero(message = "Quantity must be positive or zero")
    private BigDecimal totalPrice;

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "The date must be in the format yyyy-MM-dd")
    @NotNull(message = "Completed date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate completedAt;
}
