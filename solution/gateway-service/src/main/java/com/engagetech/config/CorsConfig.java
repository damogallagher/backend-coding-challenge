/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

/**
 * Class to handle cors configuration
 * @author damien
 *
 */
@Configuration
@EnableWebMvc
public class CorsConfig extends WebMvcConfigurerAdapter {

	/** Private logger variable **/
	private static Logger LOGGER = LoggerFactory.getLogger(CorsConfig.class);

	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		LOGGER.debug("Entered addCorsMappings");
		registry.addMapping("/**").allowedMethods("*").allowedOrigins("*").allowedHeaders("*");
	}
}