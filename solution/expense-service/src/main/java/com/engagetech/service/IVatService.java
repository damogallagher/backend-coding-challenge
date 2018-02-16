/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.service;

import java.math.BigDecimal;

/**
 * Method to define any vat related methods that need to be implemented
 * @author damien
 *
 */
public interface IVatService {

	/**
	 * Method to calculate the total vat
	 * @param totalPrice
	 * @return
	 */
	BigDecimal calculateTotalVat(BigDecimal totalPrice);

}
