package com.emanas.middleware.models;

import java.util.Date;
import java.util.List;

public class IPBMR {
	public String patientID;
    public String orgId;
    public String orgName;
    public String mhpId;
    public String capacity;
    public String history;
    public String vitals;
    public String physical_exam;
    public String mental_status;
    public String investigation;
    public String course;
    public Date createdDate;
    public String consentID;
    
    public String guardianName;
    public String  guardianPhoneNumber;
    public Date admitDate;
   
	public String past_prescription;
    public List<Med> medicationDisplay;
    public List<Complaint> complaintDisplay;
    public List<Diagnosis> diagnosisDisplay;
    
    public List<Med> medicationAfterDischarge;

    public Date dischargeDate;
 	public String dischargeTime;
 	public String dischargeType;
 	public String dischargeCondition;
 	public String conditionDescription;
 	public String treatmentAdvice;
 	public String followUp;
 	
 	public String mhpName;
 	public String mhpDesignation;
    
    public String getPatientID() {
		return patientID;
	}
	public String getConsentID() {
		return consentID;
	}
	
	
	
	public List<Med> getMedicationAfterDischarge() {
		return medicationAfterDischarge;
	}
	public void setMedicationAfterDischarge(List<Med> medicationAfterDischarge) {
		this.medicationAfterDischarge = medicationAfterDischarge;
	}
	
	
	public String getDischargeType() {
		return dischargeType;
	}
	public void setDischargeType(String dischargeType) {
		this.dischargeType = dischargeType;
	}
	public String getDischargeCondition() {
		return dischargeCondition;
	}
	public void setDischargeCondition(String dischargeCondition) {
		this.dischargeCondition = dischargeCondition;
	}
	public String getConditionDescription() {
		return conditionDescription;
	}
	public void setConditionDescription(String conditionDescription) {
		this.conditionDescription = conditionDescription;
	}
	public String getTreatmentAdvice() {
		return treatmentAdvice;
	}
	public void setTreatmentAdvice(String treatmentAdvice) {
		this.treatmentAdvice = treatmentAdvice;
	}
	public String getFollowUp() {
		return followUp;
	}
	public void setFollowUp(String followUp) {
		this.followUp = followUp;
	}
	
	
	public void setConsentID(String consentID) {
		this.consentID = consentID;
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
	
	public String getCapacity() {
		return capacity;
	}
	public void setCapacity(String capacity) {
		this.capacity = capacity;
	}
	public String getHistory() {
		return history;
	}
	public void setHistory(String history) {
		this.history = history;
	}
	public String getVitals() {
		return vitals;
	}
	public void setVitals(String vitals) {
		this.vitals = vitals;
	}
	public String getPhysical_exam() {
		return physical_exam;
	}
	public void setPhysical_exam(String physical_exam) {
		this.physical_exam = physical_exam;
	}
	public String getMental_status() {
		return mental_status;
	}
	public void setMental_status(String mental_status) {
		this.mental_status = mental_status;
	}
	public String getInvestigation() {
		return investigation;
	}
	public void setInvestigation(String investigation) {
		this.investigation = investigation;
	}
	public String getCourse() {
		return course;
	}
	public void setCourse(String course) {
		this.course = course;
	}
	public String getPast_prescription() {
		return past_prescription;
	}
	public void setPast_prescription(String past_prescription) {
		this.past_prescription = past_prescription;
	}
	public List<Med> getMedicationDisplay() {
		return medicationDisplay;
	}
	public void setMedicationDisplay(List<Med> medicationDisplay) {
		this.medicationDisplay = medicationDisplay;
	}
	public List<Complaint> getComplaintDisplay() {
		return complaintDisplay;
	}
	public void setComplaintDisplay(List<Complaint> complaintDisplay) {
		this.complaintDisplay = complaintDisplay;
	}
	public List<Diagnosis> getDiagnosisDisplay() {
		return diagnosisDisplay;
	}
	public void setDiagnosisDisplay(List<Diagnosis> diagnosisDisplay) {
		this.diagnosisDisplay = diagnosisDisplay;
	}
	
	 public Date getCreatedDate() {
			return createdDate;
		}
		public void setCreatedDate(Date createdDate) {
			this.createdDate = createdDate;
		}
		public String getMhpId() {
			return mhpId;
		}
		public void setMhpId(String mhpId) {
			this.mhpId = mhpId;
		}
		public String getGuardianName() {
			return guardianName;
		}
		public void setGuardianName(String guardianName) {
			this.guardianName = guardianName;
		}
		public String getGuardianPhoneNumber() {
			return guardianPhoneNumber;
		}
		public void setGuardianPhoneNumber(String guardianPhoneNumber) {
			this.guardianPhoneNumber = guardianPhoneNumber;
		}
		public Date getAdmitDate() {
			return admitDate;
		}
		public void setAdmitDate(Date admitDate) {
			this.admitDate = admitDate;
		}
		public Date getDischargeDate() {
			return dischargeDate;
		}
		public void setDischargeDate(Date dischargeDate) {
			this.dischargeDate = dischargeDate;
		}
		public String getDischargeTime() {
			return dischargeTime;
		}
		public void setDischargeTime(String dischargeTime) {
			this.dischargeTime = dischargeTime;
		}
		public String getMhpName() {
			return mhpName;
		}
		public void setMhpName(String mhpName) {
			this.mhpName = mhpName;
		}
		public String getMhpDesignation() {
			return mhpDesignation;
		}
		public void setMhpDesignation(String mhpDesignation) {
			this.mhpDesignation = mhpDesignation;
		}
		@Override
		public String toString() {
			return "ADDIPBMR [patientID=" + patientID + ", orgId=" + orgId + ", orgName=" + orgName + ", mhpId=" + mhpId
					+ ", capacity=" + capacity + ", history=" + history + ", vitals=" + vitals + ", physical_exam="
					+ physical_exam + ", mental_status=" + mental_status + ", investigation=" + investigation
					+ ", course=" + course + ", createdDate=" + createdDate + ", consentID=" + consentID
					+ ", guardianName=" + guardianName + ", guardianPhoneNumber=" + guardianPhoneNumber + ", admitDate="
					+ admitDate + ", past_prescription=" + past_prescription + ", medicationDisplay="
					+ medicationDisplay + ", complaintDisplay=" + complaintDisplay + ", diagnosisDisplay="
					+ diagnosisDisplay + ", medicationAfterDischarge=" + medicationAfterDischarge + ", dischargeDate="
					+ dischargeDate + ", dischargeTime=" + dischargeTime + ", dischargeType=" + dischargeType
					+ ", dischargeCondition=" + dischargeCondition + ", conditionDescription=" + conditionDescription
					+ ", treatmentAdvice=" + treatmentAdvice + ", followUp=" + followUp + ", mhpName=" + mhpName
					+ ", mhpDesignation=" + mhpDesignation + "]";
		}
		
		
		
	

}
