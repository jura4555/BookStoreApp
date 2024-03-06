package com.intent.BookStore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.intent.BookStore.dto.validation.group.OnCreate;
import com.intent.BookStore.dto.validation.group.OnUpdate;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Accessors(chain = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;

    @NotBlank(message = "Username is required")
    private String username;

    @NotBlank(message = "Password is required", groups = OnCreate.class)
    @Size(min = 8, max = 20, message = "Password must be between 8 and 20 characters", groups = OnCreate.class)
    @Null(message = "Password must be null", groups = OnUpdate.class)
    private String password;

    @Email(message = "Invalid email format")
    private String email;

    @Pattern(regexp = "\\+380\\d{9}", message = "Phone number must be in the format +380XXXXXXXXX")
    private String phoneNumber;

    private Set<OrderDTO> orders;
}
