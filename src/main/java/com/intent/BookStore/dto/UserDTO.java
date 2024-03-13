package com.intent.BookStore.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.intent.BookStore.dto.validation.group.OnCreate;
import com.intent.BookStore.dto.validation.group.OnUpdate;
import jakarta.validation.constraints.*;
import lombok.*;
import lombok.experimental.Accessors;

import java.math.BigDecimal;
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

    @Null(message = "Account balance must be null")
    private BigDecimal accountBalance;

    private Set<OrderDTO> orders;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserDTO userDTO = (UserDTO) o;

        if (!username.equals(userDTO.username)) return false;
        if (!email.equals(userDTO.email)) return false;
        return phoneNumber.equals(userDTO.phoneNumber);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + username.hashCode();
        result = 31 * result + (password != null ? password.hashCode() : 0);
        result = 31 * result + email.hashCode();
        result = 31 * result + phoneNumber.hashCode();
        result = 31 * result + (accountBalance != null ? accountBalance.hashCode() : 0);
        return result;
    }
}
