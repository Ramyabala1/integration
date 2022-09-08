package com.emanas.middleware.models;

import java.util.List;



public class Restraint {
	
	public String  uid;
	public String whetherInformed;
	public String author;
	public String bloodSupplyToLimbs;
	public String breathingProblems;
	public String confidentialityLevel;
	public String date_created;
	public String endDateTime;
	public String inchargePsychiatrist;
	public String limbIschemia;
	public String monitoringDate;
	public String nominatedName;
	public String organizationName;
	public String others;
	public String periodicInjuries;
	public String psychiatricDiagnosis;
	public String pulseUnit;
	public String respiration;
	public String respirationUnit;
	public String temperatureUnit;
	
	
	
	public String startDateTime;
	
		public String mhe;
//		public String inchargePsychiatrist;
		public String setting;
		public String informedNR;
//		public String psycDiagnosis;
		public String restraintType;
		public String duration;
		public String restraintStartDate;
		public String restraintEndDate;
		public String restraintStartTime;
		public String restraintEndTime;
		public String reason;
		public String injuries;
		public String limb;
    	public String otherComplications;
		public String monitoringDateTime;
		public String pulse;
		public String temperature;
		public String respiratoryRate;
		public String injury;
		public String bloodSupply;
		public String breath;
		public String durationType;
		public String createdDate;
	
		public List<PeriodicMonitoring> periodicArray;		
		public List<MedRestraint> medicationArr;	
//		public String LimbIsch;
//		public String otherComp;
		public String patientID;
		public String mhpId;
		public String orgId;
		public String orgName;
		public String consentID;
		
		
		public String nrName;
		
		public String getOtherComplications() {
			return otherComplications;
		}

		public void setOtherComplications(String otherComplications) {
			this.otherComplications = otherComplications;
		}

		public List<MedRestraint> getMedicationArr() {
			return medicationArr;
		}

		public void setMedicationArr(List<MedRestraint> medicationArray) {
			this.medicationArr = medicationArray;
		}

		public String getNrName() {
			return nrName;
		}
		public void setNrName(String nrName) {
			this.nrName = nrName;
		}
		public String getMhe() {
			return mhe;
		}
		public void setMhe(String mhe) {
			this.mhe = mhe;
		}
		
		public String getSetting() {
			return setting;
		}
		public void setSetting(String setting) {
			this.setting = setting;
		}
		public String getInformedNR() {
			return informedNR;
		}
		public void setInformedNR(String informedNR) {
			this.informedNR = informedNR;
		}
		
