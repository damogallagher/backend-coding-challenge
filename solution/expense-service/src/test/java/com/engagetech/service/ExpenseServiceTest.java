/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 *
 */
package com.engagetech.service;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;

import org.easymock.EasyMock;
import org.easymock.EasyMockRunner;
import org.easymock.Mock;
import org.easymock.TestSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Sort;

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
	private ExpenseVO newExpenseVO;
	
	@Before
	public void setUp() {
		expenseService.setUkVatRate(20.0f);
		newExpenseVO = new ExpenseVO();
		newExpenseVO = new ExpenseVO();
		newExpenseVO.setId(1);
		newExpenseVO.setReason("reason");
		newExpenseVO.setTotalValue(new BigDecimal("9.55"));
		newExpenseVO.setValueWithoutVat(new BigDecimal("7.64"));
		newExpenseVO.setVatPaid(new BigDecimal("1.91"));
		newExpenseVO.setDate(Calendar.getInstance());
	}
	
	@Test
	public void testGetExpenses_NullExpensesReturned() {
		List<ExpenseVO> dbExpenseList = null;
		
		EasyMock.expect(mockExpenseRepository.findAll(EasyMock.isA(Sort.class))).andReturn(dbExpenseList);
		EasyMock.replay(mockExpenseRepository);
		
		List<ExpenseVO> returnedExpenseList = expenseService.getAllExpenses();
		assertNull(returnedExpenseList);
		
		EasyMock.verify(mockExpenseRepository);
	}
	
	@Test
	public void testGetExpenses_NoExpensesReturned() {
		List<ExpenseVO> dbExpenseList = new LinkedList<>();
		
		EasyMock.expect(mockExpenseRepository.findAll(EasyMock.isA(Sort.class))).andReturn(dbExpenseList);
		EasyMock.replay(mockExpenseRepository);
		
		List<ExpenseVO> returnedExpenseList = expenseService.getAllExpenses();
		assertNotNull(returnedExpenseList);
		assertTrue(returnedExpenseList.size() == 0);
		
		EasyMock.verify(mockExpenseRepository);
	}
	
	@Test
	public void testGetExpenses_1ExpenseReturned() {
		List<ExpenseVO> dbExpenseList = new LinkedList<>();
		dbExpenseList.add(newExpenseVO);
		
		EasyMock.expect(mockExpenseRepository.findAll(EasyMock.isA(Sort.class))).andReturn(dbExpenseList);
		EasyMock.replay(mockExpenseRepository);
		
		List<ExpenseVO> returnedExpenseList = expenseService.getAllExpenses();
		assertNotNull(returnedExpenseList);
		assertTrue(returnedExpenseList.size() == 1);
		
		EasyMock.verify(mockExpenseRepository);
	}
	
	@Test
	public void testGetExpenses_2ExpensesReturned() {
		List<ExpenseVO> dbExpenseList = new LinkedList<>();
		dbExpenseList.add(newExpenseVO);
		dbExpenseList.add(newExpenseVO);
		
		EasyMock.expect(mockExpenseRepository.findAll(EasyMock.isA(Sort.class))).andReturn(dbExpenseList);
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
		ExpenseVO dbExpenseVO = null;
		EasyMock.expect(mockExpenseRepository.save(EasyMock.isA(ExpenseVO.class))).andReturn(dbExpenseVO);
		EasyMock.replay(mockExpenseRepository);
		
		ExpenseVO returnedExpenseVO = expenseService.saveExpense(newExpenseVO);
		assertNull(returnedExpenseVO);
		
		EasyMock.verify(mockExpenseRepository);
	}
	@Test
	public void testSaveExpense_Success() {
		ExpenseVO dbExpenseVO = new ExpenseVO();
		EasyMock.expect(mockExpenseRepository.save(EasyMock.isA(ExpenseVO.class))).andReturn(dbExpenseVO);
		EasyMock.replay(mockExpenseRepository);
		
		ExpenseVO returnedExpenseVO = expenseService.saveExpense(newExpenseVO);
		assertNotNull(returnedExpenseVO);
		
		EasyMock.verify(mockExpenseRepository);
	}
}

