package com.app.patient.user;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.patient.cache.Cache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class UserCacheService {

	@Autowired
	Cache cache;

	@Autowired
	ObjectMapper objStpper;

	@Autowired
	RestTemplate restTemplate;

	private final String hashReference = "USER";

	public void saveUser(Person usr) {
		cache.saveCache(hashReference, usr.getTransctionId(), usr);

		cache.setExpire(hashReference, 180, TimeUnit.SECONDS);

	}

	public Person getUser(String tranID) throws JsonMappingException, JsonProcessingException {
		Object obj = cache.getCache(hashReference, tranID);
		Person usr = null;
		ObjectMapper mapper = new ObjectMapper();
		if (obj != null) {

			usr = mapper.readValue(mapper.writeValueAsString(obj), Person.class);
			
		}
		return usr;
	}

}
