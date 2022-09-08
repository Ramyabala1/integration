package com.emanas.middleware.models;

import java.util.Date;


public class AdvanceDirectiveAccessHistory {

	
	int id;
	
	
	String advanceDirectiveID;
	
	
	String signedDirectiveFileID;
	
	
	String MHPUuid;
	
	
	
	String MHPName;
	
	
	String MHEUuid;
	
	
	String PatientUuid;
	

	String MHEName;
	
	String MHMSID;
	
	 Date viewDate;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getAdvanceDirectiveID() {
		return advanceDirectiveID;
	}

	public void setAdvanceDirectiveID(String advanceDirectiveID) {
		this.advanceDirectiveID = advanceDirectiveID;
	}

	public String getSignedDirectiveFileID() {
		return signedDirectiveFileID;
	}

	public void setSignedDirectiveFileID(String signedDirectiveFileID) {
		this.signedDirectiveFileID = signedDirectiveFileID;
	}

	public String getMHPUuid() {
		return MHPUuid;
	}

	public void setMHPUuid(String mHPUuid) {
		MHPUuid = mHPUuid;
	}

	public String getMHPName() {
		return MHPName;
	}

	public void setMHPName(String mHPName) {
		MHPName = mHPName;
	}

	public String getMHEUuid() {
		return MHEUuid;
	}

	public void setMHEUuid(String mHEUuid) {
		MHEUuid = mHEUuid;
	}

	public String getPatientUuid() {
		return PatientUuid;
	}

	public void setPatientUuid(String patientUuid) {
		PatientUuid = patientUuid;
	}

	public String getMHEName() {
		return MHEName;
	}

	public void setMHEName(String mHEName) {
		MHEName = mHEName;
	}

	public String getMHMSID() {
		return MHMSID;
	}

	public void setMHMSID(String mHMSID) {
		MHMSID = mHMSID;
	}

	public Date getViewDate() {
		return viewDate;
	}

	public void setViewDate(Date viewDate) {
		this.viewDate = viewDate;
	}

	@Override
	public String toString() {
		return "ADAccessHistory [id=" + id + ", advanceDirectiveID=" + advanceDirectiveID + ", signedDirectiveFileID="
				+ signedDirectiveFileID + ", MHPUuid=" + MHPUuid + ", MHPName=" + MHPName + ", MHEUuid=" + MHEUuid
				+ ", PatientUuid=" + PatientUuid + ", MHEName=" + MHEName + ", MHMSID=" + MHMSID + ", viewDate="
				+ viewDate + "]";
	}
	
	
	
}
