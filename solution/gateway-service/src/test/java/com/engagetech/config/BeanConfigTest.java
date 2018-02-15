/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.config;

import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;

import com.engagetech.filters.GatewayRequestFilter;

public class BeanConfigTest {

	BeanConfig beanConfig;
	@Before
	public void setUp() {
		beanConfig = new BeanConfig();
	}
	
	@Test
	public void testConstructor() {
		beanConfig = new BeanConfig();
		assertNotNull(beanConfig);
	}
	
	@Test
	public void testGatewayRequestFilter() {
		GatewayRequestFilter gatewayRequestFilter = beanConfig.gatewayRequestFilter();
		assertNotNull(gatewayRequestFilter);
	}
}
