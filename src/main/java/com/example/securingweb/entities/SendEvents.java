/**
 * 
 */
package com.example.securingweb.entities;

import java.util.List;

/**
 * @author User
 *
 */
public class SendEvents {
	private String email;
	private List<Schedule> schedule;
	private String checked;
	
	private Integer brandId;
	private Integer shipId;
	private Integer transmissionTypeId;
	private Integer regionId;
	private Integer pmsId;
	private String dateRange;
	
	public SendEvents(){
		
	}
	
	public SendEvents(String email, List<Schedule> schedule){
		this.email = email;
		this.schedule = schedule;
	}

	
	public Integer getBrandId() {
		return brandId;
	}

	public void setBrandId(Integer brandId) {
		this.brandId = brandId;
	}

	public Integer getShipId() {
		return shipId;
	}

	public void setShipId(Integer shipId) {
		this.shipId = shipId;
	}

	public Integer getTransmissionTypeId() {
		return transmissionTypeId;
	}

	public void setTransmissionTypeId(Integer transmissionTypeId) {
		this.transmissionTypeId = transmissionTypeId;
	}

	public Integer getRegionId() {
		return regionId;
	}

	public void setRegionId(Integer regionId) {
		this.regionId = regionId;
	}

	public Integer getPmsId() {
		return pmsId;
	}

	public void setPmsId(Integer pmsId) {
		this.pmsId = pmsId;
	}

	public String getDateRange() {
		return dateRange;
	}

	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Schedule> getSchedule() {
		return schedule;
	}

	public void setSchedule(List<Schedule> schedule) {
		this.schedule = schedule;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}
	
	

}
