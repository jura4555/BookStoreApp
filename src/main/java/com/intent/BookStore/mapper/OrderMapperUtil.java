package com.intent.BookStore.mapper;

import com.intent.BookStore.dto.OrderDTO;
import com.intent.BookStore.dto.OrderItemDTO;
import com.intent.BookStore.model.Order;
import com.intent.BookStore.model.User;
import lombok.experimental.UtilityClass;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@UtilityClass
public class OrderMapperUtil {
    public static OrderDTO toOrderDTO(Order order) {
        OrderDTO orderDTO = new OrderDTO()
                .setId(order.getId())
                .setUserId(order.getUser().getId())
                .setQuantity(order.getQuantity())
                .setTotalPrice(order.getTotalPrice())
                .setCompletedAt(order.getCompletedAt());
        Set<OrderItemDTO> orderItemDTOs = new HashSet<>();
        if (order.getOrderItems() != null) {
            orderItemDTOs = order.getOrderItems().stream()
                    .map(OrderItemMapperUtil::toOrderItemDTO)
                    .collect(Collectors.toSet());
        }
        orderDTO.setOrderItemDTOs(orderItemDTOs);
        return orderDTO;
    }

    public static Order toOrder(OrderDTO orderDTO, User user) {
        return new Order()
                .setId(orderDTO.getId())
                .setUser(user)
                .setQuantity(orderDTO.getQuantity())
                .setTotalPrice(orderDTO.getTotalPrice())
                .setCompletedAt(orderDTO.getCompletedAt());
    }
}
