package com.nttdata.lagm.credit.kafka.proxy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import com.nttdata.lagm.credit.kafka.service.ReactiveConsumerService;
import com.nttdata.lagm.credit.kafka.service.ReactiveProducerService;
import com.nttdata.lagm.credit.model.Customer;

import reactor.core.publisher.Mono;

@Component
public class CustomerKafkaProxyImpl implements CustomerKafkaProxy {
	
	private Logger LOGGER = LoggerFactory.getLogger(CustomerKafkaProxyImpl.class);
	
	@Value("${config-eureka.base.customer.endpoint}")
	private String endpointCustomer;
	
	@Autowired
	@Qualifier("wcLoadBalanced")
	private WebClient.Builder webClientBuilder;

	@Autowired
	private ReactiveConsumerService reactiveConsumerService;

	@Autowired
	private ReactiveProducerService reactiveProducerService;
	
	@Override
	public Mono<Customer> findById(String id) {
		return reactiveConsumerService.consumeCustomerId();
	}

	@Override
	public void sendId(String id) {
		LOGGER.info("{} - findById: id={}", this.getClass().getSimpleName(), id);
		reactiveProducerService.sendId(id);
	}

	/*@Override
	public Mono<Customer> findByDni(String dni) {
		reactiveProducerService.sendDni(dni);
		return reactiveConsumerService.consumeCustomerDni();
	}*/

}
