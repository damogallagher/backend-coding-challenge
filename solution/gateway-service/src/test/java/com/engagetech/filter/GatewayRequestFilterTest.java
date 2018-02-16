/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.filter;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import javax.servlet.http.HttpServletRequest;

import org.easymock.EasyMock;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.easymock.PowerMock;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import com.engagetech.filters.GatewayRequestFilter;
import com.engagetech.service.IAuthService;
import com.engagetech.utils.Constants;
import com.netflix.zuul.context.RequestContext;
@RunWith(PowerMockRunner.class)
@PrepareForTest(value={RequestContext.class}) 
public class GatewayRequestFilterTest {

	@TestSubject
	private GatewayRequestFilter gatewayRequestFilter = new GatewayRequestFilter();

	@Mock
	private IAuthService mockAuthService;
	
	
	@Test
	public void testFilterType() {
		String filterType = gatewayRequestFilter.filterType();
		assertNotNull(filterType);
	}
	
	@Test
	public void testFilterOrder() {
		Integer filterOrder = gatewayRequestFilter.filterOrder();
		assertNotNull(filterOrder);
	}
	
	@Test
	public void testShouldFilter() {
		Boolean shouldFilter = gatewayRequestFilter.shouldFilter();
		assertNotNull(shouldFilter);
	}
	
	@Test
	public void testRun_OptionsRequest() {

		RequestContext mockRequestContext = PowerMock.createMock(RequestContext.class);
		HttpServletRequest mockHttpServletRequest = EasyMock.createMock(HttpServletRequest.class);
		PowerMock.mockStatic(RequestContext.class);
		
		EasyMock.expect(RequestContext.getCurrentContext()).andReturn(mockRequestContext);
		EasyMock.expect(mockRequestContext.getRequest()).andReturn(mockHttpServletRequest);
		EasyMock.expect(mockHttpServletRequest.getMethod()).andReturn(Constants.OPTIONS);

		PowerMock.replay(RequestContext.class);
		EasyMock.replay(mockRequestContext);
		EasyMock.replay(mockHttpServletRequest);

		Object returnObject = gatewayRequestFilter.run();
		assertNull(returnObject);
		
		PowerMock.verify(RequestContext.class);
		EasyMock.verify(mockRequestContext);
		EasyMock.verify(mockHttpServletRequest);	
	}
	
	
	@Test
	public void testRun_ApiKeyIsNotValid() {

		RequestContext mockRequestContext = PowerMock.createMock(RequestContext.class);
		HttpServletRequest mockHttpServletRequest = EasyMock.createMock(HttpServletRequest.class);
		PowerMock.mockStatic(RequestContext.class);
		
		EasyMock.expect(RequestContext.getCurrentContext()).andReturn(mockRequestContext);
		EasyMock.expect(mockRequestContext.getRequest()).andReturn(mockHttpServletRequest);
		EasyMock.expect(mockHttpServletRequest.getMethod()).andReturn("POST").times(2);
		EasyMock.expect(mockHttpServletRequest.getRequestURL()).andReturn(new StringBuffer("localhost"));
		EasyMock.expect(mockHttpServletRequest.getHeader(EasyMock.anyString())).andReturn("x-api-key");
		EasyMock.expect(mockAuthService.isApiKeyValue(EasyMock.anyString())).andReturn(false);
		mockRequestContext.setResponseBody(EasyMock.anyString());
		EasyMock.expectLastCall();
		mockRequestContext.setSendZuulResponse(EasyMock.anyBoolean());
		EasyMock.expectLastCall();
		
		PowerMock.replay(RequestContext.class);
		EasyMock.replay(mockRequestContext);
		EasyMock.replay(mockHttpServletRequest);
		EasyMock.replay(mockAuthService);
		
		Object returnObject = gatewayRequestFilter.run();
		assertNull(returnObject);
		
		PowerMock.verify(RequestContext.class);
		EasyMock.verify(mockRequestContext);
		EasyMock.verify(mockHttpServletRequest);
		EasyMock.verify(mockAuthService);		
	}
	
	@Test
	public void testRun_ApiKeyIsValid() {

		RequestContext mockRequestContext = PowerMock.createMock(RequestContext.class);
		HttpServletRequest mockHttpServletRequest = EasyMock.createMock(HttpServletRequest.class);
		PowerMock.mockStatic(RequestContext.class);
		
		EasyMock.expect(RequestContext.getCurrentContext()).andReturn(mockRequestContext);
		EasyMock.expect(mockRequestContext.getRequest()).andReturn(mockHttpServletRequest);
		EasyMock.expect(mockHttpServletRequest.getMethod()).andReturn("POST").times(2);
		EasyMock.expect(mockHttpServletRequest.getRequestURL()).andReturn(new StringBuffer("localhost"));
		EasyMock.expect(mockHttpServletRequest.getHeader(EasyMock.anyString())).andReturn("x-api-key");
		EasyMock.expect(mockAuthService.isApiKeyValue(EasyMock.anyString())).andReturn(true);
		
		PowerMock.replay(RequestContext.class);
		EasyMock.replay(mockRequestContext);
		EasyMock.replay(mockHttpServletRequest);
		EasyMock.replay(mockAuthService);
		
		Object returnObject = gatewayRequestFilter.run();
		assertNull(returnObject);
		
		PowerMock.verify(RequestContext.class);
		EasyMock.verify(mockRequestContext);
		EasyMock.verify(mockHttpServletRequest);
		EasyMock.verify(mockAuthService);		
	}
}
