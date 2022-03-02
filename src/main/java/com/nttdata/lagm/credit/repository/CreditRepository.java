package com.nttdata.lagm.credit.repository;

import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.nttdata.lagm.credit.model.Credit;

@Repository
public interface CreditRepository extends ReactiveMongoRepository<Credit, Long> {

}