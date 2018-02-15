/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.engagetech.filters.GatewayRequestFilter;

/**
 * All beans to be configured are created in this class
 * @author damien
 *
 */
@Configuration
public class BeanConfig {

	/**
	 * Bean for the gateway request filter
	 * @return
	 */
	@Bean
	public GatewayRequestFilter gatewayRequestFilter() {
		return new GatewayRequestFilter();
	}
}
