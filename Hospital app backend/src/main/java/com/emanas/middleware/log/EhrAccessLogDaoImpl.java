package com.emanas.middleware.log;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.utility.cache.redis.Cache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class EhrAccessLogDaoImpl implements EhrAccessLogDao {

	@Autowired
	Cache cacheServ;

	@Autowired
	ObjectMapper objStpper;

	@Autowired
	RestTemplate restTemplate;

	private final String hashReference = "EhrAccessLog";

	@Override
	public void saveEhrAccessLog(EhrAccessLog ehrLog) {
		cacheServ.saveCache(hashReference, ehrLog.getTransID(), ehrLog);
		cacheServ.setExpire(hashReference, 180, TimeUnit.SECONDS);

	}

	@Override
	public EhrAccessLog getEhrAccessLog(String reqID) throws JsonMappingException, JsonProcessingException {
		Object obj = cacheServ.getCache(hashReference, reqID);
		EhrAccessLog patObj = null;
		ObjectMapper mapper = new ObjectMapper();
		if (obj != null) {
			patObj = mapper.readValue(mapper.writeValueAsString(obj), EhrAccessLog.class);
		}
		return patObj;
	}

	@Override
	public void deleteEhrAccessLog(String transID) {
		cacheServ.clearCache(hashReference, transID);

	}

}
