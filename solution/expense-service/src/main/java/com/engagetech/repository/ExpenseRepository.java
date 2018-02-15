/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.engagetech.vo.ExpenseVO;

/**
 * Jpa interface for interacting with the database for the expense repository
 * @author damien
 *
 */
@RepositoryRestResource(collectionResourceRel = "expenseResource", path = "expenseResource")
public interface ExpenseRepository extends CrudRepository<ExpenseVO, Long> {

	/**
	 * Method to find an auth object by reason
	 * @param reason
	 * @return
	 */
	ExpenseVO findByReason(@Param("reason") String reason);
}
