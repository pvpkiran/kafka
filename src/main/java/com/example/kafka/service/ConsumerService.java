package com.example.kafka.service;

import com.example.kafka.model.Customer;
import com.example.kafka.model.Product;

import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    // Change group id  and offset property and see how messages are consumed
    @KafkaListener(topics = "customer", groupId = "2", properties = {"auto.offset.reset = earliest"}, filter = "customerFilter")
    public void readCustomers(
        @Payload Customer customer) {
        System.out.println(
            "Received Customer: " + customer);
    }

    @KafkaListener(topics = "message", groupId = "1" , errorHandler = "handleMessageError")
    @RetryableTopic(attempts = "3", backoff = @Backoff(value = 3000L))
    public void readMessages(
        @Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
        @Payload String message) {
        System.out.println(
            "Received Message: " + message + " from partition " + partition);
        throw new RuntimeException("error handler test");
    }

    @DltHandler
    public void processDltMessages(String message,
                                   @Header(KafkaHeaders.EXCEPTION_STACKTRACE) String stacktrace,
                                   @Header(KafkaHeaders.EXCEPTION_MESSAGE) String errorMessage) {
        System.out.println("Processing from DLQ : " + message);
    }

    @KafkaListener(groupId = "2", topicPartitions = { @TopicPartition(topic = "product", partitions = {"0", "1"})})
    public void readProducts(@Header(KafkaHeaders.RECEIVED_PARTITION) int partition,
    @Payload Product product) {
        System.out.println(
            "Received product: " + product + " from partition " + partition);
    }
}
