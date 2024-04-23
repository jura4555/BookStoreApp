package com.intent.BookStore.mapper;

import com.intent.BookStore.dto.OrderDTO;
import com.intent.BookStore.dto.UserDTO;
import com.intent.BookStore.model.User;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class UserMapperUtil {
    public static UserDTO toUserDTO(User user) {
        UserDTO userDTO = new UserDTO()
                .setId(user.getId())
                .setUsername(user.getUsername())
                .setPassword(user.getPassword())
                .setEmail(user.getEmail())
                .setPhoneNumber(user.getPhoneNumber())
                .setAccountBalance(user.getAccountBalance())
                .setRole(user.getRole());

        Set<OrderDTO> orderDTOs = new HashSet<>();
        if (user.getOrders() != null) {
            orderDTOs = user.getOrders().stream()
                    .map(OrderMapperUtil::toOrderDTO)
                    .collect(Collectors.toSet());
        }
        userDTO.setOrders(orderDTOs);
        return userDTO;
    }

    public static User toUser(UserDTO userDTO) {
        return new User()
                .setId(userDTO.getId())
                .setUsername(userDTO.getUsername())
                .setPassword(userDTO.getPassword())
                .setEmail(userDTO.getEmail())
                .setPhoneNumber(userDTO.getPhoneNumber())
                .setAccountBalance(userDTO.getAccountBalance())
                .setRole(userDTO.getRole());
    }
}
