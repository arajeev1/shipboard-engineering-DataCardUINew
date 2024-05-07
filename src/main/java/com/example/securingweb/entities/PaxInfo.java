package com.example.securingweb.entities;


/**
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
***/

import jakarta.persistence.*;

@Entity
@Table(name="paxinfo")
public class PaxInfo {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(length=10)
	private String cardNo;
	@Column(length=8)
	private String bkgNo;
	@Column(length=9)
	private String billingNo;
	@Column(length=15)
	private String firstName;
	@Column(length=20)
	private String lastName;
	@Column(length=10)
	private String embarkDate;
	@Column(length=10)
	private String debarkDate;
	
	public PaxInfo() {
		super();
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCardNo() {
		return cardNo;
	}

	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}

	public String getBkgNo() {
		return bkgNo;
	}

	public void setBkgNo(String bkgNo) {
		this.bkgNo = bkgNo;
	}

	public String getBillingNo() {
		return billingNo;
	}

	public void setBillingNo(String billingNo) {
		this.billingNo = billingNo;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmbarkDate() {
		return embarkDate;
	}

	public void setEmbarkDate(String embarkDate) {
		this.embarkDate = embarkDate;
	}

	public String getDebarkDate() {
		return debarkDate;
	}

	public void setDebarkDate(String debarkDate) {
		this.debarkDate = debarkDate;
	}
	
	

}
