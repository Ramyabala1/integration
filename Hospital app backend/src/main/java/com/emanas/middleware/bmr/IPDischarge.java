package com.emanas.middleware.bmr;

import java.util.Date;
import java.util.List;

public class IPDischarge {

	public List<Med> medicationDisplay;
	public Date createdDate;

	public String patientID;
	public String docId;
	public String orgId;
	public String orgName;
	public String disDate;
	public String disTime1;
	public String dischargeType;
	public String dischargeCondition;
	public String conditionDescription;
	public String treatmentAdvice;
	public String followUp;
	public String docName;
	public String designation;
	public String consentID;

	public List<Med> getMedicationDisplay() {
		return medicationDisplay;
	}

	public void setMedicationDisplay(List<Med> medicationDisplay) {
		this.medicationDisplay = medicationDisplay;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getPatientID() {
		return patientID;
	}

	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getOrgId() {
		return orgId;
	}

	public void setOrgId(String orgId) {
		this.orgId = orgId;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDisDate() {
		return disDate;
	}

	public void setDisDate(String disDate) {
		this.disDate = disDate;
	}

	public String getDisTime1() {
		return disTime1;
	}

	public void setDisTime1(String disTime1) {
		this.disTime1 = disTime1;
	}

	public String getDischargeType() {
		return dischargeType;
	}

	public void setDischargeType(String dischargeType) {
		this.dischargeType = dischargeType;
	}

	public String getDischargeCondition() {
		return dischargeCondition;
	}

	public void setDischargeCondition(String dischargeCondition) {
		this.dischargeCondition = dischargeCondition;
	}

	public String getConditionDescription() {
		return conditionDescription;
	}

	public void setConditionDescription(String conditionDescription) {
		this.conditionDescription = conditionDescription;
	}

	public String getTreatmentAdvice() {
		return treatmentAdvice;
	}

	public void setTreatmentAdvice(String treatmentAdvice) {
		this.treatmentAdvice = treatmentAdvice;
	}

	public String getFollowUp() {
		return followUp;
	}

	public void setFollowUp(String followUp) {
		this.followUp = followUp;
	}

	public String getDocName() {
		return docName;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getConsentID() {
		return consentID;
	}

	public void setConsentID(String consentID) {
		this.consentID = consentID;
	}

	@Override
	public String toString() {
		return "IPDischarge [medicationDisplay=" + medicationDisplay + ", createdDate=" + createdDate + ", patientID="
				+ patientID + ", docId=" + docId + ", orgId=" + orgId + ", orgName=" + orgName + ", disDate=" + disDate
				+ ", disTime1=" + disTime1 + ", dischargeType=" + dischargeType + ", dischargeCondition="
				+ dischargeCondition + ", conditionDescription=" + conditionDescription + ", treatmentAdvice="
				+ treatmentAdvice + ", followUp=" + followUp + ", docName=" + docName + ", designation=" + designation
				+ ", consentID=" + consentID + "]";
	}

}
