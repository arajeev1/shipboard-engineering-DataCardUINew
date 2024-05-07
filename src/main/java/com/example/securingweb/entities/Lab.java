/**
 * 
 */
package com.example.securingweb.entities;

import java.util.List;

import org.hibernate.validator.constraints.NotEmpty;

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
import javax.persistence.Table;
import javax.validation.constraints.Size;
**/
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
import jakarta.validation.constraints.Size;


@Entity
@Table(name="labs")
public class Lab
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	@Column(nullable=false, unique=true)
	@NotEmpty(message="{errors.invalid_name}")
	@Size(max=24)
	private String name;
//	@Column(length=1024)
	private String description;
//	@Column(nullable=true)
//	private List<Application> applications;

	@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(
		name="labs_applications",
		joinColumns={@JoinColumn(name="LAB_ID", referencedColumnName="ID")},
		inverseJoinColumns={@JoinColumn(name="APPLICATION_ID", referencedColumnName="ID")})
	private List<Application> applications;

	@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(
			name="users_labs",
			joinColumns={@JoinColumn(name="LAB_ID", referencedColumnName="ID")},
			inverseJoinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")})
	private List<User> owners;
	
	@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(
			name="currentUsers_labs",
			joinColumns={@JoinColumn(name="LAB_ID", referencedColumnName="ID")},
			inverseJoinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")})
	private List<User> currentUsers;
	
	@ManyToMany(cascade=CascadeType.MERGE)
	@JoinTable(
			name="PMs_labs",
			joinColumns={@JoinColumn(name="LAB_ID", referencedColumnName="ID")},
			inverseJoinColumns={@JoinColumn(name="USER_ID", referencedColumnName="ID")})
	private List<User> projectMgr;
	
//	@Column
//	private List<String> currentUsers;
//	
////	@NotEmpty
//	@Column
//	private List<String> projectMgr;
//	
//	12:13:24.229 [localhost-startStop-1] ERROR o.s.b.c.e.tomcat.TomcatStarter - Error starting Tomcat context. Exception: org.springframework.beans.factory.UnsatisfiedDependencyException. Message: Error creating bean with name 'webConfig': Unsatisfied dependency expressed through field 'postAuthorizationFilter'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'postAuthorizationFilter': Unsatisfied dependency expressed through field 'securityService'; nested exception is org.springframework.beans.factory.UnsatisfiedDependencyException: Error creating bean with name 'securityService': Unsatisfied dependency expressed through field 'userRepository'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'userRepository': Cannot create inner bean '(inner bean)#60c061b8' of type [org.springframework.orm.jpa.SharedEntityManagerCreator] while setting bean property 'entityManager'; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name '(inner bean)#60c061b8': Cannot resolve reference to bean 'entityManagerFactory' while setting constructor argument; nested exception is org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'entityManagerFactory' defined in class path resource [org/springframework/boot/autoconfigure/orm/jpa/HibernateJpaAutoConfiguration.class]: Invocation of init method failed; nested exception is javax.persistence.PersistenceException: [PersistenceUnit: default] Unable to build Hibernate SessionFactory
	@NotEmpty
	private String os;
	
	private String status;


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

	public String getOs() {
		return os;
	}

	public void setOs(String os) {
		this.os = os;
	}

	public List<Application> getApplications() {
		return applications;
	}

	public void setApplications(List<Application> applications) {
		this.applications = applications;
	}

	public List<User> getCurrentUsers() {
		return currentUsers;
	}

	public void setCurrentUsers(List<User> currentUsers) {
		this.currentUsers = currentUsers;
	}

	public List<User> getProjectMgr() {
		return projectMgr;
	}

	public void setProjectMgr(List<User> projectMgr) {
		this.projectMgr = projectMgr;
	}

	public List<User> getOwners() {
		return owners;
	}

	public void setOwners(List<User> owners) {
		this.owners = owners;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

}
