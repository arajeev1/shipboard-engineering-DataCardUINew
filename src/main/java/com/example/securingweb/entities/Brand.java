/**
 * 
 */
package com.example.securingweb.entities;

import java.util.List;

/****
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
***/

import jakarta.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="brand")
public class Brand
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable=false, unique=true)
	@NotEmpty(message="{errors.invalid_code}")
	public String code;
	@NotEmpty
	private String description;
	@NotEmpty
	private String style;
	
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
	public String getStyle()
	{
		return style;
	}
	public void setStyle(String style)
	{
		this.style = style;
	}
	
	@OneToMany(mappedBy="brand")
	private List<Ship> ships;
	
//	@ManyToMany(mappedBy="brand")
//	private List<TransmissionType> transmissionType;
	
	@ManyToMany()
	@JoinTable(
		name="brand_transmissionType",
		joinColumns={@JoinColumn(name="BRAND_ID", referencedColumnName="ID")},
		inverseJoinColumns={@JoinColumn(name="TRANSMISSION_TYPE_ID", referencedColumnName="ID")})
	private List<TransmissionType> transmissionType;
	
	
	public List<TransmissionType> getTransmissionType() {
		return transmissionType;
	}
	public void setTransmissionType(List<TransmissionType> transmissionType) {
		this.transmissionType = transmissionType;
	}
	
	public List<Ship> getShips() {
		return ships;
	}
	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}
	
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((code == null) ? 0 : code.hashCode());
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((style == null) ? 0 : style.hashCode());
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
		Brand other = (Brand) obj;
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
		if (style == null) {
			if (other.style != null)
				return false;
		} else if (!style.equals(other.style))
			return false;
		return true;
	}
	
	
}
