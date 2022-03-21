package com.nttdata.lagm.credit.kafka.config;

import java.util.Collections;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;

import com.nttdata.lagm.credit.model.Customer;

import reactor.kafka.receiver.ReceiverOptions;

@Configuration
public class ReactiveKafkaConsumerConfig {
    @Bean
    public ReceiverOptions<String, Customer> kafkaReceiverOptions(@Value(value = "${kafka.topic.consumer.findById}") String topic, KafkaProperties kafkaProperties) {
        ReceiverOptions<String, Customer> basicReceiverOptions = ReceiverOptions.create(kafkaProperties.buildConsumerProperties());
        return basicReceiverOptions.subscription(Collections.singletonList(topic));
    }

    @Bean
    public ReactiveKafkaConsumerTemplate<String, Customer> reactiveKafkaConsumerTemplate(ReceiverOptions<String, Customer> kafkaReceiverOptions) {
        return new ReactiveKafkaConsumerTemplate<String, Customer>(kafkaReceiverOptions);
    }
}