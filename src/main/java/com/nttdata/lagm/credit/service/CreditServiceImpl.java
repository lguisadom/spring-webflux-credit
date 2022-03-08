package com.nttdata.lagm.credit.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.lagm.credit.model.Credit;
import com.nttdata.lagm.credit.proxy.CustomerProxy;
import com.nttdata.lagm.credit.repository.CreditRepository;
import com.nttdata.lagm.credit.util.Constants;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements CreditService {
	
	@Autowired
	private CustomerProxy customerProxy;
	
	@Autowired
	private CreditRepository creditRepository;

	@Override
	public Mono<Credit> create(Credit credit) {
		return checkCustomerExist(credit.getCustomerId())
				.mergeWith(checkCreditNotExists(credit.getId()))
				.mergeWith(checkBusinessRuleForCredit(credit.getCustomerId()))
				.then(creditRepository.save(credit));
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

	private Mono<Void> checkCustomerExist(Long id) {
		return customerProxy.findById(id)
				.switchIfEmpty(Mono.error(new Exception("No existe cliente con id: " + id)))
				.then();

	}
	
	private Mono<Void> checkCreditNotExists(Long id) {
		return creditRepository.findById(id)
				.flatMap(bankAccount -> {
					return Mono.error(new Exception("Crédito con con id: " + id + " ya existe"));
				})
				.then();
	}
	
	private Mono<Void> checkBusinessRuleForCredit(Long customerId) {
		return this.customerProxy.findById(customerId)
				.flatMap(customer -> {
					if (Constants.PERSONAL_CUSTOMER == customer.getCustomerTypeId()) {
						return this.findAllCreditsByCustomerId(customerId)
								.flatMap(result -> {
									return Mono.error(
											new Exception("El cliente: " + result.getCustomerId() + " es de tipo " + 
									Constants.PERSONAL_CUSTOMER + " y no puede tener más de un crédito"
									));
								})
								.then();
					} else {
						return Mono.empty();
					}
				});
	}
	
	private Flux<Credit> findAllCreditsByCustomerId(Long customerId) {
		return creditRepository.findAll().filter(
				credit -> credit.getCustomerId() == customerId);
	}
}