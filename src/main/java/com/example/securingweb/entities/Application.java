/**
 * 
 */
package com.example.securingweb.entities;

import java.util.List;

//import javax.persistence.CascadeType;
//import javax.persistence.Column;
//import javax.persistence.Entity;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
//import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.JoinTable;
//import javax.persistence.ManyToMany;
//import javax.persistence.Table;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
//import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.NotEmpty;
/**
 *
 */
@Entity
@Table(name="applications")
public class Application
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable=false, unique=true)
	@NotEmpty
	private String name;
	@Column(length=1024)
	private String description;
	@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(
	      name="application_user",
	      joinColumns={@JoinColumn(name="APPLICATION_ID", referencedColumnName="ID")},
	      inverseJoinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")})
	private List<User> owners;
	@Column
	private String os;

	@Column
	private String status;
	
	@ManyToMany(mappedBy="applications")
	private List<Lab> labs;
	
	
	public Application() {
		super();
	}
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	
	public List<Lab> getLabs() {
		return labs;
	}


	public void setLabs(List<Lab> labs) {
		this.labs = labs;
	}





	public List<User> getOwners() {
		return owners;
	}


	public void setOwners(List<User> owners) {
		this.owners = owners;
	}


	public String getOs() {
		return os;
	}


	public void setOs(String os) {
		this.os = os;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Application other = (Application) obj;
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
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
		
	
	
	
}
