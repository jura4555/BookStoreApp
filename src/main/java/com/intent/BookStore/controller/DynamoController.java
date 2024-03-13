package com.intent.BookStore.controller;

import com.intent.BookStore.aws.DynamoClient;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class DynamoController {
    private final DynamoClient dynamoClient;

    @PostMapping("/dynamo")
    private void storeMessage(@RequestBody String message) {
        dynamoClient.storeMessage(message);
    }
}
