package com.intent.BookStore.service.impl;

import com.intent.BookStore.exception.*;
import com.intent.BookStore.model.Book;
import com.intent.BookStore.model.Order;
import com.intent.BookStore.model.OrderItem;
import com.intent.BookStore.model.User;
import com.intent.BookStore.repository.OrderItemRepository;
import com.intent.BookStore.repository.OrderRepository;
import com.intent.BookStore.repository.UserRepository;
import com.intent.BookStore.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import static com.intent.BookStore.util.ExceptionMessageUtil.*;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderItemRepository orderItemRepository;
    private final OrderRepository orderRepository;

    private final UserRepository userRepository;

    @Override
    public Order createOrderItem(OrderItem orderItem) {
        Order order = orderItem.getOrder();
        Book book = orderItem.getBook();
        checkOrderStatus(order);
        checkSufficientStock(orderItem, book);
        decreaseBookQuantity(orderItem, book);
        updateItemTotal(orderItem, book);
        updateOrderTotal(orderItem, order);
        updateOrderItems(orderItem, order);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public Order getOrderById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(String.format(ORDER_NOT_FOUND_BY_ID_ERROR_MESSAGE, id)));
    }

    @Override
    @Transactional
    public Order createOrder(User user) {
        Order order = new Order();
        order.setUser(user);
        order.setClosed(false);
        return orderRepository.save(order);
    }

    @Override
    @Transactional
    public void deleteOrderItem(Long id) {
        OrderItem orderItem = getOrderItemById(id);
        Order order = orderItem.getOrder();
        Book book = orderItem.getBook();
        checkOrderStatus(order);
        updateOrder(orderItem, order);
        increaseBookQuantity(book, orderItem);
        removeFromOrder(order, orderItem);
        deleteOrSaveOrder(order);
        deleteOrderItem(orderItem);
    }

    @Override
    @Transactional
    public void deleteOrder(Long id) {
        Order order = getOrderById(id);
        checkOrderStatus(order);
        Set<OrderItem> orderItems = order.getOrderItems();
        restoreBookQuantities(orderItems);
        deleteOrderItems(orderItems);
        deleteOrder(order);
    }

    @Override
    @Transactional
    public Order closeOrder(Long id) {
        Order order = getOrderById(id);
        User user = order.getUser();
        checkOrderStatus(order);
        checkUserBalance(order, user);
        updateUserBalance(order, user);
        markOrderAsClosed(order);
        return orderRepository.save(order);
    }


    private void deleteOrderItems(Set<OrderItem> orderItems) {
        orderItemRepository.deleteAll(orderItems);
    }

    private void restoreBookQuantities(Set<OrderItem> orderItems) {
        for (OrderItem orderItem : orderItems) {
            Book book = orderItem.getBook();
            book.setQuantity(book.getQuantity() + orderItem.getQuantity());
        }
    }

    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new OrderItemNotFoundException(String.format(ORDER_ITEM_NOT_FOUND_BY_ID_ERROR_MESSAGE, id)));
    }

    private void checkOrderStatus(Order order) {
        if(order.isClosed()) {
            throw new ClosedOrderException(String.format(ORDER_CLOSED_ERROR_MESSAGE, order.getId()));
        }
    }

    private void checkSufficientStock(OrderItem orderItem, Book book) {
        if (orderItem.getQuantity() > book.getQuantity()) {
            throw new InsufficientStockException(String.format(INSUFFICIENT_STOCK_ERROR_MESSAGE,
                    book.getId(), book.getQuantity(), orderItem.getQuantity()));
        }
    }

    private void decreaseBookQuantity(OrderItem orderItem, Book book) {
        book.setQuantity(book.getQuantity() - orderItem.getQuantity());
    }

    private void updateItemTotal(OrderItem orderItem, Book book) {
        orderItem.setTotalPrice(book.getPrice().multiply(BigDecimal.valueOf(orderItem.getQuantity())));
    }

    private void updateOrderTotal(OrderItem orderItem, Order order) {
        order.setQuantity(order.getQuantity() + orderItem.getQuantity());
        if (order.getTotalPrice() == null) {
            order.setTotalPrice(BigDecimal.ZERO);
        }
        order.setTotalPrice(order.getTotalPrice().add(orderItem.getTotalPrice()));
    }

    private void updateOrderItems(OrderItem orderItem, Order order) {
        Set<OrderItem> orderItems = order.getOrderItems();
        boolean changed = false;
        if (orderItems == null) {
            orderItems = new HashSet<>();
        } else {
            changed = updateExistingOrderItem(orderItem, orderItems, changed);
        }
        if (!changed) {
            orderItems.add(orderItem);
        }
        order.setOrderItems(orderItems);
    }

    private boolean updateExistingOrderItem(OrderItem orderItem, Set<OrderItem> orderItems, boolean changed) {
        for (OrderItem existingOrderItem : orderItems) {
            if (existingOrderItem.getBook().getTitle().equals(orderItem.getBook().getTitle())) {
                existingOrderItem.setQuantity(existingOrderItem.getQuantity() + orderItem.getQuantity());
                existingOrderItem.setTotalPrice(existingOrderItem.getTotalPrice().add(orderItem.getTotalPrice()));
                changed = true;
                break;
            }
        }
        return changed;
    }

    private void updateOrder(OrderItem orderItem, Order order) {
        order.setQuantity(order.getQuantity() - orderItem.getQuantity());
        order.setTotalPrice(order.getTotalPrice().subtract(orderItem.getTotalPrice()));
    }

    private void increaseBookQuantity(Book book, OrderItem orderItem) {
        book.setQuantity(book.getQuantity() + orderItem.getQuantity());
    }

    private void removeFromOrder(Order order, OrderItem orderItem) {
        Set<OrderItem> orderItems = order.getOrderItems();
        orderItems.removeIf(o -> o.getId().equals(orderItem.getId()));
        order.setOrderItems(orderItems);
    }

    private void deleteOrSaveOrder(Order order) {
        if (order.getOrderItems().isEmpty()) {
            removeOrderFromUserAndDelete(order);
        } else {
            saveOrder(order);
        }
    }

    private void removeOrderFromUserAndDelete(Order order) {
        User user = order.getUser();
        Set<Order> orders = user.getOrders();
        orders.removeIf(o -> o.getId().equals(order.getId()));
        user.setOrders(orders);
        userRepository.save(user);
        orderRepository.deleteById(order.getId());
    }

    private void saveOrder(Order order) {
        orderRepository.save(order);
    }

    private void deleteOrderItem(OrderItem orderItem) {
        orderItemRepository.delete(orderItem);
    }

    private void deleteOrder(Order order) {
        orderRepository.delete(order);
    }

    private void updateUserBalance(Order order, User user) {
        user.setAccountBalance(user.getAccountBalance().subtract(order.getTotalPrice()));
    }

    private void checkUserBalance(Order order, User user) {
        if(user.getAccountBalance().compareTo(order.getTotalPrice()) < 0) {
            throw new InsufficientFundsException(INSUFFICIENT_FUNDS_ERROR_MESSAGE);
        }
    }

    private void markOrderAsClosed(Order order) {
        order.setClosed(true);
        order.setCompletedAt(LocalDate.now());
    }
}
