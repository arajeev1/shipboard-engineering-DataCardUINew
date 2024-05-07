/**
 * 
 */
package com.example.securingweb.entities;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

/***
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
***/
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;






@Entity
@Table(name="transmission_type")
public class TransmissionType
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable=false, unique=true)
	@NotEmpty(message="{errors.invalid_code}")
	private String code;
	@NotEmpty
	private String description;
	@NotNull
	private Integer offSetTransmission;
	@NotNull
	private Integer offSetTransmissionHours;	
	@JsonIgnore
	@OneToMany(mappedBy="transmissionTypeBR")
	private List<BusinessRule> businessRule;

	
	/**
	 @ManyToMany(fetch = FetchType.LAZY,
		      cascade = {
		          CascadeType.PERSIST,
		          CascadeType.MERGE
		      })
		  @JoinTable(name = "brand_transmission_type",
		        joinColumns = { @JoinColumn(name = "transmission_type_id") },
		        inverseJoinColumns = { @JoinColumn(name = "brand_id") })
	**/
	
	public Integer getOffSetTransmissionHours() {
		return offSetTransmissionHours;
	}
	public void setOffSetTransmissionHours(Integer offSetTransmissionHours) {
		this.offSetTransmissionHours = offSetTransmissionHours;
	}
	public List<BusinessRule> getBusinessRule() {
		return businessRule;
	}
	public void setBusinessRule(List<BusinessRule> businessRule) {
		this.businessRule = businessRule;
	}

	public Integer getOffSetTransmission() {
		return offSetTransmission;
	}
	public void setOffSetTransmission(Integer offSetTransmission) {
		this.offSetTransmission = offSetTransmission;
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
	
	@NotEmpty
	private String style;
	@JsonIgnore
	@ManyToMany(mappedBy="transmissionType")
	private List<Brand> brand;
	
	public List<Brand> getBrand() {
		return brand;
	}
	public void setBrand(List<Brand> brand) {
		this.brand = brand;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}

	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((brand == null) ? 0 : brand.hashCode());
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		TransmissionType other = (TransmissionType) obj;
		if (brand == null) {
			if (other.brand != null)
				return false;
		} else if (!brand.equals(other.brand))
			return false;
		if (code == null) {
			if (other.code != null)
				return false;
		} else if (!code.equals(other.code))
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
		return true;
	}

	
	
}
