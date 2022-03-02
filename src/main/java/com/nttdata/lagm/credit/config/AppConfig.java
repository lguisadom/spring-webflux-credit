package com.nttdata.lagm.credit.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import lombok.Data;

@Data
@Configuration
public class AppConfig {
	@Value("${server.port}")
	private String port;
	
	@Value("${spring.data.mongodb.database}")
	private String mongoDatabase;
	
	@Value("${application.config.description}")
	private String profileDescription;
}
