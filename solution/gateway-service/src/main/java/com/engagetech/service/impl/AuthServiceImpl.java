/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.engagetech.repository.AuthRepository;
import com.engagetech.service.IAuthService;
import com.engagetech.vo.AuthVO;



/**
 * Implementation of the auth related methods
 * @author damien
 *
 */
@Service
public class AuthServiceImpl implements IAuthService {

	/** Private logger variable **/
	private static Logger LOGGER = LoggerFactory.getLogger(AuthServiceImpl.class);
	
	@Autowired
	private AuthRepository authRepository;
	
	/**
	 * Method to check if an apiKey is valid or not
	 * @param apiKey
	 * @return
	 */
	@Transactional
	@Override
	public boolean isApiKeyValue(String apiKey) {
		LOGGER.debug("Entered isApiKeyValue - apiKey:{}", apiKey);
		boolean isApiKeyValid = false;
		
		if (StringUtils.isEmpty(apiKey)) {
			LOGGER.error("ApiKey passed in is null or empty");
			return isApiKeyValid;
		}
		
		AuthVO authVO = authRepository.findByApiKey(apiKey);
		if (authVO == null) {
			LOGGER.error("The apiKey {} does not have access", apiKey);
			return isApiKeyValid;
		}
		isApiKeyValid = true;
		
		LOGGER.debug("Exiting isApiKeyValue - isApiKeyValid:{}", isApiKeyValid);
		return isApiKeyValid;
	}

}
