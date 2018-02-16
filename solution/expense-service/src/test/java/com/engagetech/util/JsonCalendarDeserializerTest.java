/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 *
 */
package com.engagetech.util;

import java.io.IOException;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;

@RunWith(EasyMockRunner.class)
public class JsonCalendarDeserializerTest {

	@TestSubject
	private JsonCalendarDeserializer jsonCalendarDeserializer = new JsonCalendarDeserializer();

	@Mock
	private JsonParser mockJsonParser;
	@Mock
	private DeserializationContext mockDeserializationContext;

	@Test
	public void testIOException() throws IOException {
		
		EasyMock.expect(mockJsonParser.getText()).andThrow(new IOException());
		EasyMock.replay(mockJsonParser);
		EasyMock.replay(mockDeserializationContext);
		
		jsonCalendarDeserializer.deserialize(mockJsonParser, mockDeserializationContext);
		
		EasyMock.verify(mockJsonParser);
		EasyMock.verify(mockDeserializationContext);
	}
	@Test
	public void testParseException() throws IOException {
		
		EasyMock.expect(mockJsonParser.getText()).andReturn("parseException");
		EasyMock.replay(mockJsonParser);
		EasyMock.replay(mockDeserializationContext);
		
		jsonCalendarDeserializer.deserialize(mockJsonParser, mockDeserializationContext);
		
		EasyMock.verify(mockJsonParser);
		EasyMock.verify(mockDeserializationContext);
	}
	@Test
	public void testSuccess() throws IOException {
		
		EasyMock.expect(mockJsonParser.getText()).andReturn("02/02/2018");
		EasyMock.replay(mockJsonParser);
		EasyMock.replay(mockDeserializationContext);
		
		jsonCalendarDeserializer.deserialize(mockJsonParser, mockDeserializationContext);
		
		EasyMock.verify(mockJsonParser);
		EasyMock.verify(mockDeserializationContext);
	}
}
