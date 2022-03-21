package com.nttdata.lagm.credit.kafka.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.reactive.ReactiveKafkaProducerTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReactiveProducerService {
	
	private final Logger LOGGER = LoggerFactory.getLogger(ReactiveProducerService.class);
    private final ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate;

    //@Value(value = "${kafka.topic.producer.findByDni}")
    private String topicSendDni;

    @Value(value = "${kafka.topic.producer.findById}")
    private String topicSendId;

    public ReactiveProducerService(ReactiveKafkaProducerTemplate<String, String> reactiveKafkaProducerTemplate) {
        this.reactiveKafkaProducerTemplate = reactiveKafkaProducerTemplate;
    }

    /*public void sendDni(String dni) {
        LOGGER.info("send to topic={}, {}={},", topicSendDni, String.class.getSimpleName(), dni);
        reactiveKafkaProducerTemplate.send(topicSendDni, dni)
                .doOnSuccess(senderResult -> LOGGER.info("sent {} offset : {}", dni, senderResult.recordMetadata().offset()))
                .subscribe();
    }*/

    public void sendId(String id) {
        LOGGER.info("send to topic={}, {}={},", topicSendId, String.class.getSimpleName(), id);
        reactiveKafkaProducerTemplate.send(topicSendId, id)
                .doOnSuccess(senderResult -> LOGGER.info("sent {} offset : {}", id, senderResult.recordMetadata().offset()))
                .subscribe();
    }
}
