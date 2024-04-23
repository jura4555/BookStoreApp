package com.intent.BookStore.controller;

import com.intent.BookStore.dto.OrderDTO;
import com.intent.BookStore.dto.OrderItemDTO;
import com.intent.BookStore.facade.OrderFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        OrderDTO order = orderFacade.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }

    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> createOrderItem(
            Authentication authentication,
            @Valid @RequestBody OrderItemDTO orderItemDTO) {
        OrderDTO orderDTO = orderFacade.createOrderItem(authentication, orderItemDTO);
        return new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
    }

    @DeleteMapping("/orders/item/{id}")
    public ResponseEntity<Void> deleteOrderItem(Authentication authentication, @PathVariable Long id) {
        orderFacade.deleteOrderItem(authentication, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/orders/{id}")
    public ResponseEntity<Void> deleteOrder(Authentication authentication, @PathVariable Long id) {
        orderFacade.deleteOrder(authentication, id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping("/orders/{id}/close")
    public ResponseEntity<OrderDTO> closeOrder(Authentication authentication, @PathVariable Long id) {
        OrderDTO closedOrder = orderFacade.closeOrder(authentication, id);
        return ResponseEntity.ok(closedOrder);
    }

}
