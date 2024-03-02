package com.intent.BookStore.mapper;

import com.intent.BookStore.dto.OrderDTO;
import com.intent.BookStore.model.Order;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderMapperUtil {
    public static OrderDTO toOrderDTO(Order order) {
        return new OrderDTO()
                .setId(order.getId())
                .setUserDTO(UserMapperUtil.toUserDTO(order.getUser()))
                .setQuantity(order.getQuantity())
                .setTotalPrice(order.getTotalPrice())
                .setCompletedAt(order.getCompletedAt());
    }

    public static Order toOrder(OrderDTO orderDTO) {
        return new Order()
                .setId(orderDTO.getId())
                .setUser(UserMapperUtil.toUser(orderDTO.getUserDTO()))
                .setQuantity(orderDTO.getQuantity())
                .setTotalPrice(orderDTO.getTotalPrice())
                .setCompletedAt(orderDTO.getCompletedAt());
    }
}
