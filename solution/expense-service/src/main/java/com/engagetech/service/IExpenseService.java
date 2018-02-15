/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.service;

import java.util.List;

import com.engagetech.vo.ExpenseVO;

/**
 * Interface to define all the expense related services that need to be implemented
 * @author damien
 *
 */
public interface IExpenseService {

	/**
	 * Method to get all expenses
	 * @return
	 */
	List<ExpenseVO> getAllExpenses();

	/**
	 * Method to save an expense
	 * @param expenseVO
	 * @return
	 */
	ExpenseVO saveExpense(ExpenseVO expenseVO);

}
