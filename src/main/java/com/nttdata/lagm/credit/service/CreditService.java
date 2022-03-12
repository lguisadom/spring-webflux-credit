package com.nttdata.lagm.credit.service;

import com.nttdata.lagm.credit.dto.response.AvailableBalanceResponseDto;
import com.nttdata.lagm.credit.model.Credit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {

	Mono<Credit> create(Credit credit);
	Flux<Credit> findAll();
	Mono<Credit> findById(String id);
	Mono<Credit> delete(String id);
	public Mono<Credit> findByAccountNumber(String accountNumber);
	public Mono<Credit> updateAmount(String id, String amount);
	public Mono<AvailableBalanceResponseDto> getAvailableBalance(String accountNumber);
}
