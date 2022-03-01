package com.nttdata.lagm.service;



import org.springframework.stereotype.Service;
import com.nttdata.lagm.model.credit;
import com.nttdata.lagm.repository.*;



import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
@RequiredArgsConstructor
@Service

public class  ServiceCreditlmpl implements CreditService{
	
	private final CreditRepository repository;

	@Override
	public Flux<credit> findAll() {
		// TODO Auto-generated method stub
		return repository.findAll();
	}

	@Override
	public Mono<credit> save(credit retreat) {
		// TODO Auto-generated method stub
		return repository.save(retreat);
	}

	@Override
	public Mono<credit> findRetreatById(String id) {
		// TODO Auto-generated method stub
		return repository.findById(id);
	}

	@Override
	public Mono<credit> update(credit product) {
		// TODO Auto-generated method stub
		return repository.save(product);
	}

	@Override
	public void deleteById(String id) {
		// TODO Auto-generated method stub
		repository.deleteById(id).subscribe();
		
	}

	

}