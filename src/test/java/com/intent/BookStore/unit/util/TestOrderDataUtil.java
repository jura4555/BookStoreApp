package com.intent.BookStore.unit.util;

import com.intent.BookStore.dto.OrderDTO;
import com.intent.BookStore.model.Order;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;


public class TestOrderDataUtil {
    public static final Long ORDER_ID_1 = 1L;
    public static final int ORDER_QUANTITY_1 = 5;
    public static final BigDecimal ORDER_TOTAL_PRICE_1 = BigDecimal.valueOf(37.97);
    public static final LocalDate ORDER_COMPLETED_AT_1 = LocalDate.of(2024, Month.JANUARY, 3);
    public static final boolean ORDER_CLOSED_1 = true;
    public static final Long ORDER_ID_2 = 2L;
    public static final int ORDER_QUANTITY_2 = 1;
    public static final BigDecimal ORDER_TOTAL_PRICE_2 = BigDecimal.valueOf(40.97);
    public static final LocalDate ORDER_COMPLETED_AT_2 = null;
    public static final boolean ORDER_CLOSED_2 = false;

    public static Order getOrder1() {
        return new Order()
                .setId(ORDER_ID_1)
                .setUser(TestUserDataUtil.getUser1())
                .setQuantity(ORDER_QUANTITY_1)
                .setTotalPrice(ORDER_TOTAL_PRICE_1)
                .setCompletedAt(ORDER_COMPLETED_AT_1)
                .setClosed(ORDER_CLOSED_1);
    }

    public static Order getOrder2() {
        return new Order()
                .setId(ORDER_ID_2)
                .setUser(TestUserDataUtil.getUser2())
                .setQuantity(ORDER_QUANTITY_2)
                .setTotalPrice(ORDER_TOTAL_PRICE_2)
                .setCompletedAt(ORDER_COMPLETED_AT_2)
                .setClosed(ORDER_CLOSED_2);
    }

    public static OrderDTO getOrderDTO1() {
        return new OrderDTO()
                .setId(ORDER_ID_1)
                .setUserId(TestUserDataUtil.getUser1().getId())
                .setQuantity(ORDER_QUANTITY_1)
                .setTotalPrice(ORDER_TOTAL_PRICE_1)
                .setCompletedAt(ORDER_COMPLETED_AT_1)
                .setClosed(ORDER_CLOSED_1);
    }

    public static OrderDTO getOrderDTO2() {
        return new OrderDTO()
                .setId(ORDER_ID_2)
                .setUserId(TestUserDataUtil.getUser2().getId())
                .setQuantity(ORDER_QUANTITY_2)
                .setTotalPrice(ORDER_TOTAL_PRICE_2)
                .setCompletedAt(ORDER_COMPLETED_AT_2)
                .setClosed(ORDER_CLOSED_2);
    }


}
