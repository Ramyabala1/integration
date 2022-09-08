package com.emanas.middleware.log;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EhrAccessLogRepo extends JpaRepository<EhrAccessLog, Integer> {

	EhrAccessLogRepo save(EhrAccessLogRepo ehrLog);

	List<EhrAccessLog> findByPatientID(String patId);

}
