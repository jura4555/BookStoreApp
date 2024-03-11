package com.intent.BookStore.unit.service;

import com.intent.BookStore.exception.*;
import com.intent.BookStore.model.Book;
import com.intent.BookStore.model.Order;
import com.intent.BookStore.model.OrderItem;
import com.intent.BookStore.model.User;
import com.intent.BookStore.repository.OrderItemRepository;
import com.intent.BookStore.repository.OrderRepository;
import com.intent.BookStore.repository.UserRepository;
import com.intent.BookStore.service.impl.OrderServiceImpl;
import com.intent.BookStore.unit.util.TestOrderDataUtil;
import com.intent.BookStore.unit.util.TestOrderItemDataUtil;
import com.intent.BookStore.unit.util.TestUserDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static com.intent.BookStore.unit.util.TestOrderDataUtil.ORDER_ID_1;
import static com.intent.BookStore.unit.util.TestOrderDataUtil.ORDER_ID_2;
import static com.intent.BookStore.unit.util.TestOrderItemDataUtil.ORDER_ITEM_ID_4;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.ArgumentMatchers.any;


@ExtendWith(MockitoExtension.class)
class OrderServiceImplTest {

    @Mock
    OrderItemRepository orderItemRepository;

    @Mock
    OrderRepository orderRepository;

    @Mock
    UserRepository userRepository;

    @InjectMocks
    private OrderServiceImpl orderService;

    private static final BigDecimal ORDER_TOTAL_PRICE_EXCEPTION = BigDecimal.valueOf(1000.00);


