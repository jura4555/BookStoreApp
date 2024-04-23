package com.intent.BookStore.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public class LoginDTO {
    @NotBlank(message = "Current username is required")
    private String username;
    @NotBlank(message = "Current password is required")
    private String password;
}
