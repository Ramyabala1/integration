package com.emanas.middleware.models;

public class Payload {
	
	String patientId;
	String compositionId;
	String mheSession;
	String templateId;
	String role;
	String operation;
	String consentId;
	String purpose;
	public String getPatientId() {
		return patientId;
	}
	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}
	public String getCompositionId() {
		return compositionId;
	}
	public void setCompositionId(String compositionId) {
		this.compositionId = compositionId;
	}
	public String getMheSession() {
		return mheSession;
	}
	public void setMheSession(String mheSession) {
		this.mheSession = mheSession;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getOperation() {
		return operation;
	}
	public void setOperation(String operation) {
		this.operation = operation;
	}
	public String getConsentId() {
		return consentId;
	}
	public void setConsentId(String consentId) {
		this.consentId = consentId;
	}
	public String getPurpose() {
		return purpose;
	}
	public void setPurpose(String purpose) {
		this.purpose = purpose;
	}
	@Override
	public String toString() {
		return "Payload [patientId=" + patientId + ", compositionId=" + compositionId + ", mheSession=" + mheSession
				+ ", templateId=" + templateId + ", role=" + role + ", operation=" + operation + ", consentId="
				+ consentId + ", purpose=" + purpose + "]";
	}
	
	
	
	

}
