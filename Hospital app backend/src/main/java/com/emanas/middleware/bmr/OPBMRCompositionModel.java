package com.emanas.middleware.bmr;

import java.util.Date;

public class OPBMRCompositionModel {
	public String uid;
	public String author;
	public Date date_created;
	public String symptomSign;
	public String pattern;
	public Object illnessSummary;
	public Object clinicalHistory;
	public String problemDiagnosis;
	public String problemDiagnosisCode;
	public String problemDiagnosisTerminology;
	public String diagnosticCertainity;

	public String ProblemDescription;
	public Object improvementStatus;
	public String medicationItem;
	public String additionalInstructions;
	public String directionDuration;
	public String timingDescription;
	public Object treatmentInstructions;
	public String serviceName;
	public Object comorbidDiagnosis;
	public Date followUpDate;
	public Object referredDoctor;
	public int confidentialityLevel;
	public String medicationNarrative;
	public String patientID;
	public String authorID;
	public String orgID;
	public String serviceNarrative;

	public String getServiceNarrative() {
		return serviceNarrative;
	}

	public void setServiceNarrative(String serviceNarrative) {
		this.serviceNarrative = serviceNarrative;
	}

	public String getPatientID() {
		return patientID;
	}

	public void setPatientID(String patientID) {
		this.patientID = patientID;
	}

	public String getAuthorID() {
		return authorID;
	}

	public void setAuthorID(String authorID) {
		this.authorID = authorID;
	}

	public String getOrgID() {
		return orgID;
	}

	public void setOrgID(String orgID) {
		this.orgID = orgID;
	}

	public String getMedicationNarrative() {
		return medicationNarrative;
	}

	public void setMedicationNarrative(String medicationNarrative) {
		this.medicationNarrative = medicationNarrative;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Date getDate_created() {
		return date_created;
	}

	public void setDate_created(Date date_created) {
		this.date_created = date_created;
	}

	public String getSymptomSign() {
		return symptomSign;
	}

	public void setSymptomSign(String symptomSign) {
		this.symptomSign = symptomSign;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Object getIllnessSummary() {
		return illnessSummary;
	}

	public void setIllnessSummary(Object illnessSummary) {
		this.illnessSummary = illnessSummary;
	}

	public Object getClinicalHistory() {
		return clinicalHistory;
	}

	public void setClinicalHistory(Object clinicalHistory) {
		this.clinicalHistory = clinicalHistory;
	}

	public String getProblemDiagnosis() {
		return problemDiagnosis;
	}

	public void setProblemDiagnosis(String problemDiagnosis) {
		this.problemDiagnosis = problemDiagnosis;
	}

	public String getProblemDiagnosisCode() {
		return problemDiagnosisCode;
	}

	public void setProblemDiagnosisCode(String problemDiagnosisCode) {
		this.problemDiagnosisCode = problemDiagnosisCode;
	}

	public String getProblemDiagnosisTerminology() {
		return problemDiagnosisTerminology;
	}

	public void setProblemDiagnosisTerminology(String problemDiagnosisTerminology) {
		this.problemDiagnosisTerminology = problemDiagnosisTerminology;
	}

	public String getDiagnosticCertainity() {
		return diagnosticCertainity;
	}

	public void setDiagnosticCertainity(String diagnosticCertainity) {
		this.diagnosticCertainity = diagnosticCertainity;
	}

	public String getProblemDescription() {
		return ProblemDescription;
	}

	public void setProblemDescription(String problemDescription) {
		this.ProblemDescription = problemDescription;
	}

	public Object getImprovementStatus() {
		return improvementStatus;
	}

	public void setImprovementStatus(Object improvementStatus) {
		this.improvementStatus = improvementStatus;
	}

	public String getMedicationItem() {
		return medicationItem;
	}

	public void setMedicationItem(String medicationItem) {
		this.medicationItem = medicationItem;
	}

	public String getAdditionalInstructions() {
		return additionalInstructions;
	}

	public void setAdditionalInstructions(String additionalInstructions) {
		this.additionalInstructions = additionalInstructions;
	}

	public String getDirectionDuration() {
		return directionDuration;
	}

	public void setDirectionDuration(String directionDuration) {
		this.directionDuration = directionDuration;
	}

	public String getTimingDescription() {
		return timingDescription;
	}

	public void setTimingDescription(String timingDescription) {
		this.timingDescription = timingDescription;
	}

	public Object getTreatmentInstructions() {
		return treatmentInstructions;
	}

	public void setTreatmentInstructions(Object treatmentInstructions) {
		this.treatmentInstructions = treatmentInstructions;
	}

	public String getServiceName() {
		return serviceName;
	}

	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}

	public Object getComorbidDiagnosis() {
		return comorbidDiagnosis;
	}

	public void setComorbidDiagnosis(Object comorbidDiagnosis) {
		this.comorbidDiagnosis = comorbidDiagnosis;
	}

	public Object getReasonOfReferral() {
		return reasonOfReferral;
	}

	public void setReasonOfReferral(Object reasonOfReferral) {
		this.reasonOfReferral = reasonOfReferral;
	}

	public Object getReasonDescription() {
		return reasonDescription;
	}

	public void setReasonDescription(Object reasonDescription) {
		this.reasonDescription = reasonDescription;
	}

	public String getReferredOrganization() {
		return referredOrganization;
	}

	public void setReferredOrganization(String referredOrganization) {
		this.referredOrganization = referredOrganization;
	}

	public Date getFollowUpDate() {
		return followUpDate;
	}

	public void setFollowUpDate(Date followUpDate) {
		this.followUpDate = followUpDate;
	}

	public Object getReferredDoctor() {
		return referredDoctor;
	}

	public void setReferredDoctor(Object referredDoctor) {
		this.referredDoctor = referredDoctor;
	}

	public int getConfidentialityLevel() {
		return confidentialityLevel;
	}

	public void setConfidentialityLevel(int confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}

	public Object reasonOfReferral;
	public Object reasonDescription;
	public String referredOrganization;

	@Override
	public String toString() {
		return "OPBMRCompositionModel [uid=" + uid + ", author=" + author + ", date_created=" + date_created
				+ ", symptomSign=" + symptomSign + ", pattern=" + pattern + ", illnessSummary=" + illnessSummary
				+ ", clinicalHistory=" + clinicalHistory + ", problemDiagnosis=" + problemDiagnosis
				+ ", problemDiagnosisCode=" + problemDiagnosisCode + ", problemDiagnosisTerminology="
				+ problemDiagnosisTerminology + ", diagnosticCertainity=" + diagnosticCertainity
				+ ", ProblemDescription=" + ProblemDescription + ", improvementStatus=" + improvementStatus
				+ ", medicationItem=" + medicationItem + ", additionalInstructions=" + additionalInstructions
				+ ", directionDuration=" + directionDuration + ", timingDescription=" + timingDescription
				+ ", treatmentInstructions=" + treatmentInstructions + ", serviceName=" + serviceName
				+ ", comorbidDiagnosis=" + comorbidDiagnosis + ", followUpDate=" + followUpDate + ", referredDoctor="
				+ referredDoctor + ", confidentialityLevel=" + confidentialityLevel + ", medicationNarrative="
				+ medicationNarrative + ", patientID=" + patientID + ", authorID=" + authorID + ", orgID=" + orgID
				+ ", serviceNarrative=" + serviceNarrative + ", reasonOfReferral=" + reasonOfReferral
				+ ", reasonDescription=" + reasonDescription + ", referredOrganization=" + referredOrganization + "]";
	}
}
