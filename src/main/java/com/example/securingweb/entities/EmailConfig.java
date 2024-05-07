/**
 * 
 */
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

/**
 * @author User
 *
 */
@Entity
@Table(name="email_config")
public class EmailConfig {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Integer id;
	
	@NotEmpty
	private String emailHost;
	
	@NotEmpty
	private String emailUsername;
	
	@NotEmpty
	private String emailPassword;
	
	@NotEmpty
	private String emailPort;
	
	@NotEmpty
	private String createdBy;
	
	@Temporal(TemporalType.DATE)
	@Column(name="createDate", nullable=false)
	private Date createDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmailHost() {
		return emailHost;
	}

	public void setEmailHost(String emailHost) {
		this.emailHost = emailHost;
	}

	public String getEmailUsername() {
		return emailUsername;
	}

	public void setEmailUsername(String emailUsername) {
		this.emailUsername = emailUsername;
	}

	public String getEmailPassword() {
		return emailPassword;
	}

	public void setEmailPassword(String emailPassword) {
		this.emailPassword = emailPassword;
	}

	public String getEmailPort() {
		return emailPort;
	}

	public void setEmailPort(String emailPort) {
		this.emailPort = emailPort;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	
}
