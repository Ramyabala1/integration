package com.emanas.middleware.models;



import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)

public class ConsentVerified implements java.io.Serializable {
	
	
	private int ID;
	
	
	private String consentID;
	
	
	private String consentrequestID;
	
	
	private String otp;
	
	
	private String refId;
	
	
	private String status;
	
	
	private String artifact;
	
	
	private Date dateCreated;
	
	
	private Date consentexpiry;
	
	
	private int accessCount;

	public int getAccessCount() {
		return accessCount;
	}

	public void setAccessCount(int accessCount) {
		this.accessCount = accessCount;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getConsentID() {
		return consentID;
	}

	public void setConsentID(String consentID) {
		this.consentID = consentID;
	}

	public String getConsentrequestID() {
		return consentrequestID;
	}

	public void setConsentrequestID(String consentrequestID) {
		this.consentrequestID = consentrequestID;
	}

	public String getOtp() {
		return otp;
	}

	public void setOtp(String otp) {
		this.otp = otp;
	}

	public String getRefId() {
		return refId;
	}

	public void setRefId(String refId) {
		this.refId = refId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getArtifact() {
		return artifact;
	}

	public void setArtifact(String artifact) {
		this.artifact = artifact;
	}

	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public Date getConsentexpiry() {
		return consentexpiry;
	}

	public void setConsentexpiry(Date consentexpiry) {
		this.consentexpiry = consentexpiry;
	}

	@Override
	public String toString() {
		return "ConsentVerified [ID=" + ID + ", consentID=" + consentID + ", consentrequestID=" + consentrequestID
				+ ", otp=" + otp + ", refId=" + refId + ", status=" + status + ", artifact=" + artifact
				+ ", dateCreated=" + dateCreated + ", consentexpiry=" + consentexpiry + ", accessCount=" + accessCount
				+ "]";
	} 
		


	
	
	

	

}

