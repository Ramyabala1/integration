package com.emanas.middleware.models;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)

public class Policy implements Serializable {
	
	
	private int ID;
	
	
	private String Location;
	
	
	private Date createdTime;
	
	
	private String preCondition;
	
	
	private String postCondition;

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getLocation() {
		return Location;
	}

	public void setLocation(String location) {
		Location = location;
	}

	

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public String getPreCondition() {
		return preCondition;
	}

	public void setPreCondition(String preCondition) {
		this.preCondition = preCondition;
	}

	public String getPostCondition() {
		return postCondition;
	}

	public void setPostCondition(String postCondition) {
		this.postCondition = postCondition;
	}

	@Override
	public String toString() {
		return "Policy [ID=" + ID + ", Location=" + Location + ", createdTime=" + createdTime + ", preCondition="
				+ preCondition + ", postCondition=" + postCondition + "]";
	}

	
	
	
	
	
}
	


