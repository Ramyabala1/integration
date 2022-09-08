package com.emanas.middleware.models;

public class MedRestraint {
	
	String medicine;
    String dose;
    String route;
    String freq;
    String totalDose;
    String sideEffect;
	public String getMedicine() {
		return medicine;
	}
	public void setMedicine(String medicine) {
		this.medicine = medicine;
	}
	public String getDose() {
		return dose;
	}
	public void setDose(String dose) {
		this.dose = dose;
	}
	public String getRoute() {
		return route;
	}
	public void setRoute(String route) {
		this.route = route;
	}
	public String getFreq() {
		return freq;
	}
	public void setFreq(String freq) {
		this.freq = freq;
	}
	public String getTotalDose() {
		return totalDose;
	}
	public void setTotalDose(String totalDose) {
		this.totalDose = totalDose;
	}
	public String getSideEffect() {
		return sideEffect;
	}
	public void setSideEffect(String sideEffect) {
		this.sideEffect = sideEffect;
	}
	@Override
	public String toString() {
		return "MedRestraint [medicine=" + medicine + ", dose=" + dose + ", route=" + route + ", freq=" + freq
				+ ", totalDose=" + totalDose + ", sideEffect=" + sideEffect + "]";
	}
    
    
    

}
