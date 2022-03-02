package com.nttdata.lagm.credit.config;

import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.WebFluxConfigurer;

@Configuration
@EnableWebFlux
public interface WebFluxConfig extends WebFluxConfigurer{

}
