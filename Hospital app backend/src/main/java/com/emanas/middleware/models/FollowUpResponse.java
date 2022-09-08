package com.emanas.middleware.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)
public class FollowUpResponse {

	String hospital_name;
	String patient_name;
	String followup_date;
	String emanasId;
	String age;
	String gender;
	String reminderstatus;
	String phone;
	
	public String getHospital_name() {
		return hospital_name;
	}
	public void setHospital_name(String hospital_name) {
		this.hospital_name = hospital_name;
	}
	public String getPatient_name() {
		return patient_name;
	}
	public void setPatient_name(String patient_name) {
		this.patient_name = patient_name;
	}
	public String getFollowup_date() {
		return followup_date;
	}
	public void setFollowup_date(String followup_date) {
		this.followup_date = followup_date;
	}
	public String getEmanasId() {
		return emanasId;
	}
	public void setEmanasId(String emanasId) {
		this.emanasId = emanasId;
	}
	public String getAge() {
		return age;
	}
	public void setAge(String age) {
		this.age = age;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getReminderstatus() {
		return reminderstatus;
	}
	public void setReminderstatus(String reminderstatus) {
		this.reminderstatus = reminderstatus;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	@Override
	public String toString() {
		return "FollowUpResponse [hospital_name=" + hospital_name + ", patient_name=" + patient_name
				+ ", followup_date=" + followup_date + ", emanasId=" + emanasId + ", age=" + age + ", gender=" + gender
				+ ", reminderstatus=" + reminderstatus + ", phone=" + phone + "]";
	}
	
}
