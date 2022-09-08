package com.emanas.middleware.models;

import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.Pattern;
import javax.xml.bind.annotation.XmlRootElement;

import com.emanas.middleware.utility.PatternUtil;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(value=Include.NON_EMPTY, content=Include.NON_NULL)
@XmlRootElement
public class EmergencyContact {
	String id;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.name)
	String contactName;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.alphabetOnly)
	String relation;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.phoneNumberOnly)
	String telephone;
	@Pattern(message = PatternUtil.patternMessage,regexp = PatternUtil.phoneNumberOnly)
	String mobile;
	@Email
	String email;

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	public String getContactName() {
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getRelation() {
		return relation;
	}

	public void setRelation(String relation) {
		this.relation = relation;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "EmergencyContact [id=" + id + ", contactName=" + contactName + ", relation=" + relation + ", telephone="
				+ telephone + ", mobile=" + mobile + ", email=" + email + "]";
	}
	
}