		public String getDuration() {
			return duration;
		}
		public void setDuration(String duration) {
			this.duration = duration;
		}
		
		
		public String getReason() {
			return reason;
		}
		public void setReason(String reason) {
			this.reason = reason;
		}
		public String getInjuries() {
			return injuries;
		}
		public void setInjuries(String injuries) {
			this.injuries = injuries;
		}
		public String getLimb() {
			return limb;
		}
		public void setLimb(String limb) {
			this.limb = limb;
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
		public String getDurationType() {
			return durationType;
		}
		public void setDurationType(String durationType) {
			this.durationType = durationType;
		}
		public String getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(String createdDate) {
			this.createdDate = createdDate;
		}
		public List<PeriodicMonitoring> getPeriodicArray() {
			return periodicArray;
		}
		public void setPeriodicArray(List<PeriodicMonitoring> periodicArray) {
			this.periodicArray = periodicArray;
		}
		
		public String getPatientID() {
			return patientID;
		}
		public void setPatientID(String patientID) {
			this.patientID = patientID;
		}
		
		public String getOrgId() {
			return orgId;
		}
		public void setOrgId(String orgId) {
			this.orgId = orgId;
		}
		public String getOrgName() {
			return orgName;
		}
		public void setOrgName(String orgName) {
			this.orgName = orgName;
		}
		public String getConsentID() {
			return consentID;
		}
		public void setConsentID(String consentID) {
			this.consentID = consentID;
		}
		public String getUid() {
			return uid;
		}
		public void setUid(String uid) {
			this.uid = uid;
		}
		public String getWhetherInformed() {
			return whetherInformed;
		}
		public void setWhetherInformed(String whetherInformed) {
			this.whetherInformed = whetherInformed;
		}
		public String getAuthor() {
			return author;
		}
		public void setAuthor(String author) {
			this.author = author;
		}
		public String getBloodSupplyToLimbs() {
			return bloodSupplyToLimbs;
		}
		public void setBloodSupplyToLimbs(String bloodSupplyToLimbs) {
			this.bloodSupplyToLimbs = bloodSupplyToLimbs;
		}
		public String getBreathingProblems() {
			return breathingProblems;
		}
		public void setBreathingProblems(String breathingProblems) {
			this.breathingProblems = breathingProblems;
		}
		public String getConfidentialityLevel() {
			return confidentialityLevel;
		}
		public void setConfidentialityLevel(String confidentialityLevel) {
			this.confidentialityLevel = confidentialityLevel;
		}
		public String getDate_created() {
			return date_created;
		}
		public void setDate_created(String date_created) {
			this.date_created = date_created;
		}
		public String getEndDateTime() {
			return endDateTime;
		}
		public void setEndDateTime(String endDateTime) {
			this.endDateTime = endDateTime;
		}
		public String getInchargePsychiatrist() {
			return inchargePsychiatrist;
		}
		public void setInchargePsychiatrist(String inchargePsychiatrist) {
			this.inchargePsychiatrist = inchargePsychiatrist;
		}
		public String getLimbIschemia() {
			return limbIschemia;
		}
		public void setLimbIschemia(String limbIschemia) {
			this.limbIschemia = limbIschemia;
		}
		public String getMonitoringDate() {
			return monitoringDate;
		}
		public void setMonitoringDate(String monitoringDate) {
			this.monitoringDate = monitoringDate;
		}
		public String getNominatedName() {
			return nominatedName;
		}
		public void setNominatedName(String nominatedName) {
			this.nominatedName = nominatedName;
		}
		public String getOrganizationName() {
			return organizationName;
		}
		public void setOrganizationName(String organizationName) {
			this.organizationName = organizationName;
		}
		public String getOthers() {
			return others;
		}
		public void setOthers(String others) {
			this.others = others;
		}
		public String getPeriodicInjuries() {
			return periodicInjuries;
		}
		public void setPeriodicInjuries(String periodicInjuries) {
			this.periodicInjuries = periodicInjuries;
		}
		public String getPsychiatricDiagnosis() {
			return psychiatricDiagnosis;
		}
		public void setPsychiatricDiagnosis(String psychiatricDiagnosis) {
			this.psychiatricDiagnosis = psychiatricDiagnosis;
		}
		public String getPulseUnit() {
			return pulseUnit;
		}
		public void setPulseUnit(String pulseUnit) {
			this.pulseUnit = pulseUnit;
		}
		public String getRespiration() {
			return respiration;
		}
		public void setRespiration(String respiration) {
			this.respiration = respiration;
		}
		public String getRespirationUnit() {
			return respirationUnit;
		}
		public void setRespirationUnit(String respirationUnit) {
			this.respirationUnit = respirationUnit;
		}
		public String getTemperatureUnit() {
			return temperatureUnit;
		}
		public void setTemperatureUnit(String temperatureUnit) {
			this.temperatureUnit = temperatureUnit;
		}
		public String getStartDateTime() {
			return startDateTime;
		}
		public void setStartDateTime(String startDateTime) {
			this.startDateTime = startDateTime;
		}

		public String getRestraintType() {
			return restraintType;
		}

		public void setRestraintType(String restraintType) {
			this.restraintType = restraintType;
		}

		public String getRestraintStartDate() {
			return restraintStartDate;
		}

		public void setRestraintStartDate(String restraintStartDate) {
			this.restraintStartDate = restraintStartDate;
		}

		public String getRestraintEndDate() {
			return restraintEndDate;
		}

		public void setRestraintEndDate(String restraintEndDate) {
			this.restraintEndDate = restraintEndDate;
		}

		public String getRestraintStartTime() {
			return restraintStartTime;
		}

		public void setRestraintStartTime(String restraintStartTime) {
			this.restraintStartTime = restraintStartTime;
		}

		public String getRestraintEndTime() {
			return restraintEndTime;
		}

		public void setRestraintEndTime(String restraintEndTime) {
			this.restraintEndTime = restraintEndTime;
		}

		public String getMonitoringDateTime() {
			return monitoringDateTime;
		}

		public void setMonitoringDateTime(String monitoringDateTime) {
			this.monitoringDateTime = monitoringDateTime;
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

		public String getMhpId() {
			return mhpId;
		}

		public void setMhpId(String mhpId) {
			this.mhpId = mhpId;
		}

		@Override
		public String toString() {
			return "Restraint [uid=" + uid + ", whetherInformed=" + whetherInformed + ", author=" + author
					+ ", bloodSupplyToLimbs=" + bloodSupplyToLimbs + ", breathingProblems=" + breathingProblems
					+ ", confidentialityLevel=" + confidentialityLevel + ", date_created=" + date_created
					+ ", endDateTime=" + endDateTime + ", inchargePsychiatrist=" + inchargePsychiatrist
					+ ", limbIschemia=" + limbIschemia + ", monitoringDate=" + monitoringDate + ", nominatedName="
					+ nominatedName + ", organizationName=" + organizationName + ", others=" + others
					+ ", periodicInjuries=" + periodicInjuries + ", psychiatricDiagnosis=" + psychiatricDiagnosis
					+ ", pulseUnit=" + pulseUnit + ", respiration=" + respiration + ", respirationUnit="
					+ respirationUnit + ", temperatureUnit=" + temperatureUnit + ", startDateTime=" + startDateTime
					+ ", mhe=" + mhe + ", setting=" + setting + ", informedNR=" + informedNR + ", restraintType="
					+ restraintType + ", duration=" + duration + ", restraintStartDate=" + restraintStartDate
					+ ", restraintEndDate=" + restraintEndDate + ", restraintStartTime=" + restraintStartTime
					+ ", restraintEndTime=" + restraintEndTime + ", reason=" + reason + ", injuries=" + injuries
					+ ", limb=" + limb + ", monitoringDateTime=" + monitoringDateTime + ", pulse=" + pulse
					+ ", temperature=" + temperature + ", respiratoryRate=" + respiratoryRate + ", injury=" + injury
					+ ", bloodSupply=" + bloodSupply + ", breath=" + breath + ", durationType=" + durationType
					+ ", createdDate=" + createdDate + ", periodicArray=" + periodicArray + ", medicationArr="
					+ medicationArr + ", patientID=" + patientID + ", mhpId=" + mhpId + ", orgId=" + orgId
					+ ", orgName=" + orgName + ", consentID=" + consentID + ", nrName=" + nrName + "]";
		}
		
		
	   
	}




