package com.emanas.middleware.Demog;

import com.emanas.middleware.models.PatientData;
import com.emanas.middleware.redis.model.AuthVerify;
import com.emanas.middleware.redis.model.DemogDetails;
import java.io.Serializable;

public interface DemogService extends Serializable {
	String initAuthentication(PatientData patientData);

	AuthVerify verifyAndGetDemogDetails(AuthData authData);

	DemogDetails getRevisedDemogDetails(String transID);
}
