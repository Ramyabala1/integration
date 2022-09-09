package com.example.mosip.mosipAuthDemo.authentication;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthPayload {

	@JsonProperty
	AuthRequest request;
	@JsonProperty
	String id;
	@JsonProperty
	String requesttime;
	@JsonProperty
	String version;
	@JsonProperty
	Object metadata;
	
	String transactionID;
	
	String individualId;
	
	String individualIdType;
	
	List<String> otpChannel;
	
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
	public String getIndividualId() {
		return individualId;
	}
	public void setIndividualId(String individualId) {
		this.individualId = individualId;
	}
	public String getIndividualIdType() {
		return individualIdType;
	}
	public void setIndividualIdType(String individualIdType) {
		this.individualIdType = individualIdType;
	}
	public List<String> getOtpChannel() {
		return otpChannel;
	}
	public void setOtpChannel(List<String> otpChannel) {
		this.otpChannel = otpChannel;
	}
	public AuthRequest getRequest() {
		return request;
	}
	public void setRequest(AuthRequest request) {
		this.request = request;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRequesttime() {
		return requesttime;
	}
	public void setRequesttime(String requesttime) {
		this.requesttime = requesttime;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public Object getMetadata() {
		return metadata;
	}
	public void setMetadata(Object metadata) {
		this.metadata = metadata;
	}
	
	
}
