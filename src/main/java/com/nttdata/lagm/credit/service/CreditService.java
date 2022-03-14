package com.nttdata.lagm.credit.service;

import com.nttdata.lagm.credit.dto.request.CreditRequestDto;
import com.nttdata.lagm.credit.dto.response.AvailableBalanceResponseDto;
import com.nttdata.lagm.credit.model.Credit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {

	Mono<Credit> create(CreditRequestDto creditRequestDto);
	Flux<Credit> findAll();
	Mono<Credit> findById(String id);
	Mono<Credit> delete(String id);
	Mono<Credit> findByAccountNumber(String accountNumber);
	Mono<Credit> updateAmount(String id, String amount);
	Mono<AvailableBalanceResponseDto> getAvailableBalance(String accountNumber);
	Flux<Credit> findByDni(String dni);
}
