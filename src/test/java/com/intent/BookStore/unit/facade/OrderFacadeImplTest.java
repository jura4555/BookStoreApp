package com.intent.BookStore.unit.facade;

import com.intent.BookStore.dto.OrderDTO;
import com.intent.BookStore.dto.OrderItemDTO;
import com.intent.BookStore.facade.impl.OrderFacadeImpl;
import com.intent.BookStore.model.Book;
import com.intent.BookStore.model.Order;
import com.intent.BookStore.model.OrderItem;
import com.intent.BookStore.model.User;
import com.intent.BookStore.service.BookService;
import com.intent.BookStore.service.OrderService;
import com.intent.BookStore.service.UserService;
import com.intent.BookStore.unit.util.TestOrderDataUtil;
import com.intent.BookStore.unit.util.TestOrderItemDataUtil;
import com.intent.BookStore.unit.util.TestUserDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Set;

import static com.intent.BookStore.unit.util.TestBookDataUtil.BOOK_ID_1;
import static com.intent.BookStore.unit.util.TestOrderDataUtil.ORDER_ID_1;
import static com.intent.BookStore.unit.util.TestOrderDataUtil.ORDER_ID_2;
import static com.intent.BookStore.unit.util.TestOrderItemDataUtil.ORDER_ITEM_ID_4;
import static com.intent.BookStore.unit.util.TestUserDataUtil.USER_ID_2;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class)
class OrderFacadeImplTest {

    @Mock
    private OrderService orderService;

    @Mock
    private UserService userService;

    @Mock
    private BookService bookService;

    @InjectMocks
    private OrderFacadeImpl orderFacade;

    @Test
    void createOrderItemTest() {
        OrderItemDTO initialOrderItemDTO = TestOrderItemDataUtil.getOrderItemDTO4()
                .setId(0L)
                .setTotalPrice(null)
                .setOrderId(null);
        Order initialOrder = TestOrderDataUtil.getOrder2()
                .setTotalPrice(null)
                .setQuantity(0)
                .setQuantity(0);
        OrderItemDTO orderItemDTO = TestOrderItemDataUtil.getOrderItemDTO4();
        OrderItem orderItem = TestOrderItemDataUtil.getOrderItem4();
        Book book = orderItem.getBook();
        User user = TestUserDataUtil.getUser2();
        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem);
        Order createdOrder = TestOrderDataUtil.getOrder2().setOrderItems(orderItems);
        Set<OrderItemDTO> orderItemDTOs = new HashSet<>();
        orderItemDTOs.add(orderItemDTO);
        OrderDTO expectedOrderDTO = TestOrderDataUtil.getOrderDTO2().setOrderItemDTOs(orderItemDTOs);

        when(bookService.getBookById(BOOK_ID_1)).thenReturn(book);
        when(userService.getUserById(USER_ID_2)).thenReturn(user);
        when(orderService.createOrder(user)).thenReturn(initialOrder);
        when(orderService.createOrderItem(any(OrderItem.class))).thenReturn(createdOrder);

