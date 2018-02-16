/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Main class of the application
 * @author damien
 *
 */
@SpringBootApplication
@ComponentScan({ "com.engagetech" })
@EnableJpaRepositories(basePackages="com.engagetech.repository")
@EntityScan({ "com.engagetech" })
public class ExpenseServiceMain {

	/** Private logger variable **/
	private static Logger LOGGER = LoggerFactory.getLogger(ExpenseServiceMain.class);
	
	public static void main(String[] args) {
		LOGGER.debug("Entered Main Method");
		SpringApplication.run(ExpenseServiceMain.class);
	}
}
