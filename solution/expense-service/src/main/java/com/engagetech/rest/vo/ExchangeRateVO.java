/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.rest.vo;

import java.math.BigDecimal;

/**
 * Class to hold data about exchange rates
 * @author damien
 *
 */
public class ExchangeRateVO {

	private BigDecimal originalValue;
	private BigDecimal conversionRate;
	private BigDecimal convertedValue;
	public BigDecimal getOriginalValue() {
		return originalValue;
	}
	public void setOriginalValue(BigDecimal originalValue) {
		this.originalValue = originalValue;
	}
	public BigDecimal getConversionRate() {
		return conversionRate;
	}
	public void setConversionRate(BigDecimal conversionRate) {
		this.conversionRate = conversionRate;
	}
	public BigDecimal getConvertedValue() {
		return convertedValue;
	}
	public void setConvertedValue(BigDecimal convertedValue) {
		this.convertedValue = convertedValue;
	}
	@Override
	public String toString() {
		return "ExchangeRateVO []";
	}
	
	
}
