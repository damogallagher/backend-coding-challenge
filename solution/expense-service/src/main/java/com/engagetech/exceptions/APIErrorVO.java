/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 *
 */
package com.engagetech.exceptions;

import java.util.Arrays;
import java.util.List;

import org.springframework.http.HttpStatus;

/**
 * Generic class to hold exception information
 * @author damien
 *
 */
public class APIErrorVO {
	 
    private HttpStatus status;
    private String message;
    private List<String> errors;
 
    public APIErrorVO(HttpStatus status, String message, List<String> errors) {
        super();
        this.status = status;
        this.message = message;
        this.errors = errors;
    }
 
    public APIErrorVO(HttpStatus status, String message, String error) {
        super();
        this.status = status;
        this.message = message;
        errors = Arrays.asList(error);
    }
    
    public HttpStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public List<String> getErrors() {
        return errors;
    }
}