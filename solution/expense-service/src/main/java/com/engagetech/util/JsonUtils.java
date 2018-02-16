/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.util;


import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Class with all json utility methods
 * @author damien
 *
 */
public class JsonUtils {

	/** Local LOG variable. **/
	private static final Logger LOGGER = LoggerFactory.getLogger(JsonUtils.class);

	/**
	 * Protected class constructor
	 */
	protected JsonUtils() {

	}

	/**
	 * Method to convert an object to a json string
	 * 
	 * @param object
	 * @return
	 */
	public static String convertObjectToJson(Object object) {
		LOGGER.debug("Entered convertObjectToJson");
		String jsonStr = null;
		if (object == null) {
			LOGGER.error("Object passed in is null");
			return jsonStr;
		}
		ObjectMapper mapper = new ObjectMapper();

		try {
			jsonStr = mapper.writeValueAsString(object);
		}
		catch (JsonProcessingException e) {
			LOGGER.error("A JsonProcessingException has occured. Exception is:{}", e.getMessage());
			jsonStr = null;
		}

		LOGGER.debug("Exiting convertObjectToJson");
		return jsonStr;
	}

	/**
	 * Method to convert a json string to an object
	 * 
	 * @param jsonStr
	 * @return
	 */
	public static Object convertJsonToObject(String jsonStr, Class<?> classToMap) {
		LOGGER.debug("Entered convertJsonToObject");
		Object object = null;
		if (StringUtils.isEmpty(jsonStr)) {
			LOGGER.error("jsonStr passed in is null or empty");
			return object;
		}

		ObjectMapper mapper = new ObjectMapper();

		try {
			object = mapper.readValue(jsonStr, classToMap);
		}
		catch (IOException e) {
			LOGGER.error("An IOException has occured. Exception is:{}", e);
			object = null;
		}

		LOGGER.debug("Exiting convertJsonToObject");
		return object;
	}

}
