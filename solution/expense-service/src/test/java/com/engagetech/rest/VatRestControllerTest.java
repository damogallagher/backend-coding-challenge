/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.engagetech.service.IVatService;
import com.engagetech.util.JsonUtils;

@RunWith(EasyMockRunner.class)
public class VatRestControllerTest {

	@TestSubject
	private VatRestController vatRestController = new VatRestController();

	@Mock
	private IVatService mockVatService;
	
	private MockMvc mockMvc;
	@Before
	public void setUp() {
		
		// Setup Spring test in standalone mode
		this.mockMvc = MockMvcBuilders.standaloneSetup(vatRestController).build();	
	}
	@Test
	public void testGetTotalVat_FailedToGetVatRate() throws Exception {
		BigDecimal totalPrice = new BigDecimal("200.20");
		BigDecimal returnedVat = null;
		EasyMock.expect(mockVatService.calculateTotalVat(EasyMock.isA(BigDecimal.class))).andReturn(returnedVat);
		
		EasyMock.replay(mockVatService);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/vat")
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(JsonUtils.convertObjectToJson(totalPrice)))
		.andExpect(status().isOk());
		
		EasyMock.verify(mockVatService);
	}
	@Test
	public void testGetTotalVat_Success() throws Exception {
		BigDecimal totalPrice = new BigDecimal("200.20");
		BigDecimal returnedVat = new BigDecimal("20.20");
		EasyMock.expect(mockVatService.calculateTotalVat(EasyMock.isA(BigDecimal.class))).andReturn(returnedVat);
		
		EasyMock.replay(mockVatService);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/vat")
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(JsonUtils.convertObjectToJson(totalPrice)))
		.andExpect(status().isOk())
		.andExpect(content().string(returnedVat.toString()));
		
		EasyMock.verify(mockVatService);
	}
}
