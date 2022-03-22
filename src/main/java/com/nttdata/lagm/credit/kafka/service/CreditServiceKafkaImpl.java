package com.nttdata.lagm.credit.kafka.service;

import com.nttdata.lagm.credit.dto.request.CreditRequestDto;
import com.nttdata.lagm.credit.dto.response.AvailableBalanceResponseDto;
import com.nttdata.lagm.credit.kafka.proxy.CustomerKafkaProxy;
import com.nttdata.lagm.credit.model.Credit;
import com.nttdata.lagm.credit.proxy.CustomerProxy;
import com.nttdata.lagm.credit.repository.CreditRepository;
import com.nttdata.lagm.credit.service.CreditService;
import com.nttdata.lagm.credit.service.CreditServiceImpl;
import com.nttdata.lagm.credit.util.Constants;
import com.nttdata.lagm.credit.util.Converter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.math.BigDecimal;

@Service
public class CreditServiceKafkaImpl implements CreditServiceKafka {
	private Logger LOGGER = LoggerFactory.getLogger(CreditServiceImpl.class);

	@Autowired
	private CreditRepository creditRepository;

	@Autowired
	private CustomerKafkaProxy customerKafkaProxy;

	@Autowired
	private ReactiveConsumerService reactiveConsumerService;

	@Autowired
	private ReactiveProducerService reactiveProducerService;

	@Override
	public Mono<Credit> create(CreditRequestDto creditRequestDto) {
		Credit credit = Converter.convertoToCredit(creditRequestDto);
		return checkConditions(credit)
				.then(creditRepository.save(credit));
	}

	private Mono<Void> checkConditions(Credit credit) {
		return checkCustomerExist(credit)
				.mergeWith(checkAccountNumberNotExists(credit.getAccountNumber()))
				.mergeWith(checkBusinessRuleForCredit(credit.getCustomer().getId()))
				.then();
	}

	private Mono<Void> checkCustomerExist(Credit credit) {
		String customerId = credit.getCustomer().getId();
		return customerKafkaProxy.findById(customerId)
				.switchIfEmpty(Mono.error(new Exception("No existe cliente con id: " + customerId)))
				.flatMap(customer -> {
					credit.setCustomer(customer);
					return Mono.empty();
				});
	}

	private Mono<Void> checkAccountNumberNotExists(String accountNumber) {
		return creditRepository.findByAccountNumber(accountNumber)
				.flatMap(bankAccount -> {
					return Mono.error(new Exception("Crédito con número de cuenta: " + accountNumber + " ya existe"));
				})
				.then();
	}

	private Mono<Void> checkBusinessRuleForCredit(String customerId) {
		return this.customerKafkaProxy.findById(customerId)
				.flatMap(customer -> {
					if (Constants.CUSTOMER_TYPE_PERSONAL == customer.getCustomerTypeId()) {
						return this.findAllCreditsByCustomerId(customerId)
								.flatMap(result -> {
									return Mono.error(
											new Exception("El cliente: " + result.getCustomer().getId() + " es de tipo " +
													Constants.CUSTOMER_TYPE_PERSONAL_DESCRIPTION + " y no puede tener más de un crédito"
											));
								})
								.then();
					} else {
						return Mono.empty();
					}
				});
	}

	private Flux<Credit> findAllCreditsByCustomerId(String customerId) {
		return creditRepository.findAll().filter(
				credit -> credit.getCustomer().getId().equals(customerId));
	}

	private Mono<Void> checkAccountNumberExists(String accountNumber) {
		return creditRepository.findByAccountNumber(accountNumber)
				.switchIfEmpty(Mono.error(new Exception("Crédito con número de cuenta: " + accountNumber + " no existe")))
				.then();
	}
}
