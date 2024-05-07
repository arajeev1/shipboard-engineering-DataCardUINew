package com.example.securingweb.entities;

import java.util.Date;
/***
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
**/
import jakarta.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name="Itinerary")
public class Itinerary {
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
//	@Temporal(TemporalType.DATE)
	@Column(name="sailDate", nullable=true)
//	@NotEmpty
	private String sailDate;
	
	private String ShipCode;

	private String PortCode;
	
	private String PortName;
//	@Temporal(TemporalType.DATE)
	@Column(name="ItineraryDate", nullable=true)
	private String ItineraryDate;
	
	@NotEmpty
	private String CruisePackageCode;
	
	public String getCruisePackageCode() {
		return CruisePackageCode;
	}

	public void setCruisePackageCode(String cruisePackageCode) {
		CruisePackageCode = cruisePackageCode;
	}

	
	public Itinerary() {
		super();
	}


	public Integer getId() {
		return id;
	}


	public void setId(Integer id) {
		this.id = id;
	}


	public String getSailDate() {
		return sailDate;
	}


	public void setSailDate(String sailDate) {
		this.sailDate = sailDate;
	}


	public String getShipCode() {
		return ShipCode;
	}


	public void setShipCode(String shipCode) {
		ShipCode = shipCode;
	}


	public String getPortCode() {
		return PortCode;
	}


	public void setPortCode(String portCode) {
		PortCode = portCode;
	}


	public String getPortName() {
		return PortName;
	}


	public void setPortName(String portName) {
		PortName = portName;
	}


	public String getItineraryDate() {
		return ItineraryDate;
	}


	public void setItineraryDate(String itineraryDate) {
		ItineraryDate = itineraryDate;
	}


	

}
