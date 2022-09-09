package com.example.mosip.mosipAuthDemo.policy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AllowedKycAttribute {

	@JsonProperty
	String attributeName;

	public String getAttributeName() {
		return attributeName;
	}

	public void setAttributeName(String attributeName) {
		this.attributeName = attributeName;
	}

	@Override
	public String toString() {
		return "AllowedKycAttribute [attributeName=" + attributeName + "]";
	}
	
	
	
	
}
