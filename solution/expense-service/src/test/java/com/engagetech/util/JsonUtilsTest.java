/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.util;


import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import java.io.IOException;

import org.easymock.EasyMock;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.engagetech.vo.ExpenseVO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(PowerMockRunner.class)
@PrepareForTest(JsonUtils.class)
public class JsonUtilsTest {

	@Test
	public void testConstructor() {
		JsonUtils JsonUtils = new JsonUtils();
		assertNotNull(JsonUtils);
	}
	
	@Test
	public void testConvertObjectToJson_ObjectIsNull() {
		ExpenseVO object = null;
		String jsonStr = JsonUtils.convertObjectToJson(object);
		assertNull(jsonStr);
	}
	
	@Test
	public void testConvertObjectToJson_JsonProcessingException() throws Exception {
		JsonProcessingException mockJsonProcessingException = PowerMock.createMock(JsonProcessingException.class);
		ExpenseVO object = new ExpenseVO();
		
		ObjectMapper mockObjectMapper = PowerMock.createMock(ObjectMapper.class);
		PowerMock.expectNew(ObjectMapper.class).andReturn(mockObjectMapper);
		EasyMock.expect(mockObjectMapper.writeValueAsString(EasyMock.anyObject())).andThrow(mockJsonProcessingException);
		EasyMock.expect(mockJsonProcessingException.getMessage()).andReturn("exception");
		
		PowerMock.replay(mockObjectMapper, ObjectMapper.class);
		PowerMock.replay(mockJsonProcessingException);
		
		String jsonStr = JsonUtils.convertObjectToJson(object);
		assertNull(jsonStr);
		
		PowerMock.verify(mockObjectMapper, ObjectMapper.class);
		PowerMock.verify(mockJsonProcessingException);
	}
	
	@Test
	public void testConvertObjectToJson_Success() throws Exception {
		ExpenseVO object = new ExpenseVO();
		
		ObjectMapper mockObjectMapper = PowerMock.createMock(ObjectMapper.class);
		PowerMock.expectNew(ObjectMapper.class).andReturn(mockObjectMapper);
		EasyMock.expect(mockObjectMapper.writeValueAsString(EasyMock.anyObject())).andReturn("{'a':'21321'}");
		PowerMock.replay(mockObjectMapper, ObjectMapper.class);
		
		String jsonStr = JsonUtils.convertObjectToJson(object);
		assertNotNull(jsonStr);
		
		PowerMock.verify(mockObjectMapper, ObjectMapper.class);
	}
	
	@Test
	public void testConvertJsonToObject_JsonStrIsNull() throws Exception {
		String jsonStr = null;

		Object returnObject = JsonUtils.convertJsonToObject(jsonStr, ExpenseVO.class);
		assertNull(returnObject);
	}
	@Test
	public void testConvertJsonToObject_JsonStrIsEmpty() throws Exception {
		String jsonStr = "";

		Object returnObject = JsonUtils.convertJsonToObject(jsonStr, ExpenseVO.class);
		assertNull(returnObject);
	}
	
	@Test
	public void testConvertJsonToObject_FailureIOException() throws Exception {
		String jsonStr = "{'a':'21321'}";
		
		ObjectMapper mockObjectMapper = PowerMock.createMock(ObjectMapper.class);
		PowerMock.expectNew(ObjectMapper.class).andReturn(mockObjectMapper);
		EasyMock.expect(mockObjectMapper.readValue(jsonStr, ExpenseVO.class)).andThrow(new IOException());
		PowerMock.replay(mockObjectMapper, ObjectMapper.class);
		
		Object returnObject = JsonUtils.convertJsonToObject(jsonStr, ExpenseVO.class);
		assertNull(returnObject);
		
		PowerMock.verify(mockObjectMapper, ObjectMapper.class);
	}
	
	@Test
	public void testConvertJsonToObject_Success() throws Exception {
		String jsonStr = "{'a':'21321'}";
		
		ObjectMapper mockObjectMapper = PowerMock.createMock(ObjectMapper.class);
		PowerMock.expectNew(ObjectMapper.class).andReturn(mockObjectMapper);
		EasyMock.expect(mockObjectMapper.readValue(jsonStr, ExpenseVO.class)).andReturn(new ExpenseVO());
		PowerMock.replay(mockObjectMapper, ObjectMapper.class);
		
		Object returnObject = JsonUtils.convertJsonToObject(jsonStr, ExpenseVO.class);
		assertNotNull(returnObject);
		
		PowerMock.verify(mockObjectMapper, ObjectMapper.class);
	}
}


