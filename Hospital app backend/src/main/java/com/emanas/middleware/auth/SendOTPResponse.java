package com.emanas.middleware.auth;

import java.io.Serializable;

public class SendOTPResponse implements Serializable {

	String createdAt;
	String tokenID;
	String purpose;
	String contactVal;
	String contactType;
	String otp;
	String refID;
	String status;

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getTokenID() {
		return tokenID;
	}

	public void setTokenID(String tokenID) {
		this.tokenID = tokenID;
	}

	public String getPurpose() {
		return purpose;
	}

	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}

	public String getContactVal() {
		return contactVal;
	}

	public void setContactVal(String contactVal) {
		this.contactVal = contactVal;
	}

	public String getContactType() {
		return contactType;
	}

	public void setContactType(String contactType) {
		this.contactType = contactType;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getRefID() {
		return refID;
	}

	public void setRefID(String refID) {
		this.refID = refID;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "sendOTPResponse [createdAt=" + createdAt + ", tokenID=" + tokenID + ", purpose=" + purpose
				+ ", contactVal=" + contactVal + ", contactType=" + contactType + ", otp=" + otp + ", refID=" + refID
				+ ", status=" + status + "]";
	}

}
