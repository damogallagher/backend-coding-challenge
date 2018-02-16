/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.util;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.engagetech.util.Constants;
public class ConstantsTest {
	Constants constants;
	
	@Test
	public void testConstructor() {
		constants = new Constants();
		assertNotNull(constants);
	}	
}
