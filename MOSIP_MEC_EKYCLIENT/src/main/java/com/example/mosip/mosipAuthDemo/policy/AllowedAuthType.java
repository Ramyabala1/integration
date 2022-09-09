package com.example.mosip.mosipAuthDemo.policy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AllowedAuthType {
	
	@JsonProperty
	String authSubType;
	@JsonProperty
	String authType;
	@JsonProperty
	Boolean  mandatory;

	public String getAuthSubType() {
		return authSubType;
	}

	public void setAuthSubType(String authSubType) {
		this.authSubType = authSubType;
	}

	public String getAuthType() {
		return authType;
	}

	public void setAuthType(String authType) {
		this.authType = authType;
	}

	public Boolean getMandatory() {
		return mandatory;
	}

	public void setMandatory(Boolean mandatory) {
		this.mandatory = mandatory;
	}

	@Override
	public String toString() {
		return "AllowedAuthType [authSubType=" + authSubType + ", authType=" + authType + ", mandatory=" + mandatory
				+ "]";
	}
	
	
	
	

}
