package com.emanas.middleware.bmr;

import java.util.Date;
import java.util.List;

public class IPBMR {
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

	public String getPatientID() {
		return patientID;
	}

	public String getConsentID() {
		return consentID;
	}

	@Override
	public String toString() {
		return "IPBMR [patientID=" + patientID + ", orgId=" + orgId + ", orgName=" + orgName + ", docId=" + docId
				+ ", capacity=" + capacity + ", history=" + history + ", vitals=" + vitals + ", physical_exam="
				+ physical_exam + ", mental_status=" + mental_status + ", investigation=" + investigation + ", course="
				+ course + ", createdDate=" + createdDate + ", consentID=" + consentID + ", gname=" + gname
				+ ", gphone=" + gphone + ", gdmitDate=" + gdmitDate + ", past_prescription=" + past_prescription
				+ ", medicationDisplay=" + medicationDisplay + ", complaintDisplay=" + complaintDisplay
				+ ", diagnosisDisplay=" + diagnosisDisplay + "]";
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
