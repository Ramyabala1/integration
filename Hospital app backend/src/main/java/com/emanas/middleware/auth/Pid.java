package com.emanas.middleware.auth;

public class Pid {
	String emanasid;
	String authType;
	String authServiceType;
	
	public String getAuthServiceType() {
		return authServiceType;
	}
	public void setAuthServiceType(String authServiceType) {
		this.authServiceType = authServiceType;
	}
	public String getEmanasid() {
		return emanasid;
	}
	public void setEmanasid(String emanasid) {
		this.emanasid = emanasid;
	}
	public String getAuthType() {
		return authType;
	}
	public void setAuthType(String authType) {
		this.authType = authType;
	}
	public Pid(String emanasid, String authType, String authServiceType) {
		super();
		this.emanasid = emanasid;
		this.authType = authType;
		this.authServiceType = authServiceType;
	}
	
	
	
	
}

