/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.filters;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.engagetech.service.IAuthService;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * All requests to our microservices are intercepted in this filter and the api is checked top ensure it is valie
 * @author damien
 *
 */
public class GatewayRequestFilter extends ZuulFilter {

	/** Private logger variable **/
	private static Logger LOGGER = LoggerFactory.getLogger(GatewayRequestFilter.class);

	@Autowired
	private IAuthService authSevice;
	
	/**
	 * Specify the type of this filter
	 * pre - pre-routing filtering,
     * route - routing to an origin
     * post - post-routing filters
     * error - error handling
	 */
	@Override
	public String filterType() {
		return "route";
	}

	/**
	 * The order the filter should run
	 */
	@Override
	public int filterOrder() {
		return 1;
	}

	/**
	 * Whether we should filter or not
	 */
	@Override
	public boolean shouldFilter() {
		return true;
	}

	/**
	 * The filter  logic - determine if the api key is valid or not
	 */
	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		LOGGER.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
		String apiKey = request.getHeader("x-api-key");
		LOGGER.info("apiKey:"+apiKey);
		
		boolean isApiKeyValid = authSevice.isApiKeyValue(apiKey);
		if (BooleanUtils.isNotTrue(isApiKeyValid)) {
			String msg = "The apiKey '"+apiKey+"' is not valid";
			LOGGER.error(msg);
			ctx.setResponseBody(msg);
			ctx.setSendZuulResponse(false);
		} 
			
		return null;		
	} 

}
