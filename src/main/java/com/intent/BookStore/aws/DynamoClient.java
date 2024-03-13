package com.intent.BookStore.aws;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
@RequiredArgsConstructor
public class DynamoClient {

    private final DynamoDbClient dynamoDbClient;

    private static final String TABLE_NAME = "BookStoreDBTable";

    public void storeMessage(String message) {
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("uuid", AttributeValue.fromS(UUID.randomUUID().toString()));
        item.put("messages", AttributeValue.fromS(message));

        PutItemRequest putItemRequest = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(item)
                .build();

        dynamoDbClient.putItem(putItemRequest);
    }

    public void logRequest(String path, Map<String, String> parameters, String body, String method) {
        Map<String, AttributeValue> parametersAttribute = new HashMap<>();
        if (parameters != null) {
            parameters.forEach((key, value) -> parametersAttribute.put(key, AttributeValue.builder().s(value).build()));
        }
        Map<String, AttributeValue> item = new HashMap<>();
        item.put("uuid", AttributeValue.fromS(UUID.randomUUID().toString()));
        item.put("path", AttributeValue.builder().s(path).build());
        item.put("parameters", AttributeValue.builder().m(parametersAttribute).build());
        item.put("body", AttributeValue.builder().s(body).build());
        item.put("method", AttributeValue.builder().s(method).build());
        item.put("time", AttributeValue.builder().s(LocalDateTime.now().toString()).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName(TABLE_NAME)
                .item(item)
                .build();

        dynamoDbClient.putItem(request);
    }
}
