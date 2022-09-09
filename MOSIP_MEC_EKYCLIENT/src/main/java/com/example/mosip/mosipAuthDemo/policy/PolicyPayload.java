package com.example.mosip.mosipAuthDemo.policy;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PolicyPayload {
	@JsonProperty
	String id;
	
	PolicyRequest request;
	
	@JsonProperty
	String requestTime;
	
	@JsonProperty
	String version;
	
	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public PolicyRequest getRequest() {
		return request;
	}

	public void setRequest(PolicyRequest request) {
		this.request = request;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	@Override
	public String toString() {
		return "PolicyPayload [id=" + id + ", request=" + request + ", requestTime=" + requestTime + ", version="
				+ version + "]";
	}
	
	

}
