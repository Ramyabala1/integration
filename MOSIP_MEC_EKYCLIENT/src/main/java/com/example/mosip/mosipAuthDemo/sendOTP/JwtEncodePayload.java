package com.example.mosip.mosipAuthDemo.sendOTP;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JwtEncodePayload {

	
	@JsonProperty
	JwtEncodeRequest request;
	@JsonProperty
	String id;
	@JsonProperty
	String requesttime;
	@JsonProperty
	String version;
	@JsonProperty
	Object metadata;
	//String timestamp;
	public JwtEncodeRequest getRequest() {
		return request;
	}
	public void setRequest(JwtEncodeRequest request) {
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
	/*
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}*/
	
	
	
}
