/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.util;

import java.math.BigDecimal;

/**
 * Class to hold any required constants
 * @author damien
 *
 */
public class Constants {

	/**
	 * Class constructor
	 */
	protected Constants() {
		
	}
	
	/** Constant for the apiKey header **/
	public static final String API_KEY_HEADER = "x-api-key";
	
	/** Date format to use in the application **/
	public static final String DATE_FORMAT_STR = "dd/MM/yyyy";

	/** Regex pattern for text submitted through forms **/
	public static final String TEXT_REGEX = "[a-zA-Z0-9 ,-}{]+";

	/** Constant for the int number 2 **/
	public static final int INT_TWO = 2;
	
	/** Constant for the BigDecimal number 100 **/
	public static final BigDecimal BD_ONE_HUNDRED = new BigDecimal(100);
	
	/** Constant for the float number 100 **/
	public static final float FL_ONE_HUNDRED = 100.00f;


}
