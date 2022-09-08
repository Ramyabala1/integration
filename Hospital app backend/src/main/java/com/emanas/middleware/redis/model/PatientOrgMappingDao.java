
package com.emanas.middleware.redis.model;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Repository
public interface PatientOrgMappingDao {

	void savePatientOrgMap(String transId, PatientOrgMapping patOrgMap);

	PatientOrgMapping getOnePatientOrgMapping(String transId) throws JsonMappingException, JsonProcessingException;

	void deletePatientOrgMapping(String transId);
}
