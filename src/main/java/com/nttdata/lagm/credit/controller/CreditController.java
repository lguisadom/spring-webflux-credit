package com.nttdata.lagm.credit.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.lagm.credit.config.AppConfig;
import com.nttdata.lagm.credit.dto.request.CreditRequestDto;
import com.nttdata.lagm.credit.dto.response.AvailableBalanceResponseDto;
import com.nttdata.lagm.credit.model.Credit;
import com.nttdata.lagm.credit.service.CreditService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/credit")
public class CreditController {
	
	private Logger LOGGER = LoggerFactory.getLogger(CreditController.class);
	
	@Autowired
	private CreditService creditService;
	
	@Autowired
	private AppConfig appConfig;
	
	@GetMapping(value= "/properties", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	private String getProperties() {
		return String.format("ServerPort: %s\nProfile Description: %s\nMondo db: %s\n",
				appConfig.getPort(),
				appConfig.getProfileDescription(),
				appConfig.getMongoDatabase());
	}
	
	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	private Mono<Credit> create(@RequestBody CreditRequestDto creditRequestDto) {
		return creditService.create(creditRequestDto);
	}
	
	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(HttpStatus.OK)
	private Flux<Credit> findAll() {
		return creditService.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	private Mono<Credit> findById(@PathVariable("id") String id) {
		return creditService.findById(id);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	private Mono<Credit> delete(@PathVariable("id") String id) {
		return creditService.delete(id);
	}
	
	@PutMapping("/update/{id}/amount/{amount}")
	@ResponseStatus(HttpStatus.OK)
	private Mono<Credit> updateAmount(@PathVariable String id, @PathVariable String amount) {
		LOGGER.info("UpdateAmount: " + id + ", amount: " + amount);
		return creditService.updateAmount(id, amount);
	}
	
	@GetMapping("accountNumber/{accountNumber}")
	@ResponseStatus(HttpStatus.OK)
	private Mono<Credit> findByAccountNumber(@PathVariable String accountNumber) {
		LOGGER.info("FindByAccountNumber: " + accountNumber);
		return creditService.findByAccountNumber(accountNumber);
	}

	@GetMapping("/balance/{accountNumber}")
	@ResponseStatus(HttpStatus.OK)
	private Mono<AvailableBalanceResponseDto> getAvailableBalance(@PathVariable("accountNumber") String accountNumber) {
		LOGGER.info("GetAvailableBalance: " + accountNumber);
		return creditService.getAvailableBalance(accountNumber);
	}

	@GetMapping("/dni/{dni}")
	@ResponseStatus(HttpStatus.OK)
	private Flux<Credit> findByDni(@PathVariable("dni") String dni) {
		LOGGER.info("FindByDni dni: " + dni);
		return creditService.findByDni(dni);
	}
}
