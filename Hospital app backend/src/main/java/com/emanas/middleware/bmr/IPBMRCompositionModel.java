package com.emanas.middleware.bmr;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonProperty;

public class IPBMRCompositionModel {

	public String uid;

	public String resultSet;
	public String author;
	public Date date_created;
	@JsonProperty("Sectionofadmission")
	public String sectionofadmission;
	@JsonProperty("Assessmentname")
	public String assessmentname;
	@JsonProperty("Assessmentdate")
	public Date assessmentdate;

	@Override
	public String toString() {
		return "IPBMRCompositionModel [uid=" + uid + ", author=" + author + ", date_created=" + date_created
				+ ", sectionofadmission=" + sectionofadmission + ", assessmentname=" + assessmentname
				+ ", assessmentdate=" + assessmentdate + ", complaints=" + complaints + ", pattern=" + pattern
				+ ", historyofPresentIllness=" + historyofPresentIllness + ", summaryofVitals=" + summaryofVitals
				+ ", summaryofInvestigations=" + summaryofInvestigations + ", pastprescription=" + pastprescription
				+ ", summaryofMentalStatusExam=" + summaryofMentalStatusExam + ", statusofGeneralExam="
				+ statusofGeneralExam + ", uploadfile=" + uploadfile + ", multimedianame=" + multimedianame
				+ ", diagnosis=" + diagnosis + ", diagnosisCode=" + diagnosisCode + ", diagnosisTerminology="
				+ diagnosisTerminology + ", diagnosisCertainity=" + diagnosisCertainity + ", courseinHospital="
				+ courseinHospital + ", medicinaname=" + medicinaname + ", remarks=" + remarks + ", duration="
				+ duration + ", dosingtime=" + dosingtime + ", confidentialityLevel=" + confidentialityLevel + "]";
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

	public String getSectionofadmission() {
		return sectionofadmission;
	}

	public void setSectionofadmission(String sectionofadmission) {
		this.sectionofadmission = sectionofadmission;
	}

	public String getAssessmentname() {
		return assessmentname;
	}

	public void setAssessmentname(String assessmentname) {
		this.assessmentname = assessmentname;
	}

	public Date getAssessmentdate() {
		return assessmentdate;
	}

	public void setAssessmentdate(Date assessmentdate) {
		this.assessmentdate = assessmentdate;
	}

	public String getComplaints() {
		return complaints;
	}

	public void setComplaints(String complaints) {
		this.complaints = complaints;
	}

	public String getPattern() {
		return pattern;
	}

	public void setPattern(String pattern) {
		this.pattern = pattern;
	}

	public Object getHistoryofPresentIllness() {
		return historyofPresentIllness;
	}

	public void setHistoryofPresentIllness(Object historyofPresentIllness) {
		this.historyofPresentIllness = historyofPresentIllness;
	}

	public Object getSummaryofVitals() {
		return summaryofVitals;
	}

	public void setSummaryofVitals(Object summaryofVitals) {
		this.summaryofVitals = summaryofVitals;
	}

	public Object getSummaryofInvestigations() {
		return summaryofInvestigations;
	}

	public void setSummaryofInvestigations(Object summaryofInvestigations) {
		this.summaryofInvestigations = summaryofInvestigations;
	}

	public Object getPastprescription() {
		return pastprescription;
	}

	public void setPastprescription(Object pastprescription) {
		this.pastprescription = pastprescription;
	}

	public Object getSummaryofMentalStatusExam() {
		return summaryofMentalStatusExam;
	}

	public void setSummaryofMentalStatusExam(Object summaryofMentalStatusExam) {
		this.summaryofMentalStatusExam = summaryofMentalStatusExam;
	}

	public Object getStatusofGeneralExam() {
		return statusofGeneralExam;
	}

	public void setStatusofGeneralExam(Object statusofGeneralExam) {
		this.statusofGeneralExam = statusofGeneralExam;
	}

	public Object getUploadfile() {
		return uploadfile;
	}

	public void setUploadfile(Object uploadfile) {
		this.uploadfile = uploadfile;
	}

	public Object getMultimedianame() {
		return multimedianame;
	}

	public void setMultimedianame(Object multimedianame) {
		this.multimedianame = multimedianame;
	}

	public Object getDiagnosis() {
		return diagnosis;
	}

	public void setDiagnosis(Object diagnosis) {
		this.diagnosis = diagnosis;
	}

	public Object getDiagnosisCode() {
		return diagnosisCode;
	}

	public void setDiagnosisCode(Object diagnosisCode) {
		this.diagnosisCode = diagnosisCode;
	}

	public Object getDiagnosisTerminology() {
		return diagnosisTerminology;
	}

	public void setDiagnosisTerminology(Object diagnosisTerminology) {
		this.diagnosisTerminology = diagnosisTerminology;
	}

	public Object getDiagnosisCertainity() {
		return diagnosisCertainity;
	}

	public void setDiagnosisCertainity(Object diagnosisCertainity) {
		this.diagnosisCertainity = diagnosisCertainity;
	}

	public Object getCourseinHospital() {
		return courseinHospital;
	}

	public void setCourseinHospital(Object courseinHospital) {
		this.courseinHospital = courseinHospital;
	}

	public Object getMedicinaname() {
		return medicinaname;
	}

	public void setMedicinaname(Object medicinaname) {
		this.medicinaname = medicinaname;
	}

	public Object getRemarks() {
		return remarks;
	}

	public void setRemarks(Object remarks) {
		this.remarks = remarks;
	}

	public Object getDuration() {
		return duration;
	}

	public void setDuration(Object duration) {
		this.duration = duration;
	}

	public Object getDosingtime() {
		return dosingtime;
	}

	public void setDosingtime(Object dosingtime) {
		this.dosingtime = dosingtime;
	}

	public int getConfidentialityLevel() {
		return confidentialityLevel;
	}

	public void setConfidentialityLevel(int confidentialityLevel) {
		this.confidentialityLevel = confidentialityLevel;
	}

	@JsonProperty("Complaints")
	public String complaints;
	@JsonProperty("Pattern")
	public String pattern;
	@JsonProperty("HistoryofPresentIllness")
	public Object historyofPresentIllness;
	@JsonProperty("SummaryofVitals")
	public Object summaryofVitals;
	@JsonProperty("SummaryofInvestigations")
	public Object summaryofInvestigations;
	@JsonProperty("Pastprescription")
	public Object pastprescription;
	@JsonProperty("SummaryofMentalStatusExam")
	public Object summaryofMentalStatusExam;
	@JsonProperty("StatusofGeneralExam")
	public Object statusofGeneralExam;
	@JsonProperty("Uploadfile")
	public Object uploadfile;
	@JsonProperty("Multimedianame")
	public Object multimedianame;
	@JsonProperty("Diagnosis")
	public Object diagnosis;
	@JsonProperty("DiagnosisCode")
	public Object diagnosisCode;
	@JsonProperty("DiagnosisTerminology")
	public Object diagnosisTerminology;
	@JsonProperty("DiagnosisCertainity")
	public Object diagnosisCertainity;
	@JsonProperty("CourseinHospital")
	public Object courseinHospital;
	@JsonProperty("Medicinaname")
	public Object medicinaname;
	@JsonProperty("Remarks")
	public Object remarks;
	@JsonProperty("Duration")
	public Object duration;
	@JsonProperty("Dosingtime")
	public Object dosingtime;
	public int confidentialityLevel;

}
