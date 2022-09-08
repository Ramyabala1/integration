package com.emanas.middleware.models;

public class BMRForComposition {
	
	String compId;
	HiuUser User ;
	String patientId ;
	String template;
	
	
	
	
	public String getTemplate() {
		return template;
	}


	public void setTemplate(String template) {
		this.template = template;
	}


	public String getCompId() {
		return compId;
	}


	public void setCompId(String compId) {
		this.compId = compId;
	}


	public HiuUser getUser() {
		return User;
	}


	public void setUser(HiuUser user) {
		User = user;
	}


	public String getPatientId() {
		return patientId;
	}


	public void setPatientId(String patientId) {
		this.patientId = patientId;
	}


	@Override
	public String toString() {
		return "BMRForComposition [compId=" + compId + ", User=" + User + ", patientId=" + patientId + ", template="
				+ template + "]";
	}


	public void setTemplate() {
		// TODO Auto-generated method stub
		
	} 
	
	

}
