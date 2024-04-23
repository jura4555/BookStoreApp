package com.intent.BookStore.service;

import com.intent.BookStore.model.Order;
import com.intent.BookStore.model.OrderItem;
import com.intent.BookStore.model.User;

public interface OrderService {

    Order createOrderItem(OrderItem orderItem);

    Order getOrderById(Long id);

    OrderItem getOrderItemById(Long id);

    Order createOrder(User user);

    void deleteOrderItem(OrderItem orderItem);

    void deleteOrder(Order order);

    Order closeOrder(Order order);
}
