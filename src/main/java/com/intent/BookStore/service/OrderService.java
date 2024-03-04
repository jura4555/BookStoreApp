package com.intent.BookStore.service;

import com.intent.BookStore.model.Order;
import com.intent.BookStore.model.OrderItem;

public interface OrderService {

    Order createOrderItem(OrderItem orderItem);

    Order getOrderById(Long id);
}
