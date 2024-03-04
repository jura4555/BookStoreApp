package com.intent.BookStore.mapper;

import com.intent.BookStore.dto.OrderItemDTO;
import com.intent.BookStore.model.Book;
import com.intent.BookStore.model.Order;
import com.intent.BookStore.model.OrderItem;
import lombok.experimental.UtilityClass;

@UtilityClass
public class OrderItemMapperUtil {
    public static OrderItemDTO toOrderItemDTO(OrderItem orderItem) {
        return new OrderItemDTO()
                .setId(orderItem.getId())
                .setOrderId(orderItem.getOrder().getId())
                .setBookId(orderItem.getBook().getId())
                .setQuantity(orderItem.getQuantity())
                .setTotalPrice(orderItem.getTotalPrice());
    }

    public static OrderItem toOrderItem(OrderItemDTO orderItemDTO, Order order, Book book) {
        return new OrderItem()
                .setId(orderItemDTO.getId())
                .setOrder(order)
                .setBook(book)
                .setQuantity(orderItemDTO.getQuantity())
                .setTotalPrice(orderItemDTO.getTotalPrice());
    }
}
