/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.rest;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExpenseRestController {

	@RequestMapping(value = "/available")
	  public String available() {
	    return "Spring in Action";
	  }
	
}
