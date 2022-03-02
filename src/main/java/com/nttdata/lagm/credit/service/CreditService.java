package com.nttdata.lagm.credit.service;

import com.nttdata.lagm.credit.model.Credit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {

	void create(Credit credit);
	Flux<Credit> findAll();
	Mono<Credit> findById(Long id);
	Mono<Credit> update(Credit credit);
	Mono<Void> delete(Long id);
}
