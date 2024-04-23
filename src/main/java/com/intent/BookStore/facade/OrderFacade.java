package com.intent.BookStore.facade;

import com.intent.BookStore.dto.OrderDTO;
import com.intent.BookStore.dto.OrderItemDTO;
import org.springframework.security.core.Authentication;

public interface OrderFacade {

    OrderDTO createOrderItem(Authentication authentication, OrderItemDTO orderItemDTO);

    OrderDTO getOrderById(Long id);

    void deleteOrderItem(Authentication authentication, Long id);

    void deleteOrder(Authentication authentication, Long id);

    OrderDTO closeOrder(Authentication authentication, Long id);
}
