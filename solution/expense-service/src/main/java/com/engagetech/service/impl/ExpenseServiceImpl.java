/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 *
 */
package com.engagetech.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.engagetech.repository.ExpenseRepository;
import com.engagetech.rest.ExpenseRestController;
import com.engagetech.service.IExpenseService;
import com.engagetech.util.Constants;
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
	
	@Value("${uk.vat.rat}")
	private Float ukVatRate;
	
	/**
	 * Method to get all expenses
	 * @return
	 */
	@Override
	public List<ExpenseVO> getAllExpenses() {
		LOGGER.debug("Entered getAllExpenses");
		
		//Sort the expenses in descening order based on id
		Sort idDescendingOrder = new Sort(Sort.Direction.DESC, "id");
		List<ExpenseVO> expenseList = (List<ExpenseVO>) expenseRepository.findAll(idDescendingOrder);
		
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

		BigDecimal totalValue = expenseVO.getTotalValue();
		BigDecimal ukVatRatePct = new BigDecimal(ukVatRate);

		//Calculate the vat paid based on the total price
		BigDecimal vatPaid = totalValue.multiply(ukVatRatePct).divide(Constants.BD_ONE_HUNDRED);
		BigDecimal totalWithoutVat = totalValue.subtract(vatPaid);
		
		//Scale and round to 2 decimal places
		vatPaid = vatPaid.setScale(Constants.INT_TWO, RoundingMode.HALF_EVEN);
		totalWithoutVat = totalWithoutVat.setScale(Constants.INT_TWO, RoundingMode.HALF_EVEN);

		expenseVO.setVatPaid(vatPaid);
		expenseVO.setValueWithoutVat(totalWithoutVat);
		
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
