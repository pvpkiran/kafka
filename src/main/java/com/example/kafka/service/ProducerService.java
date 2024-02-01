package com.example.kafka.service;

import com.example.kafka.model.Customer;
import com.example.kafka.model.Product;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Service;

@Service
public class ProducerService {

    private final KafkaTemplate<String, Customer> kafkaCustomerTemplate;
    private final KafkaTemplate<String, Product> kafkaProductTemplate;
    private final KafkaTemplate<String, String> kafkaMessageTemplate;

    public ProducerService(KafkaTemplate<String, Customer> kafkaCustomerTemplate,
                           KafkaTemplate<String, Product> kafkaProductTemplate,
                           KafkaTemplate<String, String> kafkaMessageTemplate) {
        this.kafkaCustomerTemplate = kafkaCustomerTemplate;
        this.kafkaProductTemplate = kafkaProductTemplate;
        this.kafkaMessageTemplate = kafkaMessageTemplate;
    }

    public void sendCustomer(Customer customer) {
        kafkaCustomerTemplate.send("customer", customer);
    }

    public void sendMessage(String message) {
        kafkaMessageTemplate.send("message", message);
    }

    public void sendProduct(Message<Product> product) {
        kafkaProductTemplate.send(product);
    }
}
