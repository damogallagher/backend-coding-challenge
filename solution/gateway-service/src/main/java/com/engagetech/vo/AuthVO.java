/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 * */
package com.engagetech.vo;

import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

/**
 * DAO bean for the Auth table
 * @author damien
 *
 */
@Entity(name = "T_AUTH")
public class AuthVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	@NotNull
	@Column(name = "api_key", nullable = false, length = 32)
	private String apiKey;

	@NotNull
	@Column(name = "name", nullable = false, length = 100)
	private String name;
	
	@NotNull
	@Column(name = "email_address", nullable = false, length = 200)
	private String emailAddress;
	
	@NotNull
	@Column(name = "date_added", nullable = false)
	private Calendar dateAdded;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getApiKey() {
		return apiKey;
	}

	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailAddress() {
		return emailAddress;
	}

	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}

	public Calendar getDateAdded() {
		return dateAdded;
	}

	public void setDateAdded(Calendar dateAdded) {
		this.dateAdded = dateAdded;
	}

	@Override
	public String toString() {
		return "AuthVO [id=" + id + ", apiKey=" + apiKey + ", name=" + name + ", emailAddress=" + emailAddress
				+ ", dateAdded=" + dateAdded + "]";
	}
}
