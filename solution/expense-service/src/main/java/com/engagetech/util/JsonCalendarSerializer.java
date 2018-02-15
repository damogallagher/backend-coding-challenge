/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 *
 */
package com.engagetech.util;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

/**
 * Class to serialize a calendar to a string format
 * See https://stackoverflow.com/questions/33876131/serialization-for-java-calendar
 * @author damien
 *
 */
public class JsonCalendarSerializer extends JsonSerializer<Calendar> {
	/** Private logger variable **/
	private static Logger LOGGER = LoggerFactory.getLogger(JsonCalendarSerializer.class);
	private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * Method to serialize a json top a calendar
	 */
	@Override
	public void serialize(Calendar cal, JsonGenerator jsonGenerator, SerializerProvider provider) {
		LOGGER.debug("Entered serialize");

		try {
			String dateString = formatter.format(cal.getTime());
			LOGGER.debug("dateString:{}", dateString);

			jsonGenerator.writeString(dateString);
		} catch (IOException e) {
			LOGGER.error("An IOException occured converting json to a calendat. Exception is:{}", e);
		}
		LOGGER.debug("Exiting serialize");
	}
}
