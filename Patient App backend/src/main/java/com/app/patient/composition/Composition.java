package com.app.patient.composition;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.app.patient.healthRecord.HealthRecord;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "Compositions")
public class Composition implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "cid")
	private Long cid;

	@Column(name = "compositionId")
	private String compositionId;

	@Column(name = "name")
	private String name;

	@Column(name = "createDate")
	private String createDate;

	@Column(name = "healthProfessionalName")
	private String healthProfessionalName;

	@Column(name = "templateId")
	private String templateId;

	@ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER, targetEntity = HealthRecord.class)
	@JoinColumn(name = "episodeId")
	private HealthRecord health;

	public Long getCid() {
		return cid;
	}

	public void setCid(Long cid) {
		this.cid = cid;
	}

	public String getCompositionId() {
		return compositionId;
	}

	public void setCompositionId(String compositionId) {
		this.compositionId = compositionId;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getHealthProfessionalName() {
		return healthProfessionalName;
	}

	public void setHealthProfessionalName(String healthProfessionalName) {
		this.healthProfessionalName = healthProfessionalName;
	}

	public HealthRecord getHealthRecord() {
		return health;
	}

	public void setHealthRecord(HealthRecord health) {
		this.health = health;
	}

}
