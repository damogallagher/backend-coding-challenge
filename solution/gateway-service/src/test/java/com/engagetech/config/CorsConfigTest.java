/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.config;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;
import org.springframework.web.servlet.config.annotation.CorsRegistration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

public class CorsConfigTest {

	CorsConfig corsConfig;	
	
	CorsRegistry mockCorsRegistry;
	CorsRegistration mockCorsRegistration;
	@Before
	public void setUp() {
		corsConfig = new CorsConfig();
		mockCorsRegistry = EasyMock.createMock(CorsRegistry.class);
		mockCorsRegistration = EasyMock.createMock(CorsRegistration.class);
	}
	
	@Test
	public void testAddCorsMappings() {
		EasyMock.expect(mockCorsRegistry.addMapping(EasyMock.anyString())).andReturn(mockCorsRegistration);
		EasyMock.expect(mockCorsRegistration.allowedMethods(EasyMock.anyString())).andReturn(mockCorsRegistration);
		EasyMock.expect(mockCorsRegistration.allowedOrigins(EasyMock.anyString())).andReturn(mockCorsRegistration);
		EasyMock.expect(mockCorsRegistration.allowedHeaders(EasyMock.anyString())).andReturn(mockCorsRegistration);
		
		EasyMock.replay(mockCorsRegistry);
		EasyMock.replay(mockCorsRegistration);
		
		corsConfig.addCorsMappings(mockCorsRegistry);
		
		EasyMock.verify(mockCorsRegistry);
		EasyMock.verify(mockCorsRegistration);
	}
	
	
}
