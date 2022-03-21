package com.nttdata.lagm.credit.kafka.service;

import com.nttdata.lagm.credit.dto.request.CreditRequestDto;
import com.nttdata.lagm.credit.model.Credit;

import reactor.core.publisher.Mono;

public interface CreditServiceKafka {
	Mono<Credit> create(CreditRequestDto creditRequestDto);
}
