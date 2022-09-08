package com.emanas.middleware.models;



import java.util.Arrays;

import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import com.emanas.middleware.utility.PatternUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
@XmlRootElement
public class ChildVirtualFolder {
	
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.uuids)
	String orgUUID;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.uuids)
	String patientId;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.uuids)
	String uuid;
	Object[] uidata;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.session)
	String token;
	long startDate;
	long endDate;
	
	
	public long getStartDate() {
		return startDate;
	}
	public void setStartDate(long startDate) {
		this.startDate = startDate;
	}
	public long getEndDate() {
		return endDate;
	}
	public void setEndDate(long endDate) {
		this.endDate = endDate;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getOrgUUID() {
		return orgUUID;
	}
	public void setOrgUUID(String orgUUID) {
		this.orgUUID = orgUUID;
	}
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public Object[] getUidata() {
		return uidata;
	}
	public void setUidata(Object[] uidata) {
		this.uidata = uidata;
	}
	@Override
	public String toString() {
		return Arrays.toString(uidata);
	}
	

}