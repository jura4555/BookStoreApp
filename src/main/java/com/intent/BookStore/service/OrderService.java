package com.intent.BookStore.service;

import com.intent.BookStore.model.Order;
import com.intent.BookStore.model.OrderItem;
import com.intent.BookStore.model.User;

public interface OrderService {

    Order createOrderItem(OrderItem orderItem);

    Order getOrderById(Long id);

    Order createOrder(User user);

    void deleteOrderItem(Long id);

    void deleteOrder(Long id);

    Order closeOrder(Long id);

    //closed ++


    //changeOrderItemQuantity - як ідея проте зараз це не є правильним
}
