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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static com.intent.BookStore.unit.util.TestBookDataUtil.BOOK_ID_1;
import static com.intent.BookStore.unit.util.TestOrderDataUtil.ORDER_ID_1;
import static com.intent.BookStore.unit.util.TestOrderDataUtil.ORDER_ID_2;
import static com.intent.BookStore.unit.util.TestOrderItemDataUtil.ORDER_ITEM_ID_4;
import static com.intent.BookStore.unit.util.TestUserDataUtil.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
        Authentication authentication = new UsernamePasswordAuthenticationToken(USERNAME_2, "",
                Collections.singleton(new SimpleGrantedAuthority(ROLE_2.name())));
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
        when(userService.getUserByUsername(USERNAME_2)).thenReturn(user);
        when(userService.getUserById(USER_ID_2)).thenReturn(user);
        when(orderService.createOrder(user)).thenReturn(initialOrder);
        when(orderService.createOrderItem(any(OrderItem.class))).thenReturn(createdOrder);

        OrderDTO result = orderFacade.createOrderItem(authentication, initialOrderItemDTO);
        assertThat(result, allOf(
                hasProperty("id", equalTo(expectedOrderDTO.getId())),
                hasProperty("userId", equalTo(expectedOrderDTO.getUserId())),
                hasProperty("quantity", equalTo(expectedOrderDTO.getQuantity())),
                hasProperty("totalPrice", equalTo(expectedOrderDTO.getTotalPrice())),
                hasProperty("closed", equalTo(expectedOrderDTO.isClosed())),
                hasProperty("orderItemDTOs", equalTo(expectedOrderDTO.getOrderItemDTOs())
        )));
        verify(bookService, times(1)).getBookById(BOOK_ID_1);
        verify(userService, times(1)).getUserByUsername(USERNAME_2);
        verify(userService, times(1)).getUserById(USER_ID_2);
        verify(orderService, times(1)).createOrder(user);
        verify(orderService, times(1)).createOrderItem(any(OrderItem.class));
    }

    @Test
    void createOrderItemWithExistOrderTest() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(USERNAME_2, "",
                Collections.singleton(new SimpleGrantedAuthority(ROLE_2.name())));
        OrderItemDTO initialOrderItemDTO = TestOrderItemDataUtil.getOrderItemDTO4()
                .setId(0L)
                .setTotalPrice(null);
        OrderItemDTO orderItemDTO = TestOrderItemDataUtil.getOrderItemDTO4();
        OrderItemDTO orderItemDTO3 = TestOrderItemDataUtil.getOrderItemDTO3();

        OrderItem orderItem = TestOrderItemDataUtil.getOrderItem4();
        OrderItem orderItem3 = TestOrderItemDataUtil.getOrderItem3();

        Order order = TestOrderDataUtil.getOrder2();
        User user = TestUserDataUtil.getUser2();
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
        when(userService.getUserByUsername(USERNAME_2)).thenReturn(user);
        when(orderService.getOrderById(ORDER_ID_2)).thenReturn(order);
        when(orderService.createOrderItem(any(OrderItem.class))).thenReturn(createdOrder);

        OrderDTO result = orderFacade.createOrderItem(authentication, initialOrderItemDTO);
        assertThat(result, allOf(
                hasProperty("id", equalTo(expectedOrderDTO.getId())),
                hasProperty("userId", equalTo(expectedOrderDTO.getUserId())),
                hasProperty("quantity", equalTo(expectedOrderDTO.getQuantity())),
                hasProperty("totalPrice", equalTo(expectedOrderDTO.getTotalPrice())),
                hasProperty("closed", equalTo(expectedOrderDTO.isClosed())),
                hasProperty("orderItemDTOs", equalTo(expectedOrderDTO.getOrderItemDTOs())
                )));
        verify(bookService, times(1)).getBookById(BOOK_ID_1);
        verify(userService, times(1)).getUserByUsername(USERNAME_2);
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
        Authentication authentication = new UsernamePasswordAuthenticationToken(USERNAME_2, "",
                Collections.singleton(new SimpleGrantedAuthority(ROLE_2.name())));
        User user = TestUserDataUtil.getUser2();
        OrderItem orderItem = TestOrderItemDataUtil.getOrderItem4();

        when(userService.getUserByUsername(USERNAME_2)).thenReturn(user);
        when(orderService.getOrderItemById(ORDER_ITEM_ID_4)).thenReturn(orderItem);

        orderFacade.deleteOrderItem(authentication, ORDER_ITEM_ID_4);

        verify(userService, times(1)).getUserByUsername(USERNAME_2);
        verify(orderService, times(1)).getOrderItemById(ORDER_ITEM_ID_4);
        verify(orderService, times(1)).deleteOrderItem(orderItem);
    }

    @Test
    void deleteOrderTest() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(USERNAME_2, "",
                Collections.singleton(new SimpleGrantedAuthority(ROLE_2.name())));
        User user = TestUserDataUtil.getUser2();
        Order order = TestOrderDataUtil.getOrder2();

        when(userService.getUserByUsername(USERNAME_2)).thenReturn(user);
        when(orderService.getOrderById(ORDER_ID_2)).thenReturn(order);


        orderFacade.deleteOrder(authentication, ORDER_ID_2);

        verify(userService, times(1)).getUserByUsername(USERNAME_2);
        verify(orderService, times(1)).getOrderById(ORDER_ID_2);
        verify(orderService, times(1)).deleteOrder(order);
    }

    @Test
    void deleteOrderWithAccessDeniedExceptionTest() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(USERNAME_1, "",
                Collections.singleton(new SimpleGrantedAuthority(ROLE_1.name())));
        User user = TestUserDataUtil.getUser1();
        Order order = TestOrderDataUtil.getOrder2();

        when(userService.getUserByUsername(USERNAME_1)).thenReturn(user);
        when(orderService.getOrderById(ORDER_ID_2)).thenReturn(order);
        assertThrows(AccessDeniedException.class, () -> orderFacade.deleteOrder(authentication, ORDER_ID_2));
    }

    @Test
    void closeOrderTest() {
        Authentication authentication = new UsernamePasswordAuthenticationToken(USERNAME_1, "",
                Collections.singleton(new SimpleGrantedAuthority(ROLE_1.name())));
        User user = TestUserDataUtil.getUser1();
        Order order = TestOrderDataUtil.getOrder1();
        OrderDTO expectedOrderDTO = TestOrderDataUtil.getOrderDTO1();

        when(userService.getUserByUsername(USERNAME_1)).thenReturn(user);
        when(orderService.getOrderById(ORDER_ID_1)).thenReturn(order);
        when(orderService.closeOrder(order)).thenReturn(order);

        OrderDTO result = orderFacade.closeOrder(authentication, ORDER_ID_1);
        assertThat(result, allOf(
                hasProperty("id", equalTo(expectedOrderDTO.getId())),
                hasProperty("userId", equalTo(expectedOrderDTO.getUserId())),
                hasProperty("quantity", equalTo(expectedOrderDTO.getQuantity())),
                hasProperty("totalPrice", equalTo(expectedOrderDTO.getTotalPrice())),
                hasProperty("closed", equalTo(expectedOrderDTO.isClosed())),
                hasProperty("completedAt", equalTo(expectedOrderDTO.getCompletedAt()))
        ));

        verify(userService, times(1)).getUserByUsername(USERNAME_1);
        verify(orderService, times(1)).getOrderById(ORDER_ID_1);
        verify(orderService, times(1)).closeOrder(order);
    }
}
