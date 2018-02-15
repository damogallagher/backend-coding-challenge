/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.main;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;

@RunWith(PowerMockRunner.class)
@PrepareForTest(value={SpringApplication.class, GatewayServiceMain.class}) 
public class GatewayServiceMainTest {

	@Test
	public void mainTest() {
		ConfigurableApplicationContext mockConfigurableApplicationContext = PowerMock.createMock(ConfigurableApplicationContext.class);
		PowerMock.mockStatic(SpringApplication.class);
		
		EasyMock.expect(SpringApplication.run(EasyMock.isA(GatewayServiceMain.class.getClass()))).andReturn(mockConfigurableApplicationContext);
		PowerMock.replay(SpringApplication.class);

		String[] args = new String[]{"a", "b"};
		GatewayServiceMain.main(args);
		
		PowerMock.verify(SpringApplication.class);		
	}
	
}
