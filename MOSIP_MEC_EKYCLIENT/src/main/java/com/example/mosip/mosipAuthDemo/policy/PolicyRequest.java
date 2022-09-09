package com.example.mosip.mosipAuthDemo.policy;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PolicyRequest {
	@JsonProperty
	String desc;
	@JsonProperty
	String name;
	@JsonProperty
	String version;
	@JsonProperty
	String policyGroupName;
	@JsonProperty
	String policyType;
	
	
	
	Policy policies;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getPolicyGroupName() {
		return policyGroupName;
	}

	public void setPolicyGroupName(String policyGroupName) {
		this.policyGroupName = policyGroupName;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	
	

	public Policy getPolicies() {
		return policies;
	}

	public void setPolicies(Policy policies) {
		this.policies = policies;
	}

	@Override
	public String toString() {
		return "PolicyRequest [desc=" + desc + ", name=" + name + ", version=" + version + ", policyGroupName="
				+ policyGroupName + ", policyType=" + policyType + ", policies="
				+ policies + "]";
	}
	
	
	
	
}
