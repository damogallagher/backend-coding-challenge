/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;

@EnableZuulProxy
@SpringBootApplication
@ComponentScan({ "com.engagetech" })
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
