package com.emanas.middleware.redis.model;

import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.stereotype.Component;

import com.emanas.middleware.utility.cache.redis.Cache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class DemogDetailsImpl implements DemogDetailsDao {

	@Autowired
	Cache cacheServ;

	private final String hashReference = "DemogData";

	@Resource(name = "redisTemplate") // 'redisTemplate' is defined as a Bean in AppConfig.java
	private HashOperations<String, Integer, DemogDetails> hashOperations;

	@Override
	public void saveDemogDetails(String demogTransID, DemogDetails demog) {
		cacheServ.saveCache(hashReference, demogTransID, demog);

		cacheServ.setExpire(hashReference, 900, TimeUnit.SECONDS);

	}

	@Override
	public void deleteDemogDetails(String demogTransID) {
		cacheServ.clearCache(hashReference, demogTransID);

	}

	@Override
	public DemogDetails getOneDemogDetails(String demogTransID) throws JsonMappingException, JsonProcessingException {

		Object obj = cacheServ.getCache(hashReference, demogTransID);
		DemogDetails demog = null;
		ObjectMapper mapper = new ObjectMapper();
		if (obj != null) {
			demog = mapper.readValue(mapper.writeValueAsString(obj), DemogDetails.class);
		}

		return demog;

	}
}
