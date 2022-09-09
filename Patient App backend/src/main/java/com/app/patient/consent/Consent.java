package com.app.patient.consent;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Consents")
public class Consent implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "patientID")
	private String emanas;

	@Column(name = "Individual_Id")
	private String individualId;

	@Column(name = "Individual_Name")
	private String individual_name;

	@Column(name = "health_provider")
	private String health_provider;

	@Column(name = "consentId")
	private String consentId;

	@Transient
	private String transactionId;

	public Consent(Long id, String emanas, String individualId, String individual_name, String health_provider,
			String consentId) {
		super();
		this.id = id;
		this.emanas = emanas;
		this.individualId = individualId;
		this.individual_name = individual_name;
		this.health_provider = health_provider;
		this.consentId = consentId;

	}

	public String getConsentId() {
		return consentId;
	}

	public void setConsentId(String consentId) {
		this.consentId = consentId;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
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

	public String getIndividualId() {
		return individualId;
	}

	public void setIndividualId(String individualId) {
		this.individualId = individualId;
	}

	public String getIndividual_name() {
		return individual_name;
	}

	public void setIndividual_name(String individual_name) {
		this.individual_name = individual_name;
	}

	public String getHealth_provider() {
		return health_provider;
	}

	public void setHealth_provider(String health_provider) {
		this.health_provider = health_provider;
	}

}
