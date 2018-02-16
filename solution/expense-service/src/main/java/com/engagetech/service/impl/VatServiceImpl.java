/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 *
 */
package com.engagetech.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.engagetech.rest.ExpenseRestController;
import com.engagetech.service.IVatService;
import com.engagetech.util.Constants;

/**
 * Method to implement all vat related items
 * @author damien
 *
 */
@Service
public class VatServiceImpl implements IVatService {

	/** Private logger variable **/
	private static Logger LOGGER = LoggerFactory.getLogger(ExpenseRestController.class);
	
	@Value("${uk.vat.rat}")
	private Float ukVatRate;	
	public void setUkVatRate(Float ukVatRate) {
		this.ukVatRate = ukVatRate;
	}


	/**
	 * Method to calculate the total vat
	 * @param totalPrice
	 * @return
	 */
	public BigDecimal calculateTotalVat(BigDecimal totalPrice) {
		LOGGER.debug("Entered calculateTotalVat");
		BigDecimal totalVat = null;
		if (totalPrice == null) {
			LOGGER.error("totalPrice passed in is null");
			return totalVat;
		}

		BigDecimal ukVatRatePct = new BigDecimal(ukVatRate);

		//Calculate the vat paid based on the total price
		totalVat = totalPrice.multiply(ukVatRatePct).divide(Constants.BD_ONE_HUNDRED);
		
		//Scale and round to 2 decimal places
		totalVat = totalVat.setScale(Constants.INT_TWO, RoundingMode.HALF_EVEN);

		LOGGER.debug("Exiting calculateTotalVat - totalVat:{}", totalVat);
		return totalVat;
	}

}
