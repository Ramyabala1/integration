package com.example.mosip.mosipAuthDemo.authentication;

public class AuthRequest {
	
	String userName;
	String password;
	String appId;
	String applicationId;
	String certificateUrl;
	String dataToSign;
	Boolean includeCertHash;
	Boolean includeCertificate;
	Boolean includePayload;
	String referenceId;
	String secretKey;
	String clientId;
	
	
	
	public String getSecretKey() {
		return secretKey;
	}
	public void setSecretKey(String secretKey) {
		this.secretKey = secretKey;
	}
	public String getClientId() {
		return clientId;
	}
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
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
	public AuthRequest() {
		super();
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAppId() {
		return appId;
	}
	public void setAppId(String appId) {
		this.appId = appId;
	}
	

}
