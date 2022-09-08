package com.emanas.middleware.person;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class Person implements Serializable {

	private int ID;

	private String request;

	private String requestID;

	private String otp;

	private String refId;

	private Date dateCreated;

	private String authorisationType;

	private String service;

	private String eManasId;

	private String mosipAuthToken;

	private String authtransactionId;

	private String mosipID;

	private String phoneNumber;

	private String establishmentID;

	private String mhpId;

	public String getEstablishmentID() {
		return establishmentID;
	}

	public void setEstablishmentID(String establishmentID) {
		this.establishmentID = establishmentID;
	}

	public String getMhpId() {
		return mhpId;
	}

	public void setMhpId(String mhpId) {
		this.mhpId = mhpId;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMosipID() {
		return mosipID;
	}

	public void setMosipID(String mosipID) {
		this.mosipID = mosipID;
	}

	public String getAuthtransactionId() {
		return authtransactionId;
	}

	public void setAuthtransactionId(String authtransactionId) {
		this.authtransactionId = authtransactionId;
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String geteManasId() {
		return eManasId;
	}

	public void seteManasId(String eManasId) {
		this.eManasId = eManasId;
	}

	public String getMosipAuthToken() {
		return mosipAuthToken;
	}

	public void setMosipAuthToken(String mosipAuthToken) {
		this.mosipAuthToken = mosipAuthToken;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getRequestID() {
		return requestID;
	}

	public void setRequestID(String requestID) {
		this.requestID = requestID;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Column(name = "status")
	private String status;

	public String getAuthorisationType() {
		return authorisationType;
	}

	public void setAuthorisationType(String authorisationType) {
		this.authorisationType = authorisationType;
	}

	@Override
	public String toString() {
		return "Person [ID=" + ID + ", request=" + request + ", requestID=" + requestID + ", otp=" + otp + ", refId="
				+ refId + ", dateCreated=" + dateCreated + ", authorisationType=" + authorisationType + ", service="
				+ service + ", eManasId=" + eManasId + ", mosipAuthToken=" + mosipAuthToken + ", authtransactionId="
				+ authtransactionId + ", mosipID=" + mosipID + ", phoneNumber=" + phoneNumber + ", status=" + status
				+ "]";
	}

}
