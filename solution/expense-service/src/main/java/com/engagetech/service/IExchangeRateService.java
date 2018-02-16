/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.service;

import java.math.BigDecimal;

import com.engagetech.rest.vo.ExchangeRateVO;

/**
 * Class to define all the methods required for a nexchange rate service
 * @author damien
 *
 */
public interface IExchangeRateService {

	/**
	 * Method to perform a GBP currency conversion 
	 * @param currency
	 * @param value
	 * @return
	 */
	ExchangeRateVO performGBPConversion(String currency, BigDecimal value);

}
