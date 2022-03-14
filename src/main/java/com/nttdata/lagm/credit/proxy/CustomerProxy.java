package com.nttdata.lagm.credit.proxy;

import com.nttdata.lagm.credit.model.Customer;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CustomerProxy {
	Flux<Customer> findAll();
	Mono<Customer> findById(String id);
	Mono<Customer> findByDni(String dni);
}
