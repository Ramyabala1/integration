package com.emanas.middleware.log;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EhrAccessLogService {

	@Autowired
	EhrAccessLogRepo repo;

	public Boolean saveLog(EhrAccessLog ehrLog) {

		ehrLog.setCreatedDate(new Date());

		ehrLog.setSource("HospitalApp");
		EhrAccessLog ehrLog1 = repo.save(ehrLog);

		if (ehrLog1 != null) {
			return true;
		}
		return false;
	}

	public List<EhrAccessLog> getAccessLog(String patientId) {

		return repo.findByPatientID(patientId);

	}

}
