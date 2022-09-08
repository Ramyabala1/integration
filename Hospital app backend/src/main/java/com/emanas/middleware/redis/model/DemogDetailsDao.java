package com.emanas.middleware.redis.model;

import org.springframework.stereotype.Repository;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Repository
public interface DemogDetailsDao {

	void saveDemogDetails(String demogTransID, DemogDetails demog);

	DemogDetails getOneDemogDetails(String demogTransID) throws JsonMappingException, JsonProcessingException;

	void deleteDemogDetails(String demogTransID);
}
