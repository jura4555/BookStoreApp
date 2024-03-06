package com.intent.BookStore.facade;

import com.intent.BookStore.dto.OrderDTO;
import com.intent.BookStore.dto.OrderItemDTO;

public interface OrderFacade {

    OrderDTO createOrderItem(OrderItemDTO orderItemDTO, long userId);

    OrderDTO getOrderById(Long id);

    void deleteOrderItem(Long id);

    void deleteOrder(Long id);

    OrderDTO closeOrder(Long id);
}
