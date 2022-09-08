package com.emanas.middleware.redis.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public class PatientOrgMapping implements Serializable {

	String patientID;
	String establishment;
	String id;
	String mobile;
	List<String> otpChannel;
	Date timeStamp;
	String service;

	enum serviceName {
	}

	public String getPatientID() {
		return patientID;
	}

	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}

	public String getEstablishment() {
		return establishment;
	}

	public void setEstablishment(String establishment) {
		this.establishment = establishment;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public List<String> getOtpChannel() {
		return otpChannel;
	}

	public void setOtpChannel(List<String> otpChannel) {
		this.otpChannel = otpChannel;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	};

}
