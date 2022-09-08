package com.emanas.middleware.models;

import java.util.Date;
import java.util.List;


public class OPBMR {
	 String clinicalHistory;
	    String illnessSummary;
	    String treatmentInstructions;
	    String improvementStatus;
	    String followUpDate;
	    List<Diagnosis> diagnosisDisplay;
	   
		List<Complaint>  complaintDisplay;
		List<Med> medicationDisplay;
		String orgName;
		String consentID;
		public String getConsentID() {
			return consentID;
		}
		@Override
		public String toString() {
			return "OPBMR [clinicalHistory=" + clinicalHistory + ", illnessSummary=" + illnessSummary
					+ ", treatmentInstructions=" + treatmentInstructions + ", improvementStatus=" + improvementStatus
					+ ", followUpDate=" + followUpDate + ", diagnosisDisplay=" + diagnosisDisplay
					+ ", complaintDisplay=" + complaintDisplay + ", medicationDisplay=" + medicationDisplay
					+ ", orgName=" + orgName + ", consentID=" + consentID + ", serviceName=" + serviceName
					+ ", reasonDescription=" + reasonDescription + ", createdDate=" + createdDate + ", visitType="
					+ visitType + ", addNote=" + addNote + ", category=" + category + ", treatmentDetails="
					+ treatmentDetails + ", patientID=" + patientID + ", docId=" + mhpId + ", orgId=" + orgId + "]";
		}
		public void setConsentID(String consentID) {
			this.consentID = consentID;
		}
		String serviceName;
	    String reasonDescription;
	    Date createdDate;
	    String visitType;
	    String addNote;
	    String category;
	    String treatmentDetails;
	    String patientID;
	    String mhpId;
	    String orgId;
	    public String getClinicalHistory() {
			return clinicalHistory;
		}
		public void setClinicalHistory(String clinicalHistory) {
			this.clinicalHistory = clinicalHistory;
		}
		
	    public String getOrgName() {
			return orgName;
		}
		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}
		public String getIllnessSummary() {
			return illnessSummary;
		}
		public void setIllnessSummary(String illnessSummary) {
			this.illnessSummary = illnessSummary;
		}
		public String getTreatmentInstructions() {
			return treatmentInstructions;
		}
		public void setTreatmentInstructions(String treatmentInstructions) {
			this.treatmentInstructions = treatmentInstructions;
		}
		public String getImprovementStatus() {
			return improvementStatus;
		}
		public void setImprovementStatus(String improvementStatus) {
			this.improvementStatus = improvementStatus;
		}
		public String getFollowUpDate() {
			return followUpDate;
		}
		public void setFollowUpDate(String followUpDate) {
			this.followUpDate = followUpDate;
		}
		public List<Diagnosis> getDiagnosisDisplay() {
			return diagnosisDisplay;
		}
		public void setDiagnosisDisplay(List<Diagnosis> diagnosisDisplay) {
			this.diagnosisDisplay = diagnosisDisplay;
		}
		public List<Complaint> getComplaintDisplay() {
			return complaintDisplay;
		}
		public void setComplaintDisplay(List<Complaint> complaintDisplay) {
			this.complaintDisplay = complaintDisplay;
		}
		public List<Med> getMedicationDisplay() {
			return medicationDisplay;
		}
		public void setMedicationDisplay(List<Med> medicationDisplay) {
			this.medicationDisplay = medicationDisplay;
		}
		public String getServiceName() {
			return serviceName;
		}
		public void setServiceName(String serviceName) {
			this.serviceName = serviceName;
		}
		public String getReasonDescription() {
			return reasonDescription;
		}
		public void setReasonDescription(String reasonDescription) {
			this.reasonDescription = reasonDescription;
		}
		public Date getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
		public String getVisitType() {
			return visitType;
		}
		public void setVisitType(String visitType) {
			this.visitType = visitType;
		}
		public String getAddNote() {
			return addNote;
		}
		public void setAddNote(String addNote) {
			this.addNote = addNote;
		}
		public String getCategory() {
			return category;
		}
		public void setCategory(String category) {
			this.category = category;
		}
		public String getTreatmentDetails() {
			return treatmentDetails;
		}
		public void setTreatmentDetails(String treatmentDetails) {
			this.treatmentDetails = treatmentDetails;
		}
		public String getPatientID() {
			return patientID;
		}
		public void setPatientID(String patientID) {
			this.patientID = patientID;
		}
		public String getMhpId() {
			return mhpId;
		}
		public void setMhpId(String mhpId) {
			this.mhpId = mhpId;
		}
		public String getOrgId() {
			return orgId;
		}
		public void setOrgId(String orgId) {
			this.orgId = orgId;
		}
		

}
