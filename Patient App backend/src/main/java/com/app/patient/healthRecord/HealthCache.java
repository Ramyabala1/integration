package com.app.patient.healthRecord;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.patient.cache.Cache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HealthCache {
	@Autowired
	Cache cache;

	@Autowired
	ObjectMapper objStpper;

	@Autowired
	RestTemplate restTemplate;

	private final String hashReference = "HealthRecord";

	public void saveHealth(HealthRecord health) {
		cache.saveCache(hashReference, health.getTransactionId(), health);

	}

	public HealthRecord gethealth(String tranID) throws JsonMappingException, JsonProcessingException {
		Object obj = cache.getCache(hashReference, tranID);
		HealthRecord health = null;
		ObjectMapper mapper = new ObjectMapper();
		if (obj != null) {

			health = mapper.readValue(mapper.writeValueAsString(obj), HealthRecord.class);

		}
		return health;
	}

	public void deleteHealth(String transID) {
		cache.clearCache(hashReference, transID);

	}
}
