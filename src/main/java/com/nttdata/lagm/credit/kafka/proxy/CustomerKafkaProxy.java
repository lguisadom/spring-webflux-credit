package com.nttdata.lagm.credit.kafka.proxy;

import com.nttdata.lagm.credit.model.Customer;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerKafkaProxy {
	void sendId(String id);
	Mono<Customer> findById(String id);
	//Mono<Customer> findByDni(String dni);
}
