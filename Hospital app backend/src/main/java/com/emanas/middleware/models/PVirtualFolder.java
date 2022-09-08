package com.emanas.middleware.models;

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import com.emanas.middleware.utility.PatternUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;


@JsonIgnoreProperties(ignoreUnknown = true)	
@JsonInclude(Include.NON_NULL)
@XmlRootElement
public class PVirtualFolder {

	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.name)
	String name;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.uuids)
	String orgUUID;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.uuids)
	String userUUID;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.uuids)
	String personUUID;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String type;
	@Valid
	
	String children;
	@Valid
	
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String level;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String status;
	
	
	Object[] item;
	Period period;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String econfidentialitylevel;
	
	

	
	public Object[] getItem() {
		return item;
	}
	public void setItem(Object[] objects) {
		this.item = objects;
	}
	
	public String getEconfidentialitylevel() {
		return econfidentialitylevel;
	}
	public void setEconfidentialitylevel(String econfidentialitylevel) {
		this.econfidentialitylevel = econfidentialitylevel;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOrgUUID() {
		return orgUUID;
	}
	public void setOrgUUID(String orgUUID) {
		this.orgUUID = orgUUID;
	}
	public String getUserUUID() {
		return userUUID;
	}
	public void setUserUUID(String userUUID) {
		this.userUUID = userUUID;
	}
	public String getPersonUUID() {
		return personUUID;
	}
	public void setPersonUUID(String personUUID) {
		this.personUUID = personUUID;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	
	public String getChildren() {
		return children;
	}
	public void setChildren(String children) {
		this.children = children;
	}
	
	public String getLevel() {
		return level;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	
	
	
}
