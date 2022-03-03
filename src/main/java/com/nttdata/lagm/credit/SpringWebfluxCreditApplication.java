package com.nttdata.lagm.credit;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@EnableEurekaClient
@SpringBootApplication
public class SpringWebfluxCreditApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringWebfluxCreditApplication.class, args);
	}

}
