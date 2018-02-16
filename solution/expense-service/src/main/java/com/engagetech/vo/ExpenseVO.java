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
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;


/**
 * DAO bean for the Auth table
 * @author damien
 *
 */
@Entity(name = "T_EXPENSE")
@JsonIgnoreProperties(ignoreUnknown=false)
public class ExpenseVO {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@JsonProperty("id")
	private Integer id;
	
	@NotNull(message="Total Value cannot be null")
	@DecimalMin(value = "00.00", message="Total Value must be greater than {value}")
	@Digits(integer=6, fraction=2, message="Total Value must be in the format 00.00")
	@Column(name = "total_value", nullable = false)
	@JsonProperty("total_value")
	private BigDecimal totalValue;
	
	@Column(name = "value_without_vat", nullable = false)
	@JsonProperty("value_without_vat")
	private BigDecimal valueWithoutVat;
	
	@Column(name = "vat_paid", nullable = false)
	@JsonProperty("vat_paid")
	private BigDecimal vatPaid;
	
	@Column(name = "original_currency", nullable = true, length=10)
	@JsonProperty("original_currency")
	private String originalCurrency;
	
	@Column(name = "exchange_rate", nullable = true)
	@JsonProperty("exchange_rate")
	private BigDecimal exchangeRate;
	
	@Column(name = "original_value", nullable = true)
	@JsonProperty("original_value")
	private BigDecimal originalValue;
	
	@NotNull(message="Reason cannot be null")
	@Pattern(regexp = Constants.TEXT_REGEX, message="Reason value of '${validatedValue}' contains invalid charachters")
	@Column(name = "reason", nullable = false, length = 500)
	@JsonProperty("reason")
	private String reason;
	
	@JsonSerialize(using = JsonCalendarSerializer.class)
	@JsonDeserialize(using = JsonCalendarDeserializer.class)
	@NotNull(message="Date cannot be null")
	@Column(name = "date", nullable = false)
	@JsonProperty("date")
	private Calendar date;

	public Integer getId() {
		return id;
	}

	public String getOriginalCurrency() {
		return originalCurrency;
	}

	public void setOriginalCurrency(String originalCurrency) {
		this.originalCurrency = originalCurrency;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public BigDecimal getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(BigDecimal totalValue) {
		this.totalValue = totalValue;
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

	public BigDecimal getValueWithoutVat() {
		return valueWithoutVat;
	}

	public void setValueWithoutVat(BigDecimal valueWithoutVat) {
		this.valueWithoutVat = valueWithoutVat;
	}

	public BigDecimal getVatPaid() {
		return vatPaid;
	}

	public void setVatPaid(BigDecimal vatPaid) {
		this.vatPaid = vatPaid;
	}

	public BigDecimal getExchangeRate() {
		return exchangeRate;
	}

	public void setExchangeRate(BigDecimal exchangeRate) {
		this.exchangeRate = exchangeRate;
	}

	public BigDecimal getOriginalValue() {
		return originalValue;
	}

	public void setOriginalValue(BigDecimal originalValue) {
		this.originalValue = originalValue;
	}

	@Override
	public String toString() {
		return "ExpenseVO [id=" + id + ", totalValue=" + totalValue + ", valueWithoutVat=" + valueWithoutVat
				+ ", vatPaid=" + vatPaid + ", originalCurrency=" + originalCurrency + ", exchangeRate=" + exchangeRate
				+ ", originalValue=" + originalValue + ", reason=" + reason + ", date=" + date + "]";
	}

	
}
