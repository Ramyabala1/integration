package com.emanas.middleware.authorisedEntity;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emanas.middleware.utility.cache.redis.Cache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AEDetailsDaoImpl implements AEDetailsDao {

	@Autowired
	Cache cacheServ;

	@Autowired
	ObjectMapper objStpper;

	private final String hashReference = "AEDETAILS";

	@Override
	public void saveAEDetails(AEDetails aeDetails) {
		cacheServ.saveCache(hashReference, aeDetails.getTransID(), aeDetails);
		cacheServ.setExpire(hashReference, 180, TimeUnit.SECONDS);

	}

	@Override
	public AEDetails getAEDetails(String transID) throws JsonMappingException, JsonProcessingException {
		Object obj = cacheServ.getCache(hashReference, transID);
		AEDetails aeDetails = null;
		ObjectMapper mapper = new ObjectMapper();
		if (obj != null) {

			aeDetails = mapper.readValue(mapper.writeValueAsString(obj), AEDetails.class);

		}
		return aeDetails;
	}

	@Override
	public void deleteConsent(String transID) {
		cacheServ.clearCache(hashReference, transID);

	}

}
