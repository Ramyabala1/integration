package com.app.patient.healthRecord;

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
@Table(name = "ehr_hip_context")
public class HealthRecord implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "patientID")
	private String emanas;

	@Column(name = "healthcareEstablishment")
	private String healthcareEstablishment;

	@Column(name = "episodeId")
	private String episodeId;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "createDate")
	private Date createDate;

	@Column(name = "healthProfessionalName")
	private String healthProfessionalName;

	@Column(name = "consentId")
	private String consentId;

	@Column(name = "orgUUID")
	private String orgUUID;

	@Column(name = "isLinked")
	private Boolean isLinked;

	@Transient

	private String transactionId;

	@Transient

	private String consentTransactionID;

	@Transient

	private String healthData;

//	@ElementCollection
//	    private List<Composition> composition;
//
//
//	    @OneToMany(mappedBy = "composition", cascade = CascadeType.ALL, fetch = FetchType.EAGER,
//				 targetEntity=Composition.class)
//	 public List<Composition> getComposition() {
//	        return composition;
//	    }
//
//	    public void setComposition(List<Composition> composition) {
//	        this.composition = composition;
//	    }

	public String getHealthData() {
		return healthData;
	}

	public void setHealthData(String healthData) {
		this.healthData = healthData;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public String getHealthcareEstablishment() {
		return healthcareEstablishment;
	}

	public void setHealthcareEstablishment(String healthcareEstablishment) {
		this.healthcareEstablishment = healthcareEstablishment;
	}

	public String getEpisodeId() {
		return episodeId;
	}

	public void setEpisodeId(String episodeId) {
		this.episodeId = episodeId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getHealthProfessionalName() {
		return healthProfessionalName;
	}

	public void setHealthProfessionalName(String healthProfessionalName) {
		this.healthProfessionalName = healthProfessionalName;
	}

	public String getConsentId() {
		return consentId;
	}

	public void setConsentId(String consentId) {
		this.consentId = consentId;
	}

	public String getOrgUUID() {
		return orgUUID;
	}

	public void setOrgUUID(String orgUUID) {
		this.orgUUID = orgUUID;
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

}
