package com.intent.BookStore.facade.impl;

import com.intent.BookStore.dto.OrderDTO;
import com.intent.BookStore.dto.OrderItemDTO;
import com.intent.BookStore.facade.OrderFacade;
import com.intent.BookStore.mapper.OrderItemMapperUtil;
import com.intent.BookStore.model.Book;
import com.intent.BookStore.model.Order;
import com.intent.BookStore.model.OrderItem;
import com.intent.BookStore.model.User;
import com.intent.BookStore.service.BookService;
import com.intent.BookStore.service.OrderService;
import com.intent.BookStore.service.UserService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import static com.intent.BookStore.mapper.OrderMapperUtil.toOrderDTO;

@Component
@RequiredArgsConstructor
public class OrderFacadeImpl implements OrderFacade {

    private final OrderService orderService;
    private final UserService userService;
    private final BookService bookService;
    @Override
    @Transactional
    public OrderDTO createOrderItem(Authentication authentication, OrderItemDTO orderItemDTO) {
        Book book = bookService.getBookById(orderItemDTO.getBookId());
        Long userId = userService.getUserByUsername(authentication.getName()).getId();
        Order order = null;
        if(orderItemDTO.getOrderId() == null && userId > 0) {
            User user = userService.getUserById(userId);
            order = orderService.createOrder(user);
        } else {
            order = orderService.getOrderById(orderItemDTO.getOrderId());
            checkOrderAccess(userId, order);
        }
        OrderItem orderItem = OrderItemMapperUtil.toOrderItem(orderItemDTO, order, book);
        Order createdOrder = orderService.createOrderItem(orderItem);

        return toOrderDTO(createdOrder);
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderService.getOrderById(id);
        return toOrderDTO(order);
    }

    @Override
    public void deleteOrderItem(Authentication authentication, Long id) {
        Long userId = userService.getUserByUsername(authentication.getName()).getId();
        OrderItem orderItem = orderService.getOrderItemById(id);
        checkOrderAccess(userId, orderItem.getOrder());
        orderService.deleteOrderItem(orderItem);
    }

    @Override
    public void deleteOrder(Authentication authentication, Long id) {
        Long userId = userService.getUserByUsername(authentication.getName()).getId();
        Order order = orderService.getOrderById(id);
        checkOrderAccess(userId, order);
        orderService.deleteOrder(order);
    }

    @Override
    public OrderDTO closeOrder(Authentication authentication, Long id) {
        Long userId = userService.getUserByUsername(authentication.getName()).getId();
        Order order = orderService.getOrderById(id);
        checkOrderAccess(userId, order);
        return toOrderDTO(orderService.closeOrder(order));
    }

    private void checkOrderAccess(Long userId, Order order) {
        if (!order.getUser().getId().equals(userId)) {
            throw new AccessDeniedException("You are not allowed to modify this order");
        }
    }
}
