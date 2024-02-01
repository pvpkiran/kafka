package com.example.kafka.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.AbstractKafkaListenerContainerFactory;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
public class KafkaConfig {

    @Bean
    KafkaListenerErrorHandler handleMessageError() {
        return (msg, ex) -> {
            Integer count = msg.getHeaders().get(KafkaHeaders.DELIVERY_ATTEMPT, Integer.class);
            System.out.println("DeliveryAttempt : " + count);
            if (count > 1) {
                return "FAILED";
            }
            throw ex;
        };
    }

    //Global Error Handler
    @Bean
    public AbstractKafkaListenerContainerFactory<?, ?, ?> configureContainerProperties(AbstractKafkaListenerContainerFactory<?, ?, ?> factory) {
        factory.getContainerProperties().setDeliveryAttemptHeader(true);
        factory.setCommonErrorHandler(new DefaultErrorHandler((record, exception) -> {
            //code to be executed when all retries are exhausted
            System.out.println("All retries exhausted");
        }, new FixedBackOff(5000L, 2L)));
        return factory;
    }
}
