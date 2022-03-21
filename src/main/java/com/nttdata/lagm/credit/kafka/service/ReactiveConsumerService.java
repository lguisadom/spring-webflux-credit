package com.nttdata.lagm.credit.kafka.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.reactive.ReactiveKafkaConsumerTemplate;
import org.springframework.stereotype.Service;

import com.nttdata.lagm.credit.model.Customer;

import reactor.core.publisher.Mono;

@Service
public class ReactiveConsumerService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ReactiveConsumerService.class);

    //private final ReactiveKafkaConsumerTemplate<String, Customer> reactiveKafkaConsumerTemplateDni;
    private final ReactiveKafkaConsumerTemplate<String, Customer> reactiveKafkaConsumerTemplate;

    public ReactiveConsumerService(ReactiveKafkaConsumerTemplate<String, Customer> reactiveKafkaConsumerTemplate) {
        //this.reactiveKafkaConsumerTemplateDni = reactiveKafkaConsumerTemplateDni;
        this.reactiveKafkaConsumerTemplate = reactiveKafkaConsumerTemplate;
    }

//    public Mono<Customer> consumeCustomerDni() {
//        return reactiveKafkaConsumerTemplateDni
//                .receiveAutoAck()
//                // .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
//                .doOnNext(consumerRecord -> LOGGER.info("received key={}, value={} from topic={}, offset={}",
//                        consumerRecord.key(),
//                        consumerRecord.value(),
//                        consumerRecord.topic(),
//                        consumerRecord.offset())
//                )
//                .map(ConsumerRecord::value)
//                .doOnNext(customer -> LOGGER.info("successfully consumed {}={}", "dni", customer))
//                .doOnError(throwable -> LOGGER.error("something bad happened while consuming : {}", throwable.getMessage()))
//                .next();
//    }

    public Mono<Customer> consumeCustomerId() {
        return reactiveKafkaConsumerTemplate
                .receiveAutoAck()
                // .delayElements(Duration.ofSeconds(2L)) // BACKPRESSURE
                .doOnNext(consumerRecord -> LOGGER.info("received key={}, value={} from topic={}, offset={}",
                        consumerRecord.key(),
                        consumerRecord.value(),
                        consumerRecord.topic(),
                        consumerRecord.offset())
                )
                .map(ConsumerRecord::value)
                .doOnNext(customer -> LOGGER.info("successfully consumed {}={}", "customer", customer))
                .doOnError(throwable -> LOGGER.error("something bad happened while consuming : {}", throwable.getMessage()))
                .next();
    }
}