    @Test
    void createOrderItemTest() {
        OrderItem orderItem = TestOrderItemDataUtil.getOrderItem4();
        Order order = orderItem.getOrder()
                .setId(0L);
        Order createdOrder = orderItem.getOrder();
        Book book = orderItem.getBook();
        book.setQuantity(book.getQuantity() - orderItem.getQuantity());
        when(orderRepository.save(order)).thenReturn(createdOrder);
        Order result = orderService.createOrderItem(orderItem);
        assertThat(result, allOf(
                hasProperty("id", equalTo(createdOrder.getId())),
                hasProperty("user", equalTo(createdOrder.getUser())),
                hasProperty("quantity", equalTo(createdOrder.getQuantity())),
                hasProperty("totalPrice", equalTo(createdOrder.getTotalPrice())),
                hasProperty("completedAt", equalTo(createdOrder.getCompletedAt()))
        ));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void createOrderItemWithUpdateTest() {
        OrderItem orderItem = TestOrderItemDataUtil.getOrderItem4();
        OrderItem updatedOrderItem = TestOrderItemDataUtil.getOrderItem4();
        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(updatedOrderItem);
        Order order = TestOrderDataUtil.getOrder2().setOrderItems(orderItems);
        orderItem.setOrder(order);
        orderItem.getOrder()
                .setId(0L);
        Order createdOrder = orderItem.getOrder();
        Book book = orderItem.getBook();
        book.setQuantity(book.getQuantity() - orderItem.getQuantity());
        when(orderRepository.save(order)).thenReturn(createdOrder);
        Order result = orderService.createOrderItem(orderItem);
        assertThat(result, allOf(
                hasProperty("id", equalTo(createdOrder.getId())),
                hasProperty("user", equalTo(createdOrder.getUser())),
                hasProperty("quantity", equalTo(createdOrder.getQuantity())),
                hasProperty("totalPrice", equalTo(createdOrder.getTotalPrice())),
                hasProperty("completedAt", equalTo(createdOrder.getCompletedAt()))
        ));
        verify(orderRepository, times(1)).save(order);
    }

    @Test
    void createOrderItemWithClosedOrderExceptionTest() {
        OrderItem orderItem = TestOrderItemDataUtil.getOrderItem1();
        assertThrows(ClosedOrderException.class, () -> orderService.createOrderItem(orderItem));
    }

    @Test
    void createOrderItemWithInsufficientStockExceptionTest() {
        OrderItem orderItem = TestOrderItemDataUtil.getOrderItem4();
        orderItem.setQuantity(100);
        assertThrows(InsufficientStockException.class, () -> orderService.createOrderItem(orderItem));
    }

    @Test
    void getOrderByIdTest() {
        Order order = TestOrderDataUtil.getOrder1();
        when(orderRepository.findById(ORDER_ID_1)).thenReturn(Optional.of(order));
        Order result = orderService.getOrderById(ORDER_ID_1);
        assertThat(result, allOf(
                hasProperty("id", equalTo(order.getId())),
                hasProperty("user", equalTo(order.getUser())),
                hasProperty("quantity", equalTo(order.getQuantity())),
                hasProperty("totalPrice", equalTo(order.getTotalPrice())),
                hasProperty("completedAt", equalTo(order.getCompletedAt()))
        ));
        verify(orderRepository, times(1)).findById(ORDER_ID_1);
    }

    @Test
    void getOrderByIdWithNotFoundExceptionTest(){
        when(orderRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(OrderNotFoundException.class, () -> orderService.getOrderById(anyLong()));
    }

    @Test
    void getOrderItemByIdWithNotFoundExceptionTest() {
        when(orderItemRepository.findById(anyLong())).thenReturn(Optional.empty());
        assertThrows(OrderItemNotFoundException.class, () -> orderService.getOrderItemById(anyLong()));
    }

    @Test
    void createOrderTest() {
        Order order = TestOrderDataUtil.getOrder2();
        when(orderRepository.save(any(Order.class))).thenReturn(order);
        Order result = orderService.createOrder(order.getUser());
        assertThat(result, allOf(
                hasProperty("id", equalTo(order.getId())),
                hasProperty("user", equalTo(order.getUser())),
                hasProperty("quantity", equalTo(order.getQuantity())),
                hasProperty("totalPrice", equalTo(order.getTotalPrice())),
                hasProperty("completedAt", equalTo(order.getCompletedAt()))
        ));
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void deleteOrderItemTest() {
        OrderItem orderItem = TestOrderItemDataUtil.getOrderItem4();
        OrderItem internalOrderItem1 = TestOrderItemDataUtil.getOrderItem4();
        OrderItem internalOrderItem2 = TestOrderItemDataUtil.getOrderItem3();

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(internalOrderItem1);
        orderItems.add(internalOrderItem2);
        orderItem.getOrder().setOrderItems(orderItems);

        when(orderItemRepository.findById(ORDER_ITEM_ID_4)).thenReturn(Optional.of(orderItem));

        orderService.deleteOrderItem(ORDER_ITEM_ID_4);

        verify(orderRepository, times(1)).save(any(Order.class));
        verify(orderItemRepository, times(1)).delete(any(OrderItem.class));
    }


    @Test
    void deleteOrderItemWithFutureEmptyTest() {
        OrderItem orderItem = TestOrderItemDataUtil.getOrderItem4();
        OrderItem internalOrderItem = TestOrderItemDataUtil.getOrderItem4();
        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(internalOrderItem);
        orderItem.getOrder().setOrderItems(orderItems);

        Order order = TestOrderDataUtil.getOrder2();
        Set<Order> orders = new HashSet<>();
        orders.add(order);
        orderItem.getOrder().getUser().setOrders(orders);
        when(orderItemRepository.findById(ORDER_ITEM_ID_4)).thenReturn(Optional.of(orderItem));

        orderService.deleteOrderItem(ORDER_ITEM_ID_4);
        verify(userRepository, times(1)).save(any(User.class));
        verify(orderRepository, times(1)).deleteById(anyLong());
        verify(orderItemRepository, times(1)).delete(any(OrderItem.class));
    }

    @Test
    void deleteOrderTest() {
        Order order = TestOrderDataUtil.getOrder2();
        OrderItem internalOrderItem1 = TestOrderItemDataUtil.getOrderItem4();
        OrderItem internalOrderItem2 = TestOrderItemDataUtil.getOrderItem3();

        Set<OrderItem> orderItems = new HashSet<>();
        orderItems.add(internalOrderItem1);
        orderItems.add(internalOrderItem2);
        order.setOrderItems(orderItems);
        when(orderRepository.findById(ORDER_ID_2)).thenReturn(Optional.of(order));
        orderService.deleteOrder(ORDER_ID_2);
        verify(orderItemRepository, times(1)).deleteAll(orderItems);
        verify(orderRepository, times(1)).delete(any(Order.class));
    }

    @Test
    void closeOrderTest() {
        Order order = TestOrderDataUtil.getOrder2();
        Order closedOrder = TestOrderDataUtil.getOrder2()
                .setCompletedAt(LocalDate.now())
                .setClosed(true);
        when(orderRepository.findById(ORDER_ID_2)).thenReturn(Optional.of(order));
        when(orderRepository.save(order)).thenReturn(closedOrder);
        Order result = orderService.closeOrder(ORDER_ID_2);
        assertThat(result, allOf(
                hasProperty("id", equalTo(closedOrder.getId())),
                hasProperty("user", equalTo(closedOrder.getUser())),
                hasProperty("quantity", equalTo(closedOrder.getQuantity())),
                hasProperty("totalPrice", equalTo(closedOrder.getTotalPrice())),
                hasProperty("completedAt", equalTo(closedOrder.getCompletedAt()))
        ));
        verify(orderRepository, times(1)).findById(ORDER_ID_2);
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void closeOrderWithInsufficientFundsExceptionTest() {
        Order order = TestOrderDataUtil.getOrder2()
                .setTotalPrice(ORDER_TOTAL_PRICE_EXCEPTION);
        when(orderRepository.findById(ORDER_ID_2)).thenReturn(Optional.of(order));
        assertThrows(InsufficientFundsException.class, () -> orderService.closeOrder(ORDER_ID_2));

    }



}
