package com.intent.BookStore.controller;

import com.intent.BookStore.dto.OrderDTO;
import com.intent.BookStore.dto.OrderItemDTO;
import com.intent.BookStore.facade.OrderFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getUserById(@PathVariable Long id) {
        OrderDTO order = orderFacade.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrderItem(
            @RequestParam(required = false, defaultValue = "0") long userId,
            @Valid @RequestBody OrderItemDTO orderItemDTO) {
        OrderDTO orderDTO = orderFacade.createOrderItem(orderItemDTO, userId);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/orders/item/{id}")
    public ResponseEntity<Void> deleteOrderItem(@PathVariable Long id) {
        orderFacade.deleteOrderItem(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
        orderFacade.deleteOrder(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/orders/{id}/close")
    public ResponseEntity<OrderDTO> closeOrder(@PathVariable Long id) {
        OrderDTO closedOrder = orderFacade.closeOrder(id);
        return ResponseEntity.ok(closedOrder);
    }

}
