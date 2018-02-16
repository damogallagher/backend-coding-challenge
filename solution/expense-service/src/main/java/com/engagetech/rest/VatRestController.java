/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.rest;

import java.math.BigDecimal;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engagetech.service.IVatService;
import com.engagetech.vo.ExpenseVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for all vat related api calls
 * 
 * @author damien
 *
 */
@RestController
@RequestMapping(value="/vat")
public class VatRestController {

	/** Private logger variable **/
	private static Logger LOGGER = LoggerFactory.getLogger(VatRestController.class);

	@Autowired
	private IVatService vatService;


	/**
	 * Method to get the total vat 
	 * @param totalPrice
	 * @return
	 */
	@ApiOperation(notes = "Get Total Vat", value = "Get Total Vat", nickname = "getTotalVat")
	@ApiResponses({ @ApiResponse(code = 200, message = "Save Expense", response = ExpenseVO.class) })
	@PostMapping(value = {"", "/"})
	public BigDecimal getTotalVat(@Valid @RequestBody BigDecimal totalPrice) {
		LOGGER.debug("Entered getTotalVat - totalPrice:{}", totalPrice);
		BigDecimal totalVat = vatService.calculateTotalVat(totalPrice);
		LOGGER.debug("Exiting getTotalVat - totalVat:{}", totalVat);
		return totalVat;
	}
}
