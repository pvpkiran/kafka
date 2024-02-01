package com.example.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.adapter.RecordFilterStrategy;
import org.springframework.stereotype.Component;

import com.example.kafka.model.Customer;

@Component
public class CustomerFilter implements RecordFilterStrategy<String, Customer> {
    @Override
    public boolean filter(ConsumerRecord<String, Customer> consumerRecord) {
        return consumerRecord.value().getId() < 500;
    }
}
