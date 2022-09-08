package com.emanas.middleware.redis.model;

import java.io.Serializable;

import com.emanas.middleware.models.PatientData;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationData implements Serializable {

	PatientData patientData;
	String authTransID;

	public PatientData getPatientData() {
		return patientData;
	}

	public void setPatientData(PatientData patientData) {
		this.patientData = patientData;
	}

	public String getAuthTransID() {
		return authTransID;
	}

	public void setAuthTransID(String authTransID) {
		this.authTransID = authTransID;
	}

}
