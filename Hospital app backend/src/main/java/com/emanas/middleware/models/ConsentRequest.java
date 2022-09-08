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

public class ConsentRequest implements java.io.Serializable {
	
	
	private int ID;
	
	
	private String request;
	
	
	private String consentrequestID;
	
	
	private String otp;
	
	
	private String refId;
	
	


	private Date dateCreated;
	
	
	private String authorisationType;
	
	public Date getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		ID = iD;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
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

	@Column(name = "status")
	private String status;

	public String getAuthorisationType() {
		return authorisationType;
	}

	public void setAuthorisationType(String authorisationType) {
		this.authorisationType = authorisationType;
	}

	@Override
	public String toString() {
		return "ConsentRequest [ID=" + ID + ", request=" + request + ", consentrequestID=" + consentrequestID + ", otp="
				+ otp + ", refId=" + refId + ", dateCreated=" + dateCreated + ", authorisationType=" + authorisationType
				+ ", status=" + status + "]";
	}

	
	

	

}

