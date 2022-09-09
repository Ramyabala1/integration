package com.example.mosip.mosipAuthDemo.policy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Policy {
	@JsonProperty
	String authTokenType;
	
	List<AllowedAuthType> allowedAuthTypes;
	
	List<AllowedKycAttribute> allowedKycAttributes;

	public String getAuthTokenType() {
		return authTokenType;
	}

	public void setAuthTokenType(String authTokenType) {
		this.authTokenType = authTokenType;
	}

	public List<AllowedAuthType> getAllowedAuthTypes() {
		return allowedAuthTypes;
	}

	public void setAllowedAuthTypes(List<AllowedAuthType> allowedAuthTypes) {
		this.allowedAuthTypes = allowedAuthTypes;
	}

	public List<AllowedKycAttribute> getAllowedKycAttributes() {
		return allowedKycAttributes;
	}

	public void setAllowedKycAttributes(List<AllowedKycAttribute> allowedKycAttributes) {
		this.allowedKycAttributes = allowedKycAttributes;
	}

	@Override
	public String toString() {
		return "Policy [authTokenType=" + authTokenType + ", allowedAuthTypes=" + allowedAuthTypes
				+ ", allowedKycAttributes=" + allowedKycAttributes + "]";
	}

	
}


