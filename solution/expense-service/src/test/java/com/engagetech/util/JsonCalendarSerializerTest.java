/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 *
 */
package com.engagetech.util;

import java.io.IOException;
import java.util.Calendar;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;

@RunWith(EasyMockRunner.class)
public class JsonCalendarSerializerTest {

	@TestSubject
	private JsonCalendarSerializer jsonCalendarSerializer = new JsonCalendarSerializer();

	@Mock
	private JsonGenerator mockJsonGenerator;
	@Mock
	private SerializerProvider mockProvider;

	@Test
	public void testIOException() throws IOException {
		Calendar cal = Calendar.getInstance();
		
		mockJsonGenerator.writeString(EasyMock.anyString());
		EasyMock.expectLastCall().andThrow(new IOException());
		EasyMock.replay(mockJsonGenerator);
		EasyMock.replay(mockProvider);
		
		jsonCalendarSerializer.serialize(cal, mockJsonGenerator, mockProvider);
		
		EasyMock.verify(mockJsonGenerator);
		EasyMock.verify(mockProvider);
	}
	@Test
	public void testSuccess() throws IOException {
		Calendar cal = Calendar.getInstance();
		
		mockJsonGenerator.writeString(EasyMock.anyString());
		EasyMock.expectLastCall();
		EasyMock.replay(mockJsonGenerator);
		EasyMock.replay(mockProvider);
		
		jsonCalendarSerializer.serialize(cal, mockJsonGenerator, mockProvider);
		
		EasyMock.verify(mockJsonGenerator);
		EasyMock.verify(mockProvider);
	}
}
