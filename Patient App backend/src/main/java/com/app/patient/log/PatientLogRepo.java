package com.app.patient.log;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PatientLogRepo extends JpaRepository<PatientLog, Integer> {

	PatientLogRepo save(PatientLogRepo patLog);

}