        OrderDTO result = orderFacade.createOrderItem(initialOrderItemDTO, USER_ID_2);
        assertThat(result, allOf(
                hasProperty("id", equalTo(expectedOrderDTO.getId())),
                hasProperty("userId", equalTo(expectedOrderDTO.getUserId())),
                hasProperty("quantity", equalTo(expectedOrderDTO.getQuantity())),
                hasProperty("totalPrice", equalTo(expectedOrderDTO.getTotalPrice())),
                hasProperty("closed", equalTo(expectedOrderDTO.isClosed())),
                hasProperty("orderItemDTOs", equalTo(expectedOrderDTO.getOrderItemDTOs())
        )));
        verify(bookService, times(1)).getBookById(BOOK_ID_1);
        verify(userService, times(1)).getUserById(USER_ID_2);
        verify(orderService, times(1)).createOrder(user);
        verify(orderService, times(1)).createOrderItem(any(OrderItem.class));
    }

    @Test
    void createOrderItemWithExistOrderTest() {
        OrderItemDTO initialOrderItemDTO = TestOrderItemDataUtil.getOrderItemDTO4()
                .setId(0L)
                .setTotalPrice(null);
        OrderItemDTO orderItemDTO = TestOrderItemDataUtil.getOrderItemDTO4();
        OrderItemDTO orderItemDTO3 = TestOrderItemDataUtil.getOrderItemDTO3();

        OrderItem orderItem = TestOrderItemDataUtil.getOrderItem4();
        OrderItem orderItem3 = TestOrderItemDataUtil.getOrderItem3();

        Order order = TestOrderDataUtil.getOrder2();
        Book book = orderItem.getBook();
        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(orderItem);
        orderItems.add(orderItem3);
        Order createdOrder = TestOrderDataUtil.getOrder2().setOrderItems(orderItems);
        Set<OrderItemDTO> orderItemDTOs = new HashSet<>();
        orderItemDTOs.add(orderItemDTO);
        orderItemDTOs.add(orderItemDTO3);
        OrderDTO expectedOrderDTO = TestOrderDataUtil.getOrderDTO2().setOrderItemDTOs(orderItemDTOs);

        when(bookService.getBookById(BOOK_ID_1)).thenReturn(book);
        when(orderService.getOrderById(ORDER_ID_2)).thenReturn(order);
        when(orderService.createOrderItem(any(OrderItem.class))).thenReturn(createdOrder);

        OrderDTO result = orderFacade.createOrderItem(initialOrderItemDTO, 0);
        assertThat(result, allOf(
                hasProperty("id", equalTo(expectedOrderDTO.getId())),
                hasProperty("userId", equalTo(expectedOrderDTO.getUserId())),
                hasProperty("quantity", equalTo(expectedOrderDTO.getQuantity())),
                hasProperty("totalPrice", equalTo(expectedOrderDTO.getTotalPrice())),
                hasProperty("closed", equalTo(expectedOrderDTO.isClosed())),
                hasProperty("orderItemDTOs", equalTo(expectedOrderDTO.getOrderItemDTOs())
                )));
        verify(bookService, times(1)).getBookById(BOOK_ID_1);
        verify(orderService, times(1)).getOrderById(ORDER_ID_2);
        verify(orderService, times(1)).createOrderItem(any(OrderItem.class));
    }

    @Test
    void getOrderByIdTest() {
        Order order = TestOrderDataUtil.getOrder1();
        OrderDTO expectedOrderDTO = TestOrderDataUtil.getOrderDTO1();

        when(orderService.getOrderById(ORDER_ID_1)).thenReturn(order);
        OrderDTO result = orderFacade.getOrderById(ORDER_ID_1);
        assertThat(result, allOf(
                hasProperty("id", equalTo(expectedOrderDTO.getId())),
                hasProperty("userId", equalTo(expectedOrderDTO.getUserId())),
                hasProperty("quantity", equalTo(expectedOrderDTO.getQuantity())),
                hasProperty("totalPrice", equalTo(expectedOrderDTO.getTotalPrice())),
                hasProperty("closed", equalTo(expectedOrderDTO.isClosed())),
                hasProperty("completedAt", equalTo(expectedOrderDTO.getCompletedAt()))
        ));
        verify(orderService, times(1)).getOrderById(ORDER_ID_1);
    }

    @Test
    void deleteOrderItemTest() {
        orderFacade.deleteOrderItem(ORDER_ITEM_ID_4);
        verify(orderService, times(1)).deleteOrderItem(ORDER_ITEM_ID_4);
    }

    @Test
    void deleteOrderTest() {
        orderFacade.deleteOrder(ORDER_ID_2);
        verify(orderService, times(1)).deleteOrder(ORDER_ID_2);
    }

    @Test
    void closeOrderTest() {
        Order order = TestOrderDataUtil.getOrder1();
        OrderDTO expectedOrderDTO = TestOrderDataUtil.getOrderDTO1();

        when(orderService.closeOrder(ORDER_ID_1)).thenReturn(order);

        OrderDTO result = orderFacade.closeOrder(ORDER_ID_1);
        assertThat(result, allOf(
                hasProperty("id", equalTo(expectedOrderDTO.getId())),
                hasProperty("userId", equalTo(expectedOrderDTO.getUserId())),
                hasProperty("quantity", equalTo(expectedOrderDTO.getQuantity())),
                hasProperty("totalPrice", equalTo(expectedOrderDTO.getTotalPrice())),
                hasProperty("closed", equalTo(expectedOrderDTO.isClosed())),
                hasProperty("completedAt", equalTo(expectedOrderDTO.getCompletedAt()))
        ));
        verify(orderService, times(1)).closeOrder(ORDER_ID_1);


    }
}
