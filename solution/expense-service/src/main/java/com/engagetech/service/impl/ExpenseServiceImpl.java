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
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.engagetech.repository.ExpenseRepository;
import com.engagetech.rest.vo.ExchangeRateVO;
import com.engagetech.service.IExchangeRateService;
import com.engagetech.service.IExpenseService;
import com.engagetech.service.IVatService;
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
	private static Logger LOGGER = LoggerFactory.getLogger(ExpenseServiceImpl.class);
	
	@Autowired
	private ExpenseRepository expenseRepository;
	
	@Autowired 
	private IVatService vatService;

	@Autowired
	private IExchangeRateService exchangeRateService;
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

		String currency = expenseVO.getOriginalCurrency();
		//If currency is not empty - then we need to perform a 
		if (!StringUtils.isEmpty(currency) && !currency.equalsIgnoreCase(Constants.CURRENCY_GBP)) {
			ExchangeRateVO exchangeRateVO = exchangeRateService.performGBPConversion(currency, expenseVO.getTotalValue());
			if (exchangeRateVO == null) {
				LOGGER.error("Failed to get the exchange rate for the currency:{}", currency);
				return savedExpense;
			}
			
			expenseVO.setTotalValue(exchangeRateVO.getConvertedValue());
			expenseVO.setOriginalCurrency(currency.toUpperCase());
			expenseVO.setOriginalValue(exchangeRateVO.getOriginalValue());
			expenseVO.setExchangeRate(exchangeRateVO.getConversionRate());
			
		} else {
			//Set the currency to be gdp in the db so we know what it is - as well as original value and exchange rate of 2
			expenseVO.setOriginalCurrency(Constants.CURRENCY_GBP);
			expenseVO.setOriginalValue(expenseVO.getTotalValue());
			expenseVO.setExchangeRate(Constants.BD_ONE);
		}
		
		BigDecimal totalValue = expenseVO.getTotalValue();
		BigDecimal vatPaid = vatService.calculateTotalVat(totalValue);
		if (vatPaid == null) {
			LOGGER.error("Failed to calculate the total vat paid");
			return savedExpense;
		}

		//Calculate the vat paid based on the total price
		BigDecimal totalWithoutVat = totalValue.subtract(vatPaid);
		
		//Scale and round to 2 decimal places
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
