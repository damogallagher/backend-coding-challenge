/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.rest;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.engagetech.service.IExpenseService;
import com.engagetech.vo.ExpenseVO;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 * Controller for all expense related api calls
 * 
 * @author damien
 *
 */
@RestController
public class ExpenseRestController {

	/** Private logger variable **/
	private static Logger LOGGER = LoggerFactory.getLogger(ExpenseRestController.class);

	@Autowired
	private IExpenseService expenseService;

	/**
	 * Method to get all expenses
	 * 
	 * @return
	 */
	@ApiOperation(notes = "Get All Expenses", value = "Get All Expenses", nickname = "getAllExpenses")
	@ApiResponses({ @ApiResponse(code = 200, message = "Expenses retrieved", response = ExpenseVO.class) })
	@GetMapping(value = "/")
	public List<ExpenseVO> getAllExpenses() {
		LOGGER.debug("Entered getAllExpenses");
		List<ExpenseVO> expenseList = expenseService.getAllExpenses();
		LOGGER.debug("Exiting getAllExpenses");
		return expenseList;
	}

	/**
	 * Method to save an expense
	 * 
	 * @return
	 */
	@ApiOperation(notes = "Save An Expense", value = "Save An Expense", nickname = "saveExpense")
	@ApiResponses({ @ApiResponse(code = 200, message = "Save Expense", response = ExpenseVO.class) })
	@PostMapping(value = "/")
	public ExpenseVO saveExpense(@Valid ExpenseVO expenseVO) {
		LOGGER.debug("Entered saveExpense");
		ExpenseVO savedExpense = expenseService.saveExpense(expenseVO);
		LOGGER.debug("Exiting saveExpense");
		return savedExpense;
	}
}
