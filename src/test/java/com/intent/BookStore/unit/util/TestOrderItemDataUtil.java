package com.intent.BookStore.unit.util;

import com.intent.BookStore.dto.OrderItemDTO;
import com.intent.BookStore.model.OrderItem;

import java.math.BigDecimal;

public class TestOrderItemDataUtil {

    public static final Long ORDER_ITEM_ID_1 = 1L;
    public static final BigDecimal ORDER_ITEM_TOTAL_PRICE_1 = BigDecimal.valueOf(21.98);
    public static final int ORDER_ITEM_QUANTITY_1 = 2;

    public static final Long ORDER_ITEM_ID_2 = 2L;
    public static final BigDecimal ORDER_ITEM_TOTAL_PRICE_2 = BigDecimal.valueOf(12.99);
    public static final int ORDER_ITEM_QUANTITY_2 = 1;

    public static final Long ORDER_ITEM_ID_3 = 3L;
    public static final BigDecimal ORDER_ITEM_TOTAL_PRICE_3 = BigDecimal.valueOf(29.98);
    public static final int ORDER_ITEM_QUANTITY_3 = 2;

    public static final Long ORDER_ITEM_ID_4 = 4L;
    public static final BigDecimal ORDER_ITEM_TOTAL_PRICE_4 = BigDecimal.valueOf(10.99);
    public static final int ORDER_ITEM_QUANTITY_4 = 1;


    public static OrderItem getOrderItem1() {
        return new OrderItem()
                .setId(ORDER_ITEM_ID_1)
                .setOrder(TestOrderDataUtil.getOrder1())
                .setBook(TestBookDataUtil.getBook1())
                .setTotalPrice(ORDER_ITEM_TOTAL_PRICE_1)
                .setQuantity(ORDER_ITEM_QUANTITY_1);
    }

    public static OrderItem getOrderItem2() {
        return new OrderItem()
                .setId(ORDER_ITEM_ID_2)
                .setOrder(TestOrderDataUtil.getOrder1())
                .setBook(TestBookDataUtil.getBook2())
                .setTotalPrice(ORDER_ITEM_TOTAL_PRICE_2)
                .setQuantity(ORDER_ITEM_QUANTITY_2);
    }

    public static OrderItem getOrderItem3() {
        return new OrderItem()
                .setId(ORDER_ITEM_ID_3)
                .setOrder(TestOrderDataUtil.getOrder2())
                .setBook(TestBookDataUtil.getBook3())
                .setTotalPrice(ORDER_ITEM_TOTAL_PRICE_3)
                .setQuantity(ORDER_ITEM_QUANTITY_3);
    }

    public static OrderItem getOrderItem4() {
        return new OrderItem()
                .setId(ORDER_ITEM_ID_4)
                .setOrder(TestOrderDataUtil.getOrder2())
                .setBook(TestBookDataUtil.getBook1())
                .setTotalPrice(ORDER_ITEM_TOTAL_PRICE_4)
                .setQuantity(ORDER_ITEM_QUANTITY_4);
    }

    public static OrderItemDTO getOrderItemDTO1() {
        return new OrderItemDTO()
                .setId(ORDER_ITEM_ID_1)
                .setOrderId(TestOrderDataUtil.getOrder1().getId())
                .setBookId(TestBookDataUtil.getBook1().getId())
                .setTotalPrice(ORDER_ITEM_TOTAL_PRICE_1)
                .setQuantity(ORDER_ITEM_QUANTITY_1);
    }

    public static OrderItemDTO getOrderItemDTO2() {
        return new OrderItemDTO()
                .setId(ORDER_ITEM_ID_2)
                .setOrderId(TestOrderDataUtil.getOrder1().getId())
                .setBookId(TestBookDataUtil.getBook2().getId())
                .setTotalPrice(ORDER_ITEM_TOTAL_PRICE_2)
                .setQuantity(ORDER_ITEM_QUANTITY_2);
    }

    public static OrderItemDTO getOrderItemDTO3() {
        return new OrderItemDTO()
                .setId(ORDER_ITEM_ID_3)
                .setOrderId(TestOrderDataUtil.getOrder2().getId())
                .setBookId(TestBookDataUtil.getBook3().getId())
                .setTotalPrice(ORDER_ITEM_TOTAL_PRICE_3)
                .setQuantity(ORDER_ITEM_QUANTITY_3);
    }

    public static OrderItemDTO getOrderItemDTO4() {
        return new OrderItemDTO()
                .setId(ORDER_ITEM_ID_4)
                .setOrderId(TestOrderDataUtil.getOrder2().getId())
                .setBookId(TestBookDataUtil.getBook1().getId())
                .setTotalPrice(ORDER_ITEM_TOTAL_PRICE_4)
                .setQuantity(ORDER_ITEM_QUANTITY_4);
    }
}
