/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.filters;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

public class GatewayRequestFilter extends ZuulFilter {

	/** Private logger variable **/
	private static Logger LOGGER = LoggerFactory.getLogger(GatewayRequestFilter.class);

	@Override
	public String filterType() {
		return "route";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		LOGGER.info(String.format("%s request to %s", request.getMethod(), request.getRequestURL().toString()));
		String apiKey = request.getHeader("x-api-key");
		LOGGER.info("apiKey:"+apiKey);
		
		if (apiKey == null || apiKey.length() ==0) {
			ctx.setResponseBody("Invalid or empty apiKey passed into method");
			ctx.setSendZuulResponse(false);
		}
		return null;
	}

}
