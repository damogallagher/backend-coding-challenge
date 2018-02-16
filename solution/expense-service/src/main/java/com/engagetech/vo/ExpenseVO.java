/*
 * Copyright (c) 2018, Damien Gallagher. All rights reserved.
 *
 */
package com.engagetech.vo;

import java.math.BigDecimal;
import java.util.Calendar;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.engagetech.util.Constants;
import com.engagetech.util.JsonCalendarDeserializer;
import com.engagetech.util.JsonCalendarSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * DAO bean for the Auth table
 * @author damien
 *
 */
@Entity(name = "T_EXPENSE")
public class ExpenseVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;
	
	@NotNull(message="Value cannot be null")
	@DecimalMin(value = "00.00", message="Value must be greater than {value}")
	@Digits(integer=6, fraction=2, message="Value must be in the format 00.00")
	@Column(name = "value", nullable = false)
	private BigDecimal value;
	
	@NotNull(message="Reason cannot be null")
	@Pattern(regexp = Constants.TEXT_REGEX, message="Reason value of '${validatedValue}' contains invalid charachters")
	@Column(name = "reason", nullable = false, length = 500)
	private String reason;
	
	@JsonSerialize(using = JsonCalendarSerializer.class)
	@JsonDeserialize(using = JsonCalendarDeserializer.class)
	@NotNull(message="Date cannot be null")
	@Column(name = "date", nullable = false)
	private Calendar date;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public Calendar getDate() {
		return date;
	}

	public void setDate(Calendar date) {
		this.date = date;
	}

	@Override
	public String toString() {
		return "ExpenseVO [id=" + id + ", reason=" + reason + ", date=" + date + "]";
	}

	
}
