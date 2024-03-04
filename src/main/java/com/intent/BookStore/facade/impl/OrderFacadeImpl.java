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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.intent.BookStore.mapper.OrderItemMapperUtil.toOrderItemDTO;
import static com.intent.BookStore.mapper.OrderMapperUtil.toOrderDTO;

@Component
@RequiredArgsConstructor
public class OrderFacadeImpl implements OrderFacade {

    private final OrderService orderService;
    //private final UserService userService;
    private final BookService bookService;
    @Override
    public OrderDTO createOrderItem(OrderItemDTO orderItemDTO) {
        //todo: в orderItemDTO міститься bookId і userID
        // todo: в order міститься userId
        //todo: можна спочатку створити order пустий а потім вже його наповнювати - без цього буде погано!!!
        Book book = bookService.getBookById(orderItemDTO.getBookId());
        Order order = orderService.getOrderById(orderItemDTO.getOrderId());
        // todo: проговорити що робити коли order ще не стоврений і як це зробити
        // todo: запропонувати спочатку або створити його або тут перевіряти чи такий order істнує і якщо ні то
        // todo: ту використати метод createInitialOrder і

        OrderItem orderItem = OrderItemMapperUtil.toOrderItem(orderItemDTO, order, book);
        Order createdOrder = orderService.createOrderItem(orderItem);

        return toOrderDTO(createdOrder);
        //return null;
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderService.getOrderById(id);
        System.out.println(order.getUser().getEmail());
        System.out.println(order.getUser());
        return toOrderDTO(order);
    }


}
