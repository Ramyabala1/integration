package com.example.mosip.mosipAuthDemo.authentication;

public class JWTRequest {
	
	String applicationId;
	String certificateUrl;
	String dataToSign;
	Boolean includeCertHash;
	Boolean includeCertificate;
	Boolean includePayload;
	String referenceId;
	
	
	
	
	public String getApplicationId() {
		return applicationId;
	}
	public void setApplicationId(String applicationId) {
		this.applicationId = applicationId;
	}
	public String getCertificateUrl() {
		return certificateUrl;
	}
	public void setCertificateUrl(String certificateUrl) {
		this.certificateUrl = certificateUrl;
	}
	public String getDataToSign() {
		return dataToSign;
	}
	public void setDataToSign(String dataToSign) {
		this.dataToSign = dataToSign;
	}
	public Boolean getIncludeCertHash() {
		return includeCertHash;
	}
	public void setIncludeCertHash(Boolean includeCertHash) {
		this.includeCertHash = includeCertHash;
	}
	public Boolean getIncludeCertificate() {
		return includeCertificate;
	}
	public void setIncludeCertificate(Boolean includeCertificate) {
		this.includeCertificate = includeCertificate;
	}
	public Boolean getIncludePayload() {
		return includePayload;
	}
	public void setIncludePayload(Boolean includePayload) {
		this.includePayload = includePayload;
	}
	public String getReferenceId() {
		return referenceId;
	}
	public void setReferenceId(String referenceId) {
		this.referenceId = referenceId;
	}
	public JWTRequest() {
		super();
	}
	
	

}
