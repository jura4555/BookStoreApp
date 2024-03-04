package com.intent.BookStore.facade;

import com.intent.BookStore.dto.OrderDTO;
import com.intent.BookStore.dto.OrderItemDTO;

public interface OrderFacade {

    OrderDTO createOrderItem(OrderItemDTO orderItemDTO);

    OrderDTO getOrderById(Long id);
}
