package com.intent.BookStore.controller;

import com.intent.BookStore.dto.OrderDTO;
import com.intent.BookStore.facade.OrderFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderFacade orderFacade;

    @GetMapping("/orders/{id}")
    public ResponseEntity<OrderDTO> getUserById(@PathVariable Long id) {
        OrderDTO order = orderFacade.getOrderById(id);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
}
