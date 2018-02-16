/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.engagetech.rest.vo.ExchangeRateVO;
import com.engagetech.rest.vo.FixerDataVO;
import com.engagetech.service.IExchangeRateService;
import com.engagetech.util.Constants;

/**
 * Class to implement all methods associated with exchange rates
 * @author damien
 *
 */
@Service
public class ExchangeRateServiceImpl implements IExchangeRateService {
	/** Private logger variable **/
	private static Logger LOGGER = LoggerFactory.getLogger(ExchangeRateServiceImpl.class);
	
	@Value("${fixer.latestEndpoint}")
	private String fixerLatestEndpoint;
	public void setFixerLatestEndpoint(String fixerLatestEndpoint) {
		this.fixerLatestEndpoint = fixerLatestEndpoint;
	}
	
	@Autowired
	private RestTemplate restTemplate;

	/**
	 * Method to perform a GBP currency conversion 
	 * @param currency
	 * @param originalValue
	 * @return
	 */
	@Override
	public ExchangeRateVO performGBPConversion(String currency, BigDecimal originalValue) {
		LOGGER.info("Entered getExchangeRate - currency:{}, value:{}", currency, originalValue);
		ExchangeRateVO exchangeRateVO = null;
		if (StringUtils.isEmpty(currency) || originalValue == null) {
			LOGGER.info("Currency passed in is null or empty or the originalValue is null");
			return exchangeRateVO;
		}
		
		UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(fixerLatestEndpoint)
		        .queryParam("base", currency.toUpperCase())
		        .queryParam("symbols", Constants.CURRENCY_GBP);
		String url = builder.build().encode().toUri().toString();
		LOGGER.debug("url:{}", url);
		
		FixerDataVO fixerDataVO = restTemplate.getForObject(url, FixerDataVO.class);
		LOGGER.debug("fixerDataVO: {}", fixerDataVO);
		if (fixerDataVO == null) {
			LOGGER.error("Failed to get data for the base currency {}", currency);
			return exchangeRateVO;
		}
		
		BigDecimal conversionRate = null;
		for (Map.Entry<String, BigDecimal> entry : fixerDataVO.getRates().entrySet()) {
			String key = entry.getKey();
			if (key.equalsIgnoreCase(Constants.CURRENCY_GBP)) {
				conversionRate = entry.getValue();
			}
		}
		if (conversionRate == null) {
			LOGGER.error("Failed to get exchange rate for the currency {}", Constants.CURRENCY_GBP);
			return exchangeRateVO;
		}
		
		LOGGER.debug("conversionRate: {}", conversionRate);
		
		BigDecimal convertedValue = originalValue.multiply(conversionRate);
		//Scale and round to 2 decimal places
		convertedValue = convertedValue.setScale(Constants.INT_TWO, RoundingMode.HALF_EVEN);		
		LOGGER.debug("convertedValue: {}", convertedValue);
		
		exchangeRateVO = new ExchangeRateVO();
		exchangeRateVO.setOriginalValue(originalValue);
		exchangeRateVO.setConvertedValue(convertedValue);
		exchangeRateVO.setConversionRate(conversionRate);
		
		LOGGER.info("Exiting getExchangeRate");
		return exchangeRateVO;
	}

}
