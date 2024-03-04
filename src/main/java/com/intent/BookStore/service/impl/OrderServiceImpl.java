package com.intent.BookStore.service.impl;

import com.intent.BookStore.exception.OrderNotFoundException;
import com.intent.BookStore.model.Order;
import com.intent.BookStore.model.OrderItem;
import com.intent.BookStore.repository.OrderItemRepository;
import com.intent.BookStore.repository.OrderRepository;
import com.intent.BookStore.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import static com.intent.BookStore.util.ExceptionMessageUtil.ORDER_NOT_FOUND_BY_ID_ERROR_MESSAGE;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private OrderItemRepository orderItemRepository;
    private OrderRepository orderRepository;

    @Override
    public Order createOrderItem(OrderItem orderItem) {

        orderItemRepository.save(orderItem);

        return null;
    }

    @Override
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(String.format(ORDER_NOT_FOUND_BY_ID_ERROR_MESSAGE, id)));
    }
}
