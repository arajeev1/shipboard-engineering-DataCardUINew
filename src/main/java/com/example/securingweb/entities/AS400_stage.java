/**
 * 
 */
package com.example.securingweb.entities;

import java.util.List;

/**
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
**/
import jakarta.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;




@Entity
@Table(name="AS400Data_stage")
public class AS400_stage
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
//	@Column(nullable=false, unique=true)
//	@NotEmpty(message="{errors.invalid_code}")
	private String ship;
//	@Temporal(TemporalType.DATE)
//	@Column(name="startDate", nullable=false)
	private String sailingDate;
	@NotEmpty
	private String embarkingPort;
	@NotEmpty
	private String voyageNumber;
	
//
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getShip() {
		return ship;
	}
	public void setShip(String ship) {
		this.ship = ship;
	}
	public String getSailingDate() {
		return sailingDate;
	}
	public void setSailingDate(String sailingDate) {
		this.sailingDate = sailingDate;
	}

	public String getEmbarkingPort() {
		return embarkingPort;
	}
	public void setEmbarkingPort(String embarkingPort) {
		this.embarkingPort = embarkingPort;
	}

	public String getVoyageNumber() {
		return voyageNumber;
	}

	public void setVoyageNumber(String voyageNumber) {
		this.voyageNumber = voyageNumber;
	}

	
}
