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
import com.engagetech.rest.vo.ExchangeRateVO;
import com.engagetech.service.impl.ExpenseServiceImpl;
import com.engagetech.util.Constants;
import com.engagetech.vo.ExpenseVO;

@RunWith(EasyMockRunner.class)
public class ExpenseServiceTest {

	@TestSubject
	private ExpenseServiceImpl expenseService = new ExpenseServiceImpl();

	@Mock
	private ExpenseRepository mockExpenseRepository;
	@Mock
	private IVatService mockVatService;
	@Mock
	private IExchangeRateService mockExchangeRateService;
	@Mock
	private DataAccessException mockDataAccessException;
	private ExpenseVO newExpenseVO;
	private ExchangeRateVO exchangeRateVO;
	@Before
	public void setUp() {
		newExpenseVO = new ExpenseVO();
		newExpenseVO = new ExpenseVO();
		newExpenseVO.setId(1);
		newExpenseVO.setReason("reason");
		newExpenseVO.setTotalValue(new BigDecimal("9.55"));
		newExpenseVO.setValueWithoutVat(new BigDecimal("7.64"));
		newExpenseVO.setVatPaid(new BigDecimal("1.91"));
		newExpenseVO.setOriginalCurrency(null);
		newExpenseVO.setExchangeRate(new BigDecimal("0.55"));
		newExpenseVO.setOriginalValue(new BigDecimal("9.55"));
		newExpenseVO.setDate(Calendar.getInstance());
		
		exchangeRateVO = new ExchangeRateVO();
		exchangeRateVO.setConversionRate(new BigDecimal("0.55"));
		exchangeRateVO.setConvertedValue(new BigDecimal("55.55"));
		exchangeRateVO.setOriginalValue(new BigDecimal("66.55"));
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
	public void testSaveExpense_FailedToCalculateVat() {
		EasyMock.expect(mockVatService.calculateTotalVat(EasyMock.isA(BigDecimal.class))).andReturn(null);
		
		EasyMock.replay(mockVatService);
		
		ExpenseVO returnedExpenseVO = expenseService.saveExpense(newExpenseVO);
		assertNull(returnedExpenseVO);

		EasyMock.verify(mockVatService);
	}
	@Test
	public void testSaveExpense_ExceptionPerformingSave() {
		EasyMock.expect(mockVatService.calculateTotalVat(EasyMock.isA(BigDecimal.class))).andReturn(new BigDecimal("2.22"));
		EasyMock.expect(mockExpenseRepository.save(EasyMock.isA(ExpenseVO.class))).andThrow(mockDataAccessException);
		EasyMock.expect(mockDataAccessException.getMessage()).andReturn("Exception");
		EasyMock.expect(mockDataAccessException.getStackTrace()).andReturn(null);
		EasyMock.expect(mockDataAccessException.getCause()).andReturn(null);
		
		EasyMock.replay(mockExpenseRepository);
		EasyMock.replay(mockDataAccessException);
		EasyMock.replay(mockVatService);
		
		ExpenseVO returnedExpenseVO = expenseService.saveExpense(newExpenseVO);
		assertNull(returnedExpenseVO);
		
		EasyMock.verify(mockExpenseRepository);
		EasyMock.verify(mockDataAccessException);
		EasyMock.verify(mockVatService);
	}
	@Test
	public void testSaveExpense_NullReturned() {
		ExpenseVO dbExpenseVO = null;
		EasyMock.expect(mockVatService.calculateTotalVat(EasyMock.isA(BigDecimal.class))).andReturn(new BigDecimal("2.22"));
		EasyMock.expect(mockExpenseRepository.save(EasyMock.isA(ExpenseVO.class))).andReturn(dbExpenseVO);
		
		EasyMock.replay(mockExpenseRepository);
		EasyMock.replay(mockVatService);
		
		ExpenseVO returnedExpenseVO = expenseService.saveExpense(newExpenseVO);
		assertNull(returnedExpenseVO);
		
		EasyMock.verify(mockExpenseRepository);
		EasyMock.verify(mockVatService);
	}
	@Test
	public void testSaveExpense_Success() {
		ExpenseVO dbExpenseVO = new ExpenseVO();
		EasyMock.expect(mockVatService.calculateTotalVat(EasyMock.isA(BigDecimal.class))).andReturn(new BigDecimal("2.22"));
		EasyMock.expect(mockExpenseRepository.save(EasyMock.isA(ExpenseVO.class))).andReturn(dbExpenseVO);
		
		EasyMock.replay(mockExpenseRepository);
		EasyMock.replay(mockVatService);
		
		ExpenseVO returnedExpenseVO = expenseService.saveExpense(newExpenseVO);
		assertNotNull(returnedExpenseVO);
		
		EasyMock.verify(mockExpenseRepository);
		EasyMock.verify(mockVatService);
	}
	@Test
	public void testSaveExpense_SuccessCurrencyIsGBP() {
		newExpenseVO.setOriginalCurrency(Constants.CURRENCY_GBP);
		ExpenseVO dbExpenseVO = new ExpenseVO();
		EasyMock.expect(mockVatService.calculateTotalVat(EasyMock.isA(BigDecimal.class))).andReturn(new BigDecimal("2.22"));
		EasyMock.expect(mockExpenseRepository.save(EasyMock.isA(ExpenseVO.class))).andReturn(dbExpenseVO);
		
		EasyMock.replay(mockExpenseRepository);
		EasyMock.replay(mockVatService);
		
		ExpenseVO returnedExpenseVO = expenseService.saveExpense(newExpenseVO);
		assertNotNull(returnedExpenseVO);
		
		EasyMock.verify(mockExpenseRepository);
		EasyMock.verify(mockVatService);
	}
	@Test
	public void testSaveExpense_SuccessCurrencyIsNull() {
		newExpenseVO.setOriginalCurrency(null);
		ExpenseVO dbExpenseVO = new ExpenseVO();
		EasyMock.expect(mockVatService.calculateTotalVat(EasyMock.isA(BigDecimal.class))).andReturn(new BigDecimal("2.22"));
		EasyMock.expect(mockExpenseRepository.save(EasyMock.isA(ExpenseVO.class))).andReturn(dbExpenseVO);
		
		EasyMock.replay(mockExpenseRepository);
		EasyMock.replay(mockVatService);
		
		ExpenseVO returnedExpenseVO = expenseService.saveExpense(newExpenseVO);
		assertNotNull(returnedExpenseVO);
		
		EasyMock.verify(mockExpenseRepository);
		EasyMock.verify(mockVatService);
	}
	@Test
	public void testSaveExpense_SuccessCurrencyIsEmpty() {
		newExpenseVO.setOriginalCurrency("");
		ExpenseVO dbExpenseVO = new ExpenseVO();
		EasyMock.expect(mockVatService.calculateTotalVat(EasyMock.isA(BigDecimal.class))).andReturn(new BigDecimal("2.22"));
		EasyMock.expect(mockExpenseRepository.save(EasyMock.isA(ExpenseVO.class))).andReturn(dbExpenseVO);
		
		EasyMock.replay(mockExpenseRepository);
		EasyMock.replay(mockVatService);
		
		ExpenseVO returnedExpenseVO = expenseService.saveExpense(newExpenseVO);
		assertNotNull(returnedExpenseVO);
		
		EasyMock.verify(mockExpenseRepository);
		EasyMock.verify(mockVatService);
	}
	@Test
	public void testSaveExpense_SuccessCurrencyIsNotGBP() {
		newExpenseVO.setOriginalCurrency("EUR");
		ExpenseVO dbExpenseVO = new ExpenseVO();		

		EasyMock.expect(mockExchangeRateService.performGBPConversion(EasyMock.anyString(), EasyMock.isA(BigDecimal.class))).andReturn(exchangeRateVO);
		EasyMock.expect(mockVatService.calculateTotalVat(EasyMock.isA(BigDecimal.class))).andReturn(new BigDecimal("2.22"));
		EasyMock.expect(mockExpenseRepository.save(EasyMock.isA(ExpenseVO.class))).andReturn(dbExpenseVO);
		
		EasyMock.replay(mockExpenseRepository);
		EasyMock.replay(mockVatService);
		EasyMock.replay(mockExchangeRateService);
		
		ExpenseVO returnedExpenseVO = expenseService.saveExpense(newExpenseVO);
		assertNotNull(returnedExpenseVO);
		
		EasyMock.verify(mockExpenseRepository);
		EasyMock.verify(mockVatService);
		EasyMock.verify(mockExchangeRateService);
	}
	
	@Test
	public void testSaveExpense_FailedToPerformConversion() {
		newExpenseVO.setOriginalCurrency("EUR");	
		exchangeRateVO = null;
		
		EasyMock.expect(mockExchangeRateService.performGBPConversion(EasyMock.anyString(), EasyMock.isA(BigDecimal.class))).andReturn(exchangeRateVO);

		EasyMock.replay(mockExpenseRepository);

		ExpenseVO returnedExpenseVO = expenseService.saveExpense(newExpenseVO);
		assertNull(returnedExpenseVO);
		
		EasyMock.verify(mockExpenseRepository);
	}
}

