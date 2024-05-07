/**
 * 
 */
package com.example.securingweb.entities;

import java.util.List;

/***
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
***/

import jakarta.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="business_rule", uniqueConstraints = @UniqueConstraint(columnNames = {"region_id","pms_id","transmission_type_id"}))
public class BusinessRule
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable=false, unique=true)
	@NotEmpty(message="{errors.invalid_code}")
	private String code;
	@NotEmpty
	private String description;

	
//	@ManyToOne(cascade = {CascadeType.ALL})
	@ManyToOne()
	@JoinColumn(name="transmission_type_id")
	private TransmissionType transmissionTypeBR;
		
	@ManyToOne()
	@JoinColumn(name="region_id")
	private Region regionBR;
		
	@ManyToOne()
	@JoinColumn(name="pms_id")
	private PMS pmsBR;
	
	@OneToMany(mappedBy="businessRule", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
	private List<AS400BusinessRuleMap> as400BusinessRuleMap;
	
//  removed 04/02/2018 - to make 	business_rule_id nullable 
//	@OneToMany(mappedBy="businessRule", cascade={CascadeType.PERSIST, CascadeType.REMOVE})
//	private List<Schedule> schedule;
	
	private String compoundOffSet;
	
	

	public List<AS400BusinessRuleMap> getAs400BusinessRuleMap() {
		return as400BusinessRuleMap;
	}

	public void setAs400BusinessRuleMap(List<AS400BusinessRuleMap> as400BusinessRuleMap) {
		this.as400BusinessRuleMap = as400BusinessRuleMap;
	}

//	public List<Schedule> getSchedule() {
//		return schedule;
//	}
//
//	public void setSchedule(List<Schedule> schedule) {
//		this.schedule = schedule;
//	}

	public String getCompoundOffSet() {
		return compoundOffSet;
	}

	public void setCompoundOffSet(String compoundOffSet) {
		this.compoundOffSet = compoundOffSet;
	}

	public Region getRegionBR() {
		return regionBR;
	}
	public void setRegionBR(Region regionBR) {
		this.regionBR = regionBR;
	}
	public PMS getPmsBR() {
		return pmsBR;
	}
	public void setPmsBR(PMS pmsBR) {
		this.pmsBR = pmsBR;
	}
	public TransmissionType getTransmissionTypeBR() {
		return transmissionTypeBR;
	}
	public void setTransmissionTypeBR(TransmissionType transmissionTypeBR) {
		this.transmissionTypeBR = transmissionTypeBR;
	}
	
	public Integer getId()
	{
		return id;
	}
	public void setId(Integer id)
	{
		this.id = id;
	}
	public String getCode()
	{
		return code;
	}
	public void setCode(String code)
	{
		this.code = code;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((compoundOffSet == null) ? 0 : compoundOffSet.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((pmsBR == null) ? 0 : pmsBR.hashCode());
		result = prime * result + ((regionBR == null) ? 0 : regionBR.hashCode());
		result = prime * result + ((transmissionTypeBR == null) ? 0 : transmissionTypeBR.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BusinessRule other = (BusinessRule) obj;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
			return false;
		if (compoundOffSet == null) {
			if (other.compoundOffSet != null)
				return false;
		} else if (!compoundOffSet.equals(other.compoundOffSet))
			return false;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (pmsBR == null) {
			if (other.pmsBR != null)
				return false;
		} else if (!pmsBR.equals(other.pmsBR))
			return false;
		if (regionBR == null) {
			if (other.regionBR != null)
				return false;
		} else if (!regionBR.equals(other.regionBR))
			return false;
		if (transmissionTypeBR == null) {
			if (other.transmissionTypeBR != null)
				return false;
		} else if (!transmissionTypeBR.equals(other.transmissionTypeBR))
			return false;
		return true;
	}


	
}
