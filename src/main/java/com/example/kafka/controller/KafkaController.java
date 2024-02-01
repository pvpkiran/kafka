package com.example.kafka.controller;

import com.example.kafka.model.Customer;
import com.example.kafka.model.Product;
import com.example.kafka.service.ProducerService;

import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/kafka")
public class KafkaController {

    private final ProducerService producerService;

    public KafkaController(ProducerService producerService) {
        this.producerService = producerService;
    }

    @PostMapping("/customer")
    public void sendCustomer(@RequestBody Customer customer) {
        producerService.sendCustomer(customer);
    }

    @PostMapping
    public void sendCustomer(@RequestParam("message") String message) {
        producerService.sendMessage(message);
    }

    @PostMapping("/product/{partition}")
    public void sendProduct(@RequestBody Product product, @PathVariable int partition) {
        Message<Product> message = MessageBuilder.withPayload(product)
            .setHeader(KafkaHeaders.PARTITION, partition)
            //.setHeader(KafkaHeaders.KEY, "123") If partition and key both are specified partition has precedence over key
            .setHeader(KafkaHeaders.TOPIC, "product")
            .build();
        producerService.sendProduct(message);
    }
}
