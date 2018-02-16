/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 *
 */
package com.engagetech.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;

import com.engagetech.repository.ExpenseRepository;
import com.engagetech.service.impl.ExpenseServiceImpl;
import com.engagetech.vo.ExpenseVO;

@RunWith(EasyMockRunner.class)
public class ExpenseServiceTest {

	@TestSubject
	private ExpenseServiceImpl expenseService = new ExpenseServiceImpl();

	@Mock
	private ExpenseRepository mockExpenseRepository;
	@Mock
	private DataAccessException mockDataAccessException;
	
	@Test
	public void testGetExpenses_NullExpensesReturned() {
		List<ExpenseVO> dbExpenseList = null;
		
		EasyMock.expect(mockExpenseRepository.findAll()).andReturn(dbExpenseList);
		EasyMock.replay(mockExpenseRepository);
		
		List<ExpenseVO> returnedExpenseList = expenseService.getAllExpenses();
		assertNull(returnedExpenseList);
		
		EasyMock.verify(mockExpenseRepository);
	}
	
	@Test
	public void testGetExpenses_NoExpensesReturned() {
		List<ExpenseVO> dbExpenseList = new LinkedList<>();
		
		EasyMock.expect(mockExpenseRepository.findAll()).andReturn(dbExpenseList);
		EasyMock.replay(mockExpenseRepository);
		
		List<ExpenseVO> returnedExpenseList = expenseService.getAllExpenses();
		assertNotNull(returnedExpenseList);
		assertTrue(returnedExpenseList.size() == 0);
		
		EasyMock.verify(mockExpenseRepository);
	}
	
	@Test
	public void testGetExpenses_1ExpenseReturned() {
		List<ExpenseVO> dbExpenseList = new LinkedList<>();
		dbExpenseList.add(new ExpenseVO());
		
		EasyMock.expect(mockExpenseRepository.findAll()).andReturn(dbExpenseList);
		EasyMock.replay(mockExpenseRepository);
		
		List<ExpenseVO> returnedExpenseList = expenseService.getAllExpenses();
		assertNotNull(returnedExpenseList);
		assertTrue(returnedExpenseList.size() == 1);
		
		EasyMock.verify(mockExpenseRepository);
	}
	
	@Test
	public void testGetExpenses_2ExpensesReturned() {
		List<ExpenseVO> dbExpenseList = new LinkedList<>();
		dbExpenseList.add(new ExpenseVO());
		dbExpenseList.add(new ExpenseVO());
		
		EasyMock.expect(mockExpenseRepository.findAll()).andReturn(dbExpenseList);
		EasyMock.replay(mockExpenseRepository);
		
		List<ExpenseVO> returnedExpenseList = expenseService.getAllExpenses();
		assertNotNull(returnedExpenseList);
		assertTrue(returnedExpenseList.size() == 2);
		
		EasyMock.verify(mockExpenseRepository);
	}
	@Test
	public void testSaveExpense_ExpenseVOIsNull() {
		ExpenseVO newExpenseVO = null;
		
		ExpenseVO returnedExpenseVO = expenseService.saveExpense(newExpenseVO);
		assertNull(returnedExpenseVO);
	}
	
	@Test
	public void testSaveExpense_ExceptionPerformingSave() {
		ExpenseVO newExpenseVO = new ExpenseVO();
		EasyMock.expect(mockExpenseRepository.save(EasyMock.isA(ExpenseVO.class))).andThrow(mockDataAccessException);
		EasyMock.expect(mockDataAccessException.getMessage()).andReturn("Exception");
		EasyMock.expect(mockDataAccessException.getStackTrace()).andReturn(null);
		EasyMock.expect(mockDataAccessException.getCause()).andReturn(null);
		
		EasyMock.replay(mockExpenseRepository);
		EasyMock.replay(mockDataAccessException);
		
		ExpenseVO returnedExpenseVO = expenseService.saveExpense(newExpenseVO);
		assertNull(returnedExpenseVO);
		
		EasyMock.verify(mockExpenseRepository);
		EasyMock.verify(mockDataAccessException);
	}
	@Test
	public void testSaveExpense_NullReturned() {
		ExpenseVO newExpenseVO = new ExpenseVO();
		ExpenseVO dbExpenseVO = null;
		EasyMock.expect(mockExpenseRepository.save(EasyMock.isA(ExpenseVO.class))).andReturn(dbExpenseVO);
		EasyMock.replay(mockExpenseRepository);
		
		ExpenseVO returnedExpenseVO = expenseService.saveExpense(newExpenseVO);
		assertNull(returnedExpenseVO);
		
		EasyMock.verify(mockExpenseRepository);
	}
	@Test
	public void testSaveExpense_Success() {
		ExpenseVO newExpenseVO = new ExpenseVO();
		ExpenseVO dbExpenseVO = new ExpenseVO();
		EasyMock.expect(mockExpenseRepository.save(EasyMock.isA(ExpenseVO.class))).andReturn(dbExpenseVO);
		EasyMock.replay(mockExpenseRepository);
		
		ExpenseVO returnedExpenseVO = expenseService.saveExpense(newExpenseVO);
		assertNotNull(returnedExpenseVO);
		
		EasyMock.verify(mockExpenseRepository);
	}
}

