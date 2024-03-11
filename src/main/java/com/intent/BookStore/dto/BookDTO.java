package com.intent.BookStore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.intent.BookStore.dto.validation.DescriptionWordCount;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.URL;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@EqualsAndHashCode
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookDTO {
    private Long id;

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Author name is required")
    private String authorName;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be positive or zero")
    private BigDecimal price;

    @NotNull(message = "Price is required")
    @PositiveOrZero(message = "Price must be positive or zero")
    private int quantity;

    @NotBlank(message = "Description is required")
    @DescriptionWordCount(value = 6)
    private String description;

    @URL(message = "Image URL must be a valid URL")
    private String imageURL;
}
