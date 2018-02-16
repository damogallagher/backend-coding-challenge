/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.util;

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
	public static final String DATE_FORMAT_STR = "MM/dd/yyyy";

	/** Regex pattern for text submitted through forms **/
	public static final String TEXT_REGEX = "[a-zA-Z0-9 ,-}{]+";
	
}
