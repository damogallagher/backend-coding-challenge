/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.main;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({ "com.engagetech" })
public class ExpenseServiceMain {

	/** Private logger variable **/
	private static Logger LOGGER = LoggerFactory.getLogger(ExpenseServiceMain.class);
	
	public static void main(String[] args) {
		LOGGER.debug("Entered Main Method");
		SpringApplication.run(ExpenseServiceMain.class);
	}
}
