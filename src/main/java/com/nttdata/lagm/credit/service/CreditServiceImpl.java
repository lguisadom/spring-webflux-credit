package com.nttdata.lagm.credit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.lagm.credit.model.Credit;
import com.nttdata.lagm.credit.repository.CreditRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements CreditService {
	@Autowired
	private CreditRepository creditRepository;

	@Override
	public void create(Credit credit) {
		creditRepository.save(credit).subscribe();
	}

	@Override
	public Flux<Credit> findAll() {
		return creditRepository.findAll();
	}

	@Override
	public Mono<Credit> findById(Long id) {
		return creditRepository.findById(id);
	}

	@Override
	public Mono<Credit> update(Credit credit) {
		return creditRepository.save(credit);
	}

	@Override
	public Mono<Void> delete(Long id) {
		return creditRepository.deleteById(id);
	}

}