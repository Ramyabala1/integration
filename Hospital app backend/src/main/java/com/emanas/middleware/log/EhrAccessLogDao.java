package com.emanas.middleware.log;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface EhrAccessLogDao {

	void saveEhrAccessLog(EhrAccessLog ehrLog);

	EhrAccessLog getEhrAccessLog(String reqID) throws JsonMappingException, JsonProcessingException;

	void deleteEhrAccessLog(String transID);

}
