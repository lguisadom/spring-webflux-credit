package com.nttdata.lagm.repository;



import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

import com.nttdata.lagm.model.credit;



public interface CreditRepository extends ReactiveMongoRepository<credit, String>{

}