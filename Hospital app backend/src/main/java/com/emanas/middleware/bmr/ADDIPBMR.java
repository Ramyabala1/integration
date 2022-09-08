package com.emanas.middleware.bmr;

import java.util.Date;
import java.util.List;

public class ADDIPBMR {
	public String patientID;
	public String orgId;
	public String orgName;
	public String docId;
	public String capacity;
	public String history;
	public String vitals;
	public String physical_exam;
	public String mental_status;
	public String investigation;
	public String course;
	public Date createdDate;
	public String consentID;

	public String gname;
	public String gphone;
	public Date gdmitDate;

	public String past_prescription;
	public List<Med> medicationDisplay;
	public List<Complaint> complaintDisplay;
	public List<Diagnosis> diagnosisDisplay;

	public List<Med> medicationAfterDischarge;

	public String disDate;
	public String disTime1;
	public String dischargeType;
	public String dischargeCondition;
	public String conditionDescription;
	public String treatmentAdvice;
	public String followUp;

	public String docName;
	public String designation;

	public String getPatientID() {
		return patientID;
	}

	public String getConsentID() {
		return consentID;
	}

	public List<Med> getMedicationAfterDischarge() {
		return medicationAfterDischarge;
	}

	public void setMedicationAfterDischarge(List<Med> medicationAfterDischarge) {
		this.medicationAfterDischarge = medicationAfterDischarge;
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

	@Override
	public String toString() {
		return "ADDIPBMR [patientID=" + patientID + ", orgId=" + orgId + ", orgName=" + orgName + ", docId=" + docId
				+ ", capacity=" + capacity + ", history=" + history + ", vitals=" + vitals + ", physical_exam="
				+ physical_exam + ", mental_status=" + mental_status + ", investigation=" + investigation + ", course="
				+ course + ", createdDate=" + createdDate + ", consentID=" + consentID + ", gname=" + gname
				+ ", gphone=" + gphone + ", gdmitDate=" + gdmitDate + ", past_prescription=" + past_prescription
				+ ", medicationDisplay=" + medicationDisplay + ", complaintDisplay=" + complaintDisplay
				+ ", diagnosisDisplay=" + diagnosisDisplay + ", medicationAfterDischarge=" + medicationAfterDischarge
				+ ", disDate=" + disDate + ", disTime1=" + disTime1 + ", dischargeType=" + dischargeType
				+ ", dischargeCondition=" + dischargeCondition + ", conditionDescription=" + conditionDescription
				+ ", treatmentAdvice=" + treatmentAdvice + ", followUp=" + followUp + ", docName=" + docName
				+ ", designation=" + designation + ", getPatientID()=" + getPatientID() + ", getConsentID()="
				+ getConsentID() + ", getMedicationAfterDischarge()=" + getMedicationAfterDischarge()
				+ ", getDisDate()=" + getDisDate() + ", getDisTime1()=" + getDisTime1() + ", getDischargeType()="
				+ getDischargeType() + ", getDischargeCondition()=" + getDischargeCondition()
				+ ", getConditionDescription()=" + getConditionDescription() + ", getTreatmentAdvice()="
				+ getTreatmentAdvice() + ", getFollowUp()=" + getFollowUp() + ", getDocName()=" + getDocName()
				+ ", getDesignation()=" + getDesignation() + ", getOrgId()=" + getOrgId() + ", getOrgName()="
				+ getOrgName() + ", getDocId()=" + getDocId() + ", getCapacity()=" + getCapacity() + ", getHistory()="
				+ getHistory() + ", getVitals()=" + getVitals() + ", getPhysical_exam()=" + getPhysical_exam()
				+ ", getMental_status()=" + getMental_status() + ", getInvestigation()=" + getInvestigation()
				+ ", getCourse()=" + getCourse() + ", getPast_prescription()=" + getPast_prescription()
				+ ", getMedicationDisplay()=" + getMedicationDisplay() + ", getComplaintDisplay()="
				+ getComplaintDisplay() + ", getDiagnosisDisplay()=" + getDiagnosisDisplay() + ", getCreatedDate()="
				+ getCreatedDate() + ", getGname()=" + getGname() + ", getGphone()=" + getGphone() + ", getGdmitDate()="
				+ getGdmitDate() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()="
				+ super.toString() + "]";
	}

	public void setConsentID(String consentID) {
		this.consentID = consentID;
	}

	public void setPatientID(String patientID) {
		this.patientID = patientID;
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

	public String getDocId() {
		return docId;
	}

	public void setDocId(String docId) {
		this.docId = docId;
	}

	public String getCapacity() {
		return capacity;
	}

	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}

	public String getHistory() {
		return history;
	}

	public void setHistory(String history) {
		this.history = history;
	}

	public String getVitals() {
		return vitals;
	}

	public void setVitals(String vitals) {
		this.vitals = vitals;
	}

	public String getPhysical_exam() {
		return physical_exam;
	}

	public void setPhysical_exam(String physical_exam) {
		this.physical_exam = physical_exam;
	}

	public String getMental_status() {
		return mental_status;
	}

	public void setMental_status(String mental_status) {
		this.mental_status = mental_status;
	}

	public String getInvestigation() {
		return investigation;
	}

	public void setInvestigation(String investigation) {
		this.investigation = investigation;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getPast_prescription() {
		return past_prescription;
	}

	public void setPast_prescription(String past_prescription) {
		this.past_prescription = past_prescription;
	}

	public List<Med> getMedicationDisplay() {
		return medicationDisplay;
	}

	public void setMedicationDisplay(List<Med> medicationDisplay) {
		this.medicationDisplay = medicationDisplay;
	}

	public List<Complaint> getComplaintDisplay() {
		return complaintDisplay;
	}

	public void setComplaintDisplay(List<Complaint> complaintDisplay) {
		this.complaintDisplay = complaintDisplay;
	}

	public List<Diagnosis> getDiagnosisDisplay() {
		return diagnosisDisplay;
	}

	public void setDiagnosisDisplay(List<Diagnosis> diagnosisDisplay) {
		this.diagnosisDisplay = diagnosisDisplay;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public String getGname() {
		return gname;
	}

	public void setGname(String gname) {
		this.gname = gname;
	}

	public String getGphone() {
		return gphone;
	}

	public void setGphone(String gphone) {
		this.gphone = gphone;
	}

	public Date getGdmitDate() {
		return gdmitDate;
	}

	public void setGdmitDate(Date gdmitDate) {
		this.gdmitDate = gdmitDate;
	}

}
