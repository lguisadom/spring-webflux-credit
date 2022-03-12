package com.nttdata.lagm.credit.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.lagm.credit.dto.response.AvailableBalanceResponseDto;
import com.nttdata.lagm.credit.model.Credit;
import com.nttdata.lagm.credit.proxy.CustomerProxy;
import com.nttdata.lagm.credit.repository.CreditRepository;
import com.nttdata.lagm.credit.util.Constants;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CreditServiceImpl implements CreditService {

	private Logger LOGGER = LoggerFactory.getLogger(CreditServiceImpl.class);
	
	@Autowired
	private CustomerProxy customerProxy;
	
	@Autowired
	private CreditRepository creditRepository;

	@Override
	public Mono<Credit> create(Credit credit) {
		return checkCustomerExist(credit.getCustomerId())
				.mergeWith(checkAccountNumberNotExists(credit.getAccountNumber()))
				.mergeWith(checkBusinessRuleForCredit(credit.getCustomerId()))
				.then(creditRepository.save(credit));
	}

	@Override
	public Flux<Credit> findAll() {
		return creditRepository.findAll();
	}

	@Override
	public Mono<Credit> findById(String id) {
		return creditRepository.findById(id);
	}

	@Override
	public Mono<Credit> delete(String id) {
		return creditRepository.findById(id)
				.flatMap(credit -> {
					credit.setStatus(false);
					return creditRepository.save(credit);
				});
	}

	private Mono<Void> checkCustomerExist(Long id) {
		return customerProxy.findById(id)
				.switchIfEmpty(Mono.error(new Exception("No existe cliente con id: " + id)))
				.then();

	}
	
	private Mono<Void> checkAccountNumberNotExists(String accountNumber) {
		return creditRepository.findByAccountNumber(accountNumber)
				.flatMap(bankAccount -> {
					return Mono.error(new Exception("Crédito con número de cuenta: " + accountNumber + " ya existe"));
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

	@Override
	public Mono<Credit> findByAccountNumber(String accountNumber) {
		return creditRepository.findByAccountNumber(accountNumber);
	}
	
	@Override
	public Mono<Credit> updateAmount(String id, String strAmount) {
		return creditRepository.findById(id)
				.switchIfEmpty(Mono.error(new Exception("Crédito con id: " + id + " no existe")))
				.flatMap(credit -> {
					BigDecimal currentAmount = new BigDecimal(credit.getAmount());
					BigDecimal amount = new BigDecimal(strAmount);
					BigDecimal finalAmount = currentAmount.add(amount);
					credit.setAmount(finalAmount.toString());
					LOGGER.info("current " + currentAmount + " -> final: " + finalAmount);
					return creditRepository.save(credit);
				});
	}

	private Mono<Void> checkAccountNumberExists(String accountNumber) {
		return creditRepository.findByAccountNumber(accountNumber)
				.switchIfEmpty(Mono.error(new Exception("Crédito con número de cuenta: " + accountNumber + " no existe")))
				.then();
	}
	
	@Override
	public Mono<AvailableBalanceResponseDto> getAvailableBalance(String accountNumber) {
		return checkAccountNumberExists(accountNumber)
				.then(creditRepository.findByAccountNumber(accountNumber).map(credit -> {
					return new AvailableBalanceResponseDto(credit.getAccountNumber(), credit.getAmount().toString());
				}));
	}
}