package com.app.patient.healthRecord;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthRecordService {

	@Autowired
	HealthRecordRepo repo;

	public Boolean saveHealth(HealthRecord h) {
		HealthRecord health = repo.save(h);
		if (health != null) {
			return true;
		}
		return false;
	}

	public List<HealthRecord> getHealth(String patientId) {
		List<HealthRecord> health = repo.findByEmanas(patientId);
		if (health != null && health.size() > 0) {
			return health;
		}
		return health;
	}

}
