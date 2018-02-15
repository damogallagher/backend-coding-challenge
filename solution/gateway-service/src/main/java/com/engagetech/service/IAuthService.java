/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.service;

/**
 * Method to define auth related methods that need to be implemented
 * @author damien
 *
 */
public interface IAuthService {

	/**
	 * Method to check if an apiKey is valid or not
	 * @param apiKey
	 * @return
	 */
	boolean isApiKeyValue(String apiKey);
}
