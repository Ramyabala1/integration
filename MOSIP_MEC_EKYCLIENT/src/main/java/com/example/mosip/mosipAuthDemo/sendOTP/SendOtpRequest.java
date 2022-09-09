package com.example.mosip.mosipAuthDemo.sendOTP;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SendOtpRequest {

	
	@JsonProperty
	String id;
	@JsonProperty
	String requestTime;
	@JsonProperty
	String version;
	
	//String env;
	
	/*public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	public String getDomainUri() {
		return domainUri;
	}
	public void setDomainUri(String domainUri) {
		this.domainUri = domainUri;
	}
	*/
	//String domainUri;
	
	String transactionID;
	
	String individualId;
	
	String individualIdType;
	
	List<String> otpChannel;
	
	
	
	
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
	
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRequestTime() {
		return requestTime;
	}
	public void setRequestTime(String requesttime) {
		this.requestTime = requesttime;
	}
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public void setenv(String string) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
