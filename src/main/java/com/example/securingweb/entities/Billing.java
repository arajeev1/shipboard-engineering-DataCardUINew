package com.example.securingweb.entities;

/***
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
**/

import jakarta.persistence.*;

@Entity
@Table(name="billing")
public class Billing {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(length=9)
	private Integer billingNo;
	@Column(length=6)
	private Integer cabinNo;
	@Column(length=1)
	private String billType;
	@Column(length=2)
	private String ccCode;
	@Column(length=5)
	private String ccExpires;
	@Column(length=2)
	private String creditType;
	@Column(length=12)
	private String chargesTtl;
	
	public Billing() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getBillingNo() {
		return billingNo;
	}

	public void setBillingNo(Integer billingNo) {
		this.billingNo = billingNo;
	}

	public Integer getCabinNo() {
		return cabinNo;
	}

	public void setCabinNo(Integer cabinNo) {
		this.cabinNo = cabinNo;
	}

	public String getBillType() {
		return billType;
	}

	public void setBillType(String billType) {
		this.billType = billType;
	}

	public String getCcCode() {
		return ccCode;
	}

	public void setCcCode(String ccCode) {
		this.ccCode = ccCode;
	}

	public String getCcExpires() {
		return ccExpires;
	}

	public void setCcExpires(String ccExpires) {
		this.ccExpires = ccExpires;
	}

	public String getCreditType() {
		return creditType;
	}

	public void setCreditType(String creditType) {
		this.creditType = creditType;
	}

	public String getChargesTtl() {
		return chargesTtl;
	}

	public void setChargesTtl(String chargesTtl) {
		this.chargesTtl = chargesTtl;
	}

	

}
