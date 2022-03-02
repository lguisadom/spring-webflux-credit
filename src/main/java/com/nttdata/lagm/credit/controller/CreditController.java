package com.nttdata.lagm.credit.controller;

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
import com.nttdata.lagm.credit.model.Credit;
import com.nttdata.lagm.credit.service.CreditService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/credit")
public class CreditController {
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
	private void create(@RequestBody Credit credit) {
		creditService.create(credit);
	}
	
	@GetMapping(produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus(HttpStatus.OK)
	private Flux<Credit> findAll() {
		return creditService.findAll();
	}
	
	@GetMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	private Mono<Credit> findById(@PathVariable("id") Long id) {
		return creditService.findById(id);
	}
	
	@PutMapping
	@ResponseStatus(HttpStatus.OK)
	private Mono<Credit> update(@RequestBody Credit credit) {
		return creditService.update(credit);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(HttpStatus.OK)
	private Mono<Void> delete(@PathVariable("id") Long id) {
		return creditService.delete(id);
	}
}
