package com.app.patient.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

@JsonInclude(Include.NON_NULL)
public class ResponseConfig {
	int code,status;
	String  errors = "" , errorcode="", response="",action = "",transactionId="",
			api="",version="", message = "",consentID;
	Date timeStamp;


	
	
	public String getApi() {
		return api;
	}

	public void setApi(String api) {
		this.api = api;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getErrorcode() {
		return errorcode;
	}

	public void setErrorcode(String errorcode) {
		this.errorcode = errorcode;
	}

	public String getConsentID() {
		return consentID;
	}

	public void setConsentID(String consentID) {
		this.consentID = consentID;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the status
	 */
	
	/**
	 * @param status the status to set
	 */
	

	/**
	 * @return the location
	 */
	

	/**
	 * @param location the location to set
	 */
	

	
	
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	
	
	
	public String getMessage() {
		return message;
	}

	@JsonIgnore
	public int getStatus() {
		return status;
	}
	@JsonIgnore
	public void setStatus(int status) {
		this.status = status;
	}

	public void setMessage(String string) {
		this.message = string;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

	public Date getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(Date timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String createResponse(int objCode, int objStatus, String objLocation, String objMessage,String res)
	{
		
		
		
		String response="{\n" + 
				"		    \"code\": "+code+",\n" + 
			
				"		    \"errors\": \""+errors+"\",\n" + 
					   
				"		    \"response\": "+res+"\n" + 
				"		}";
		
		return response;
	}

	
	
	
	

	

	
	@Override
	public String toString() {
		return "ResponseConfig [code=" + code + ", errors=" + errors + ", errorcode=" + errorcode + ", response="
				+ response + ", action=" + action + ", transactionId=" + transactionId + ", api=" + api + ", version="
				+ version + ", message=" + message + ", consentID=" + consentID + ", timeStamp=" + timeStamp + "]";
	}

	public String getErrors() {
		return errors;
	}

	public void setErrors(String errors) {
		this.errors = errors;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

}
