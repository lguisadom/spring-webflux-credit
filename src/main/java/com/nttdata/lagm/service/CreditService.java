package com.nttdata.lagm.service;

package com.nttdata.lagm.service;




import com.nttdata.lagm.model.credit;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CreditService {
	
Flux<credit> findAll();
	
	Mono<credit> save(credit accountNumber);
	
	Mono<credit> findRetreatById(String id);
	
	Mono<credit> update(credit retreat);
	
}
