/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.utils;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;
public class ConstantsTest {
	Constants constants;
	
	@Test
	public void testConstructor() {
		constants = new Constants();
		assertNotNull(constants);
	}	
}
