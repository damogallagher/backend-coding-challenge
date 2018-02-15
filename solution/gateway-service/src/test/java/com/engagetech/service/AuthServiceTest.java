/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.Calendar;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.engagetech.repository.AuthRepository;
import com.engagetech.service.impl.AuthServiceImpl;
import com.engagetech.vo.AuthVO;

@RunWith(EasyMockRunner.class)
public class AuthServiceTest {

	@TestSubject
	private AuthServiceImpl authService = new AuthServiceImpl();

	@Mock
	private AuthRepository mockAuthRepository;

	@Test
	public void testIsApiKeyValid_ApiKeyIsNull() {
		String apiKey = null;

		Boolean isValid = authService.isApiKeyValue(apiKey);
		assertFalse(isValid);
	}
	
	@Test
	public void testIsApiKeyValid_ApiKeyIsEmpty() {
		String apiKey = "";

		Boolean isValid = authService.isApiKeyValue(apiKey);
		assertFalse(isValid);
	}
	
	@Test
	public void testIsApiKeyValid_False() {
		String apiKey = "dasdsa";
		AuthVO authVO = null;
		
		EasyMock.expect(mockAuthRepository.findByApiKey(EasyMock.anyString())).andReturn(authVO);
		EasyMock.replay(mockAuthRepository);		

		Boolean isValid = authService.isApiKeyValue(apiKey);
		assertFalse(isValid);		

		EasyMock.verify(mockAuthRepository);		
	}
	
	@Test
	public void testIsApiKeyValid_True() {
		String apiKey = "dasdsa";
		AuthVO authVO = new AuthVO();
		authVO.setApiKey("apiKey");
		authVO.setDateAdded(Calendar.getInstance());
		authVO.setName("Joe Bloggs");
		authVO.setId(1);
		authVO.setEmailAddress("joe.bloggs@gmail.com");
		
		EasyMock.expect(mockAuthRepository.findByApiKey(EasyMock.anyString())).andReturn(authVO);
		EasyMock.replay(mockAuthRepository);		

		Boolean isValid = authService.isApiKeyValue(apiKey);
		assertTrue(isValid);		

		EasyMock.verify(mockAuthRepository);		
	}
}
