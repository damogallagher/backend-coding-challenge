/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.rest;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.engagetech.service.IExpenseService;
import com.engagetech.util.JsonUtils;
import com.engagetech.vo.ExpenseVO;

@RunWith(EasyMockRunner.class)
public class ExpenseRestControllerTest {

	@TestSubject
	private ExpenseRestController expenseRestController = new ExpenseRestController();

	@Mock
	private IExpenseService mockExpenseService;
	
	private ExpenseVO expenseVO;
	private MockMvc mockMvc;
	@Before
	public void setUp() {
		
		// Setup Spring test in standalone mode
		this.mockMvc = MockMvcBuilders.standaloneSetup(expenseRestController).build();	

		expenseVO = new ExpenseVO();
		expenseVO.setId(1);
		expenseVO.setReason("reason");
		expenseVO.setValue(new BigDecimal("00.55"));
		expenseVO.setDate(Calendar.getInstance());
		
	}
	
	@Test
	public void testGetExpenses_NullExpensesReturned() throws Exception {
		List<ExpenseVO> expenseVOList = null;

		EasyMock.expect(mockExpenseService.getAllExpenses()).andReturn(expenseVOList);
		
		EasyMock.replay(mockExpenseService);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/")
		.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("[0].id").doesNotExist())
		.andExpect(jsonPath("[0].reason").doesNotExist())
		.andExpect(jsonPath("[0].value").doesNotExist())		
		.andExpect(jsonPath("[0].date").doesNotExist());
		
		EasyMock.verify(mockExpenseService);
	}
	@Test
	public void testGetExpenses_0ExpensesReturned() throws Exception {
		List<ExpenseVO> expenseVOList = new LinkedList<>();

		EasyMock.expect(mockExpenseService.getAllExpenses()).andReturn(expenseVOList);
		
		EasyMock.replay(mockExpenseService);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/")
		.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("[0].id").doesNotExist())
		.andExpect(jsonPath("[0].reason").doesNotExist())
		.andExpect(jsonPath("[0].value").doesNotExist())		
		.andExpect(jsonPath("[0].date").doesNotExist());
		
		EasyMock.verify(mockExpenseService);
	}
	
	@Test
	public void testGetExpenses_1ExpenseReturned() throws Exception {
		List<ExpenseVO> expenseVOList = new LinkedList<>();
		expenseVOList.add(expenseVO);
		EasyMock.expect(mockExpenseService.getAllExpenses()).andReturn(expenseVOList);
		
		EasyMock.replay(mockExpenseService);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/")
		.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("[0].id").value(expenseVOList.get(0).getId()))
		.andExpect(jsonPath("[0].reason").value(expenseVOList.get(0).getReason()))
		.andExpect(jsonPath("[0].value").value(expenseVOList.get(0).getValue()))		
		.andExpect(jsonPath("[0].date").exists());
		
		EasyMock.verify(mockExpenseService);
	}
	
	@Test
	public void testGetExpenses_2ExpensesReturned() throws Exception {
		List<ExpenseVO> expenseVOList = new LinkedList<>();
		expenseVOList.add(expenseVO);
		expenseVOList.add(expenseVO);
		EasyMock.expect(mockExpenseService.getAllExpenses()).andReturn(expenseVOList);
		
		EasyMock.replay(mockExpenseService);
		
		mockMvc.perform(MockMvcRequestBuilders.get("/")
		.contentType(MediaType.APPLICATION_JSON_VALUE))
		.andExpect(status().isOk())
		.andExpect(jsonPath("[0].id").value(expenseVOList.get(0).getId()))
		.andExpect(jsonPath("[0].reason").value(expenseVOList.get(0).getReason()))
		.andExpect(jsonPath("[0].value").value(expenseVOList.get(0).getValue()))		
		.andExpect(jsonPath("[0].date").exists())
		.andExpect(jsonPath("[1].id").value(expenseVOList.get(1).getId()))
		.andExpect(jsonPath("[1].reason").value(expenseVOList.get(1).getReason()))
		.andExpect(jsonPath("[1].value").value(expenseVOList.get(1).getValue()))		
		.andExpect(jsonPath("[1].date").exists());
		
		EasyMock.verify(mockExpenseService);
	}
	@Test
	public void testSaveExpenses_FailedToSave() throws Exception {
		EasyMock.expect(mockExpenseService.saveExpense(EasyMock.isA(ExpenseVO.class))).andReturn(null);
		
		EasyMock.replay(mockExpenseService);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/")
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(JsonUtils.convertObjectToJson(expenseVO)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("id").doesNotExist())
		.andExpect(jsonPath("reason").doesNotExist())
		.andExpect(jsonPath("value").doesNotExist())
		.andExpect(jsonPath("date").doesNotExist());
		
		EasyMock.verify(mockExpenseService);
	}
	@Test
	public void testSaveExpenses_1ExpenseReturned() throws Exception {
		EasyMock.expect(mockExpenseService.saveExpense(EasyMock.isA(ExpenseVO.class))).andReturn(expenseVO);
		
		EasyMock.replay(mockExpenseService);
		
		mockMvc.perform(MockMvcRequestBuilders.post("/")
		.contentType(MediaType.APPLICATION_JSON_VALUE)
		.content(JsonUtils.convertObjectToJson(expenseVO)))
		.andExpect(status().isOk())
		.andExpect(jsonPath("id").value(expenseVO.getId()))
		.andExpect(jsonPath("reason").value(expenseVO.getReason()))
		.andExpect(jsonPath("value").value(expenseVO.getValue()))
		.andExpect(jsonPath("date").exists());
		
		EasyMock.verify(mockExpenseService);
	}
}
