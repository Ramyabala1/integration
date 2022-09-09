package com.example.mosip.mosipAuthDemo.createOtpEKycRequest;

public class EkycRequestPayload {

	
	
	String transactionID;
	String timestamp;
	String otp;
	
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getOtp() {
		return otp;
	}
	public void setOtp(String otp) {
		this.otp = otp;
	}
	public String getTransactionID() {
		return transactionID;
	}
	public void setTransactionID(String transactionID) {
		this.transactionID = transactionID;
	}
	
	
	
	
	
	
	
}
