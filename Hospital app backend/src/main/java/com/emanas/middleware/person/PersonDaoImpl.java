package com.emanas.middleware.person;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.utility.cache.redis.Cache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PersonDaoImpl implements PersonDao {
	@Autowired
	Cache cacheServ;

	@Autowired
	ObjectMapper objStpper;

	@Autowired
	RestTemplate restTemplate;

	private final String hashReference = "CONSENT";

	@Override
	public void savePerson(Person person) {
		cacheServ.saveCache(hashReference, person.getRequestID(), person);

		cacheServ.setExpire(hashReference, 180, TimeUnit.SECONDS);

	}

	@Override
	public Person getPerson(String reqID) throws JsonMappingException, JsonProcessingException {
		Object obj = cacheServ.getCache(hashReference, reqID);
		Person personObj = null;
		ObjectMapper mapper = new ObjectMapper();
		if (obj != null) {

			personObj = mapper.readValue(mapper.writeValueAsString(obj), Person.class);

		}
		return personObj;
	}

	@Override
	public void deletePerson(String consentID) {
		cacheServ.clearCache(hashReference, consentID);

	}

}
