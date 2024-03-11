package com.intent.BookStore.unit.controller;

import com.intent.BookStore.controller.OrderController;
import com.intent.BookStore.dto.OrderDTO;
import com.intent.BookStore.dto.OrderItemDTO;
import com.intent.BookStore.facade.impl.OrderFacadeImpl;
import com.intent.BookStore.unit.util.TestOrderDataUtil;
import com.intent.BookStore.unit.util.TestOrderItemDataUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.intent.BookStore.unit.util.TestBookDataUtil.BOOK_ID_1;
import static com.intent.BookStore.unit.util.TestOrderDataUtil.ORDER_ID_1;
import static com.intent.BookStore.unit.util.TestOrderDataUtil.ORDER_ID_2;
import static com.intent.BookStore.unit.util.TestOrderItemDataUtil.ORDER_ITEM_ID_1;
import static com.intent.BookStore.unit.util.TestUserDataUtil.USER_ID_2;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderControllerTest {

    @Mock
    private OrderFacadeImpl orderFacadeImpl;

    @InjectMocks
    private OrderController orderController;


    @Test
    void createOrderItemTest() {
        OrderItemDTO orderItemDTO = TestOrderItemDataUtil.getOrderItemDTO4()
                .setId(0L)
                .setOrderId(0L)
                .setTotalPrice(null);
        OrderDTO orderDTO = TestOrderDataUtil.getOrderDTO2();
        ResponseEntity<OrderDTO> expectedResponseEntity = ResponseEntity.status(HttpStatus.CREATED).body(orderDTO);

        when(orderFacadeImpl.createOrderItem(orderItemDTO, USER_ID_2)).thenReturn(orderDTO);

        ResponseEntity<OrderDTO> resultResponseEntity = orderController.createOrderItem(USER_ID_2, orderItemDTO);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(orderFacadeImpl).createOrderItem(orderItemDTO, USER_ID_2);
    }

    @Test
    void getOrderByIdTest() {
        OrderDTO orderDTO = TestOrderDataUtil.getOrderDTO1();
        ResponseEntity<OrderDTO> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK).body(orderDTO);

        when(orderFacadeImpl.getOrderById(ORDER_ID_1)).thenReturn(orderDTO);

        ResponseEntity<OrderDTO> resultResponseEntity = orderController.getUserById(ORDER_ID_1);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(orderFacadeImpl).getOrderById(ORDER_ID_1);
    }

    @Test
    void deleteOrderItemTest() {
        ResponseEntity<Void> resultResponseEntity = orderController.deleteOrderItem(ORDER_ITEM_ID_1);

        assertThat(resultResponseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
        verify(orderFacadeImpl, times(1)).deleteOrderItem(ORDER_ITEM_ID_1);
    }

    @Test
    void deleteOrderTest() {

        ResponseEntity<Void> resultResponseEntity = orderController.deleteOrder(ORDER_ID_1);

        assertThat(resultResponseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
        verify(orderFacadeImpl, times(1)).deleteOrder(ORDER_ID_1);
    }

    @Test
    void closeOrderTest() {
        OrderDTO orderDTO = TestOrderDataUtil.getOrderDTO1();
        ResponseEntity<OrderDTO> expectedResponseEntity = ResponseEntity.status(HttpStatus.OK).body(orderDTO);

        when(orderFacadeImpl.closeOrder(ORDER_ID_1)).thenReturn(orderDTO);

        ResponseEntity<OrderDTO> resultResponseEntity = orderController.closeOrder(ORDER_ID_1);

        assertThat(resultResponseEntity.getBody(), is(expectedResponseEntity.getBody()));
        assertThat(resultResponseEntity.getStatusCode(), is(expectedResponseEntity.getStatusCode()));
        verify(orderFacadeImpl, times(1)).closeOrder(ORDER_ID_1);
    }





}
