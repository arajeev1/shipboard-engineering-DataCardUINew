/**
 * 
 */
package com.example.securingweb.entities;

import java.util.Date;

/***
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
**/


import jakarta.persistence.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


/**
 *
 */
@Entity
@Table(name="schedule")
public class Schedule
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name="user_name")
	private User user;
	
	@Temporal(TemporalType.DATE)
	@Column(name="startDate", nullable=false)
//	@NotEmpty
	private Date startDate;
	
	@Temporal(TemporalType.DATE)
	@Column(name="endDate", nullable=true)
//	@NotEmpty
	private Date endDate;
	
	@NotEmpty
	private String startTime;
	
	//@NotEmpty
	@Column(nullable=true)
	private String description;
	
	@NotEmpty
	private String title;
	
	private int status;
	
//	@JsonIgnore
//	@ManyToOne
//	@JoinColumn(name="brand_id")
//	@JsonIgnoreProperties("brand_id")
//	private Brand brand_id;
	
	@JsonIgnore
	@ManyToOne(optional=false, cascade={CascadeType.PERSIST})
	@JoinColumn(name="business_rule_id")
	private BusinessRule businessRule;
	

	public BusinessRule getBusinessRule() {
		return businessRule;
	}

	public void setBusinessRule(BusinessRule businessRule) {
		this.businessRule = businessRule;
	}
	@Column(name="brand_id",nullable=true)
	private String brand_id;

	@Column(name="transmission_type_id",nullable=true)
	private String transmission_type_id;

	@Column(name="ship_id",nullable=true)
	private String ship_id;

	@Column(name="region_id",nullable=true)
	private String region_id;

	@Column(name="pms_id",nullable=true)
	private String pms_id;

	@Transient
	private String color;
	
	@Column(name="isManualEntry" , columnDefinition = "TINYINT(1) DEFAULT 0")
	private int isManualEntry ;
	
	@Column(name="isDeleted" , columnDefinition = "TINYINT(1) DEFAULT 0")
	private int isDeleted ;
	
	//@NotEmpty
	private String voyageNumber;
	
	
//	public Brand getBrand() {
//		return brand_id;
//	}
//
//	public void setBrand(Brand brand) {
//		this.brand_id = brand;
//	}

	
	public String getVoyageNumber() {
		return voyageNumber;
	}


	public int getIsManualEntry() {
		return isManualEntry;
	}

	public void setIsManualEntry(int isManualEntry) {
		this.isManualEntry = isManualEntry;
	}


	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public void setVoyageNumber(String voyageNumber) {
		this.voyageNumber = voyageNumber;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getBrand_id() {
		return brand_id;
	}

	public void setBrand_id(String brand_id) {
		this.brand_id = brand_id;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getTransmission_type_id() {
		return transmission_type_id;
	}

	public void setTransmission_type_id(String transmission_type_id) {
		this.transmission_type_id = transmission_type_id;
	}

	public String getShip_id() {
		return ship_id;
	}

	public void setShip_id(String ship_id) {
		this.ship_id = ship_id;
	}

	public String getRegion_id() {
		return region_id;
	}

	public void setRegion_id(String region_id) {
		this.region_id = region_id;
	}

	public String getPms_id() {
		return pms_id;
	}

	public void setPms_id(String pms_id) {
		this.pms_id = pms_id;
	}

	
	
}
