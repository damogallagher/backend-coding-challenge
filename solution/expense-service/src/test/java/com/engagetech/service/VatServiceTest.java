/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 *
 */
package com.engagetech.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;

import org.easymock.EasyMockRunner;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.engagetech.service.impl.VatServiceImpl;

@RunWith(EasyMockRunner.class)
public class VatServiceTest {

	@TestSubject
	private VatServiceImpl vatService = new VatServiceImpl();

	@Before
	public void setUp() {
		vatService.setUkVatRate(20.0f);
	}
	@Test
	public void testCalculateTotalVat_TotalPriceIsNull() {
		BigDecimal totalPrice = null;
		
		BigDecimal totalVat = vatService.calculateTotalVat(totalPrice);
		assertNull(totalVat);
	}
	@Test
	public void testCalculateTotalVat_TotalPriceIs0() {
		BigDecimal totalPrice = new BigDecimal(0.0);
		
		BigDecimal totalVat = vatService.calculateTotalVat(totalPrice);
		assertNotNull(totalVat);
	}
	@Test
	public void testCalculateTotalVat_Success() {
		BigDecimal totalPrice = new BigDecimal(100.0);
		
		BigDecimal totalVat = vatService.calculateTotalVat(totalPrice);
		assertNotNull(totalVat);
	}
}

