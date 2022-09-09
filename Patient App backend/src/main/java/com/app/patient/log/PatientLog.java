package com.app.patient.log;

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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "patient_log")
public class PatientLog implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "ID")
	private Long id;

	@Column(name = "patientID")
	private String patientID;

	@Column(name = "Activity ")
	private String activity;

	@Column(name = "healthResource")
	private String healthResource;

	@Temporal(value = TemporalType.TIMESTAMP)
	@Column(name = "createdDate")
	private Date createdDate;

	@Column(name = "healthProfessionalName")
	private String healthProfessionalName;

	@Column(name = "mhpId")
	private String mhpId;

	@Column(name = "mhpRole")
	private String mhpRole;

	@Column(name = "hiu")
	private String hiu;

	@Column(name = "hip")
	private String hip;

	@Column(name = "source")
	private String source;

}
