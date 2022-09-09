package com.app.patient.healthRecord;

public interface HealthRecordDao {

	HealthRecord savehealth(HealthRecord h);

	public HealthRecord getHealth(String eposideId);

}
