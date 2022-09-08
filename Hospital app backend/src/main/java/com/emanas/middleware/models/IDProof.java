package com.emanas.middleware.models;

public class IDProof {
	
	String idNumber;
	String type;

	
	
	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getIdNumber() {
		return idNumber;
	}

	public void setIdNumber(String idNumber) {
		this.idNumber = idNumber;
	}

	@Override
	public String toString() {
		return "IDProof [idNumber=" + idNumber + "]";
	}

}
