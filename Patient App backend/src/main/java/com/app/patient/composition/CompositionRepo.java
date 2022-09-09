package com.app.patient.composition;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.app.patient.healthRecord.HealthRecord;

@Repository
public interface CompositionRepo extends JpaRepository<Composition, Integer> {

	@Override
	Composition save(Composition h);

	List<Composition> findByHealth(HealthRecord h);

	Composition findByCompositionId(String compId);

}
