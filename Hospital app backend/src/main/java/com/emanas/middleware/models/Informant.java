package com.emanas.middleware.models;

public class Informant {

	public String informantName;
	public String informantRelataionship;
	
	public String informant;
	public String relationship;
	public String getInformant() {
		return informant;
	}
	public void setInformant(String informant) {
		this.informant = informant;
	}
	public String getRelationship() {
		return relationship;
	}
	public void setRelationship(String relationship) {
		this.relationship = relationship;
	}
	@Override
	public String toString() {
		return "Informant [informantName=" + informantName + ", informantRelataionship=" + informantRelataionship
				+ ", informant=" + informant + ", relationship=" + relationship + "]";
	}
	
	public String getInformantName() {
		return informantName;
	}
	public void setInformantName(String informantName) {
		this.informantName = informantName;
	}
	public String getInformantRelataionship() {
		return informantRelataionship;
	}
	public void setInformantRelataionship(String informantRelataionship) {
		this.informantRelataionship = informantRelataionship;
	}
	
	
	
	
}
