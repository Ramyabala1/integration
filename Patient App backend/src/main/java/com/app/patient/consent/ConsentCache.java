package com.app.patient.consent;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.patient.cache.Cache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConsentCache {

	@Autowired
	Cache cache;

	@Autowired
	ObjectMapper objStpper;

	@Autowired
	RestTemplate restTemplate;

	private final String hashReference = "Consent";

	public void saveUser(Consent c) {
		cache.saveCache(hashReference, c.getTransactionId(), c);

		cache.setExpire(hashReference, 180, TimeUnit.SECONDS);

	}

	public Consent getConsent(String tranID) throws JsonMappingException, JsonProcessingException {
		Object obj = cache.getCache(hashReference, tranID);
		Consent c = null;
		ObjectMapper mapper = new ObjectMapper();
		if (obj != null) {

			c = mapper.readValue(mapper.writeValueAsString(obj), Consent.class);

		}
		return c;
	}

}
