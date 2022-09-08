package com.emanas.middleware.models;



 

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(Include.NON_NULL)


public class HiuUser implements java.io.Serializable {
	
	
		private int serverId;
	
	private String userName;
	
	private String password;
	
	private String hospitalName; 
	
	public String getHospitalName() {
		return hospitalName;
	}

	public void setHospitalName(String hospitalName) {
		this.hospitalName = hospitalName;
	}

	public String getHiuSessionToken() {
		return hiuSessionToken;
	}

	public void setHiuSessionToken(String hiuSessionToken) {
		this.hiuSessionToken = hiuSessionToken;
	}

	public String getMheSessionToken() {
		return mheSessionToken;
	}

	public void setMheSessionToken(String mheSessionToken) {
		this.mheSessionToken = mheSessionToken;
	}


	private String hiuSessionToken;
	
	private String mheSessionToken;
	
	private String status;
	
	private String mheUserName;
	
	private String mhePassword;
	

	private String mheOrgname;
	
	private String mheAccessToken;
	
	public String getOrguuid() {
		return orguuid;
	}

	public void setOrguuid(String orguuid) {
		this.orguuid = orguuid;
	}

	public String getUuid() {
		return uuid;
	}

	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	private String orguuid;
	
	private String uuid;
	
	
	
	
	public String getMheAccessToken() {
		return mheAccessToken;
	}

	public void setMheAccessToken(String mheAccessToken) {
		this.mheAccessToken = mheAccessToken;
	}

	public String getMheOrgname() {
		return mheOrgname;
	}

	public void setMheOrgname(String mheOrgname) {
		this.mheOrgname = mheOrgname;
	}


	private Date lastUpdatedTime;

	public int getServerId() {
		return serverId;
	}

	public void setServerId(int serverId) {
		this.serverId = serverId;
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMheUserName() {
		return mheUserName;
	}

	public void setMheUserName(String mheUserName) {
		this.mheUserName = mheUserName;
	}

	public String getMhePassword() {
		return mhePassword;
	}

	public void setMhePassword(String mhePassword) {
		this.mhePassword = mhePassword;
	}
	
	

	public Date getLastUpdatedTime() {
		return lastUpdatedTime;
	}

	public void setLastUpdatedTime(Date lastUpdatedTime) {
		this.lastUpdatedTime = lastUpdatedTime;
	}
	
	

	@Override
	public String toString() {
		return "HiuUser [serverId=" + serverId + ", userName=" + userName + ", password=" + password + ", hospitalName="
				+ hospitalName + ", hiuSessionToken=" + hiuSessionToken + ", mheSessionToken=" + mheSessionToken
				+ ", status=" + status + ", mheUserName=" + mheUserName + ", mhePassword=" + mhePassword
				+ ", mheOrgname=" + mheOrgname + ", mheAccessToken=" + mheAccessToken + ", orguuid=" + orguuid
				+ ", uuid=" + uuid + ", lastUpdatedTime=" + lastUpdatedTime + "]";
	}
	
	

	

}


	


	
	
	

