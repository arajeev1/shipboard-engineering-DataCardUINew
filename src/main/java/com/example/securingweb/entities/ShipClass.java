/**
 * 
 */
package com.example.securingweb.entities;

import java.util.List;

/***
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
***/

import jakarta.persistence.*;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Table(name="ship_class")
public class ShipClass
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable=false, unique=true)
	@NotEmpty(message="{errors.invalid_code}")
	private String code;
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
	
	@OneToMany(mappedBy="shipClass")
	private List<Ship> ships;
	
	
	public List<Ship> getShips() {
		return ships;
	}
	public void setShips(List<Ship> ships) {
		this.ships = ships;
	}
	
	
	
}
