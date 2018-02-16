/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 *
 */
package com.engagetech.rest.vo;

import java.math.BigDecimal;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Object to hold the data returned from fixer
 * @author damien
 *
 */
@JsonIgnoreProperties(ignoreUnknown=true)
public class FixerDataVO {

	@JsonProperty("base")
	private String baseCurrency;
	
	@JsonProperty("date")
	private String date;
	
	@JsonProperty("rates")
	private Map<String, BigDecimal> rates;

	public String getBaseCurrency() {
		return baseCurrency;
	}

	public void setBaseCurrency(String baseCurrency) {
		this.baseCurrency = baseCurrency;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public Map<String, BigDecimal> getRates() {
		return rates;
	}

	public void setRates(Map<String, BigDecimal> rates) {
		this.rates = rates;
	}

	@Override
	public String toString() {
		return "FixerDataVO [baseCurrency=" + baseCurrency + ", date=" + date + ", rates=" + rates + "]";
	}
	
	
}
