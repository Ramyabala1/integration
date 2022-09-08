package com.emanas.middleware.models;

public class PeriodicMonitoring {

	public String respiration;
	public String pulseUnit;
	public String respirationUnit;
	public String loodSupplyToLimbs;
	public String periodicInjuries;
	public String breathingProblems;
	public String monitoringDate;
	
	public String monitoringDateTime;
	public String pulse;
	public String temperature;
	public String respiratoryRate;
	public String injury;
	public String bloodSupply;
	public String breath;
	
	
	public String getMonitoringDateTime() {
		return monitoringDateTime;
	}
	public void setMonitoringDateTime(String monitoringDateTime) {
		this.monitoringDateTime = monitoringDateTime;
	}
	public String getPulse() {
		return pulse;
	}
	public void setPulse(String pulse) {
		this.pulse = pulse;
	}
	public String getTemperature() {
		return temperature;
	}
	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	public String getInjury() {
		return injury;
	}
	public void setInjury(String injury) {
		this.injury = injury;
	}
	
	public String getBreath() {
		return breath;
	}
	public void setBreath(String breath) {
		this.breath = breath;
	}
	
	
	public String getRespiratoryRate() {
		return respiratoryRate;
	}
	public void setRespiratoryRate(String respiratoryRate) {
		this.respiratoryRate = respiratoryRate;
	}
	public String getBloodSupply() {
		return bloodSupply;
	}
	public void setBloodSupply(String bloodSupply) {
		this.bloodSupply = bloodSupply;
	}
	public String getRespiration() {
		return respiration;
	}
	public void setRespiration(String respiration) {
		this.respiration = respiration;
	}
	public String getPulseUnit() {
		return pulseUnit;
	}
	public void setPulseUnit(String pulseUnit) {
		this.pulseUnit = pulseUnit;
	}
	public String getRespirationUnit() {
		return respirationUnit;
	}
	public void setRespirationUnit(String respirationUnit) {
		this.respirationUnit = respirationUnit;
	}
	public String getLoodSupplyToLimbs() {
		return loodSupplyToLimbs;
	}
	public void setLoodSupplyToLimbs(String loodSupplyToLimbs) {
		this.loodSupplyToLimbs = loodSupplyToLimbs;
	}
	public String getPeriodicInjuries() {
		return periodicInjuries;
	}
	public void setPeriodicInjuries(String periodicInjuries) {
		this.periodicInjuries = periodicInjuries;
	}
	public String getBreathingProblems() {
		return breathingProblems;
	}
	public void setBreathingProblems(String breathingProblems) {
		this.breathingProblems = breathingProblems;
	}
	public String getMonitoringDate() {
		return monitoringDate;
	}
	public void setMonitoringDate(String monitoringDate) {
		this.monitoringDate = monitoringDate;
	}
	@Override
	public String toString() {
		return "PeriodicMonitoring [respiration=" + respiration + ", pulseUnit=" + pulseUnit + ", respirationUnit="
				+ respirationUnit + ", loodSupplyToLimbs=" + loodSupplyToLimbs + ", periodicInjuries="
				+ periodicInjuries + ", breathingProblems=" + breathingProblems + ", monitoringDate=" + monitoringDate
				+ ", monitoringDateTime=" + monitoringDateTime + ", pulse=" + pulse + ", temperature=" + temperature
				+ ", respiratoryRate=" + respiratoryRate + ", injury=" + injury + ", bloodSupply=" + bloodSupply
				+ ", breath=" + breath + "]";
	}
	
	
	
	
}
