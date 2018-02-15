/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Main entry point for the gateway service
 * @author damien
 *
 */
@EnableZuulProxy
@SpringBootApplication
@ComponentScan({ "com.engagetech" })
@EnableJpaRepositories(basePackages="com.engagetech.repository")
@EntityScan({ "com.engagetech" })
@EnableTransactionManagement
public class GatewayServiceMain {

	/** Private logger variable **/
	private static Logger LOGGER = LoggerFactory.getLogger(GatewayServiceMain.class);
	
	/**
	 * Main class to kick off class processing
	 * @param args
	 */
	public static void main(String[] args) {
		LOGGER.debug("Entered Main Method");
		SpringApplication.run(GatewayServiceMain.class, args);
	}
}
