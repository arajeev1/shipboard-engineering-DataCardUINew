/**
 * 
 */
package com.example.securingweb.entities;

/**
 * @author User
 *
 */
public class SearchEvents {
	
	private Integer id;
	private Integer brandId;
	private Integer shipId;
	private Integer transmissionTypeId;
	private Integer regionId;
	private Integer pmsId;
	private String dateRange;
	
	

	public SearchEvents(Integer brandId, Integer shipId, Integer transmissionTypeId, Integer regionId, Integer pmsId,
			String dateRange) {
		super();
		this.brandId = brandId;
		this.shipId = shipId;
		this.transmissionTypeId = transmissionTypeId;
		this.regionId = regionId;
		this.pmsId = pmsId;
		this.dateRange = dateRange;
	}
	
	public SearchEvents(){};

	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
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
	public String getDateRange() {
		return dateRange;
	}
	public void setDateRange(String dateRange) {
		this.dateRange = dateRange;
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

}
