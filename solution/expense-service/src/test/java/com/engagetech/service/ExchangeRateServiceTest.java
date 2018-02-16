/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 *
 */
package com.engagetech.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.web.client.RestTemplate;

import com.engagetech.rest.vo.ExchangeRateVO;
import com.engagetech.rest.vo.FixerDataVO;
import com.engagetech.service.impl.ExchangeRateServiceImpl;
import com.engagetech.util.Constants;

@RunWith(EasyMockRunner.class)
public class ExchangeRateServiceTest {

	@TestSubject
	private ExchangeRateServiceImpl exchangeRateService = new ExchangeRateServiceImpl();

	@Mock
	private RestTemplate mockRestTemplate;
	
	@Before
	public void setUp() {
		exchangeRateService.setFixerLatestEndpoint("http://api.fixer.io/latest");
	}
	@Test
	public void testPerformGBPConversion_CurrencyIsNull() {
		String currency = null;
		BigDecimal originalValue = new BigDecimal("1.0");
		
		ExchangeRateVO exchangeRateVO = exchangeRateService.performGBPConversion(currency, originalValue);
		assertNull(exchangeRateVO);
	}
	@Test
	public void testPerformGBPConversion_CurrencyIsEmpty() {
		String currency = "";
		BigDecimal originalValue = new BigDecimal("1.0");
		
		ExchangeRateVO exchangeRateVO = exchangeRateService.performGBPConversion(currency, originalValue);
		assertNull(exchangeRateVO);
	}
	@Test
	public void testPerformGBPConversion_OriginalValueIsNull() {
		String currency = "EUR";
		BigDecimal originalValue = null;
		
		ExchangeRateVO exchangeRateVO = exchangeRateService.performGBPConversion(currency, originalValue);
		assertNull(exchangeRateVO);
	}
	
	@Test
	public void testPerformGBPConversion_FailedToGetDataFromFixer() {
		String currency = "EUR";
		BigDecimal originalValue = new BigDecimal("1.1");
		
		EasyMock.expect(mockRestTemplate.getForObject(EasyMock.anyString(), EasyMock.anyObject())).andReturn(null);
		EasyMock.replay(mockRestTemplate);
		
		ExchangeRateVO exchangeRateVO = exchangeRateService.performGBPConversion(currency, originalValue);
		assertNull(exchangeRateVO);
		
		EasyMock.verify(mockRestTemplate);
	}
	
	@Test
	public void testPerformGBPConversion_NoGDPCurrency() {
		String currency = "EUR";
		BigDecimal originalValue = new BigDecimal("1.1");
		
		FixerDataVO fixerData = new FixerDataVO();
		fixerData.setBaseCurrency("EUR");
		fixerData.setDate("02/02/2018");
		Map<String, BigDecimal> ratesMap = new HashMap<>();
		ratesMap.put("EUR", new BigDecimal("2.00"));
		fixerData.setRates(ratesMap);
		
		EasyMock.expect(mockRestTemplate.getForObject(EasyMock.anyString(), EasyMock.anyObject())).andReturn(fixerData );
		EasyMock.replay(mockRestTemplate);
		
		ExchangeRateVO exchangeRateVO = exchangeRateService.performGBPConversion(currency, originalValue);
		assertNull(exchangeRateVO);
		
		EasyMock.verify(mockRestTemplate);
	}
	
	@Test
	public void testPerformGBPConversion_Success() {
		String currency = "EUR";
		BigDecimal originalValue = new BigDecimal("1.1");
		
		FixerDataVO fixerData = new FixerDataVO();
		fixerData.setBaseCurrency("EUR");
		fixerData.setDate("02/02/2018");
		Map<String, BigDecimal> ratesMap = new HashMap<>();
		ratesMap.put("EUR", new BigDecimal("2.00"));
		ratesMap.put(Constants.CURRENCY_GBP, new BigDecimal("2.00"));
		fixerData.setRates(ratesMap);
		
		EasyMock.expect(mockRestTemplate.getForObject(EasyMock.anyString(), EasyMock.anyObject())).andReturn(fixerData );
		EasyMock.replay(mockRestTemplate);
		
		ExchangeRateVO exchangeRateVO = exchangeRateService.performGBPConversion(currency, originalValue);
		assertNotNull(exchangeRateVO);
		assertNotNull(exchangeRateVO.getConversionRate());
		assertNotNull(exchangeRateVO.getConvertedValue());
		assertNotNull(exchangeRateVO.getOriginalValue());
		assertNotNull(exchangeRateVO.toString());
		
		EasyMock.verify(mockRestTemplate);
	}
	
}

