/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 *
 */
package com.engagetech.util;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

/**
 * Class to serialize a calendar to a string format
 * 
 * @author damien
 *
 */
public class JsonCalendarDeserializer extends JsonDeserializer<Calendar> {
	/** Private logger variable **/
	private static Logger LOGGER = LoggerFactory.getLogger(JsonCalendarDeserializer.class);
	private SimpleDateFormat formatter = new SimpleDateFormat("MM/dd/yyyy");

	/**
	 * Method to deserialize a calendar to json
	 */
	@Override
	public Calendar deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
		LOGGER.debug("Entered deserialize");

		try {
			String dateAsString = jsonParser.getText();
			LOGGER.debug("dateAsString:{}", dateAsString);
			Date date = formatter.parse(dateAsString);
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			return calendar;
		} catch (ParseException e) {
			LOGGER.error("A ParseException occured converted a calendar to json. Exception:{}", e);
		} catch (IOException e) {
			LOGGER.error("An IOException occured converted a calendar to json. Exception:{}", e);
		}
		return null;

	}
}
