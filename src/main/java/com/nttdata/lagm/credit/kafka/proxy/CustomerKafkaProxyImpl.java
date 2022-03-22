package com.nttdata.lagm.credit.kafka.proxy;

import com.nttdata.lagm.credit.kafka.config.ReactiveKafkaProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.lagm.credit.model.Customer;

import reactor.core.publisher.Mono;
import reactor.kafka.receiver.KafkaReceiver;
import reactor.kafka.sender.KafkaSender;
import reactor.kafka.sender.SenderRecord;

import java.util.Collections;

@Component
public class CustomerKafkaProxyImpl implements CustomerKafkaProxy {

	@Autowired
	private ReactiveKafkaProducerConfig reactiveKafkaProducerConfig;

	private Logger LOGGER = LoggerFactory.getLogger(CustomerKafkaProxyImpl.class);
	
	@Value("${config-eureka.base.customer.endpoint}")
	private String endpointCustomer;
	
	@Autowired
	@Qualifier("wcLoadBalanced")
	private WebClient.Builder webClientBuilder;

	@Override
	public void findById(String id) {
		return reactiveKafkaProducerConfig.sender()
				.send(Mono.just(new ProducerRecord<String, String>("customer.findById.id", id)))
				.then()
				.doOnError(e -> LOGGER.error("Send failed", e))
				.subscribe();
	}
}
