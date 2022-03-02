package com.nttdata.lagm.credit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class AppConfig {
	@Value("${server.port}")
	private String port;
	
	@Value("${spring.data.mongodb.authentication-database}")
	private String mongoAuthenticationDatabase;
	
	@Value("${application.config.description}")
	private String profileDescription;
}
