package com.example.securingweb.entities;


/**
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
**/

import jakarta.persistence.*;

@Entity
@Table(name="paxsales")
public class PaxSales {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(length=1)
	private Integer passNo;
	@Column(length=9)
	private Integer billingNo;
	@Column(length=10)
	private Integer checkNum;
	@Column(length=11)
	private String totalAmt;
	@Column(length=7)
	private String shipDept;
	@Column(length=50)
	private String saleDtl;
	@Column(length=10)
	private String saleDate;
	@Column(length=5)
	private String saleTime;
	@Column(length=5)
	private String voyageID;
	@Column(length=12)
	private String cruiseID;
	
	public PaxSales() {
		super();
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getPassNo() {
		return passNo;
	}
	public void setPassNo(Integer passNo) {
		this.passNo = passNo;
	}
	public Integer getBillingNo() {
		return billingNo;
	}
	public void setBillingNo(Integer billingNo) {
		this.billingNo = billingNo;
	}
	public Integer getCheckNum() {
		return checkNum;
	}
	public void setCheckNum(Integer checkNum) {
		this.checkNum = checkNum;
	}
	public String getTotalAmt() {
		return totalAmt;
	}
	public void setTotalAmt(String totalAmt) {
		this.totalAmt = totalAmt;
	}
	public String getShipDept() {
		return shipDept;
	}
	public void setShipDept(String shipDept) {
		this.shipDept = shipDept;
	}
	public String getSaleDtl() {
		return saleDtl;
	}
	public void setSaleDtl(String saleDtl) {
		this.saleDtl = saleDtl;
	}
	public String getSaleDate() {
		return saleDate;
	}
	public void setSaleDate(String saleDate) {
		this.saleDate = saleDate;
	}
	public String getSaleTime() {
		return saleTime;
	}
	public void setSaleTime(String saleTime) {
		this.saleTime = saleTime;
	}
	public String getVoyageID() {
		return voyageID;
	}
	public void setVoyageID(String voyageID) {
		this.voyageID = voyageID;
	}
	public String getCruiseID() {
		return cruiseID;
	}
	public void setCruiseID(String cruiseID) {
		this.cruiseID = cruiseID;
	}

	

}
