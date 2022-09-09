package com.app.patient.healthRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HealthRecordDaoImpl implements HealthRecordDao {
	@Autowired
	HealthRecordRepo repo;

	@Override
	public HealthRecord savehealth(HealthRecord h) {
		return repo.save(h);
	}

	@Override
	public HealthRecord getHealth(String eposideId) {
		return repo.findByEpisodeId(eposideId);
	}

}
