package com.app.patient.user;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "person_register")
public class Person implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "patientID")
	private String emanas;

	@Column(name = "aeID")
	private String aeID;

	@Column(name = "type")
	private String type;

	@Column(name = "login")
	private String login;

	@Column(name = "password")
	private String password;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "CREATED_TIME")
	private Date creationTime;

	@Transient
	private String transctionId;

	public Person(String emanas, String login, String password) {
		super();

		this.emanas = emanas;
		this.login = login;
		this.password = password;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmanas() {
		return emanas;
	}

	public void setEmanas(String emanas) {
		this.emanas = emanas;
	}

	public String getaeId() {
		return aeID;
	}

	public void setaeId(String aeID) {
		this.aeID = aeID;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getCreationTime() {
		return creationTime;
	}

	public void setCreationTime(Date creationTime) {
		this.creationTime = creationTime;
	}

	public String getTransctionId() {
		return transctionId;
	}

	public void setTransctionId(String transctionId) {
		this.transctionId = transctionId;
	}

}
