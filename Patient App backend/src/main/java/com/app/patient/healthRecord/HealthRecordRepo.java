package com.app.patient.healthRecord;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HealthRecordRepo extends JpaRepository<HealthRecord, Integer> {

	@Override
	@SuppressWarnings("unchecked")
	HealthRecord save(HealthRecord h);

	List<HealthRecord> findByEmanas(String patId);

	HealthRecord findByEpisodeId(String episodeId);

}
