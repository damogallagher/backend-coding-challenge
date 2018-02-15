/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 *
 */
package com.engagetech.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import com.engagetech.repository.ExpenseRepository;
import com.engagetech.rest.ExpenseRestController;
import com.engagetech.service.IExpenseService;
import com.engagetech.vo.ExpenseVO;

/**
 * Method to implement all expense related items
 * @author damien
 *
 */
@Service
public class ExpenseServiceImpl implements IExpenseService {

	/** Private logger variable **/
	private static Logger LOGGER = LoggerFactory.getLogger(ExpenseRestController.class);
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	/**
	 * Method to get all expenses
	 * @return
	 */
	@Override
	public List<ExpenseVO> getAllExpenses() {
		LOGGER.debug("Entered getAllExpenses");
		
		List<ExpenseVO> expenseList = (List<ExpenseVO>) expenseRepository.findAll();
		
		LOGGER.debug("Exiting getAllExpenses");
		return expenseList;
	}
	
	/**
	 * Method to save an expense
	 * @param expenseVO
	 * @return
	 */
	@Override
	public ExpenseVO saveExpense(ExpenseVO expenseVO) {
		LOGGER.debug("Entered saveExpense");
		ExpenseVO savedExpense = null;
		if (expenseVO == null) {
			LOGGER.error("expenseVO passed in is null");
			return savedExpense;
		}
		try {
			savedExpense = expenseRepository.save(expenseVO);
		} catch (DataAccessException e) {
			LOGGER.error("A DataAccessException has occured saving the Expense. Exception is:{}", e);
			savedExpense = null;
		}
		LOGGER.debug("Exiting saveExpense");
		return savedExpense;
	}

}
