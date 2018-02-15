/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 */
package com.engagetech.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.engagetech.vo.AuthVO;

/**
 * Jpa interface for interacting with the database for the auth repository
 * @author damien
 *
 */
@Repository
public interface AuthRepository extends CrudRepository<AuthVO, Long> {

	/**
	 * Method to find an auth object by apiKey
	 * @param id
	 * @return
	 */
	AuthVO findByApiKey(@Param("apiKey") String key);
}
