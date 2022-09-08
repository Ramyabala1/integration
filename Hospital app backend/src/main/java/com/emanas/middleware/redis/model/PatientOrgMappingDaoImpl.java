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
public class PatientOrgMappingDaoImpl implements PatientOrgMappingDao {

	@Autowired
	Cache cacheServ;

	private final String hashReference = "PatOrgMap";

	@Resource(name = "redisTemplate") // 'redisTemplate' is defined as a Bean in AppConfig.java
	private HashOperations<String, Integer, PatientOrgMapping> hashOperations;

	@Override
	public void savePatientOrgMap(String transID, PatientOrgMapping patOrgMap) {

		cacheServ.saveCache(hashReference, transID, patOrgMap);

		cacheServ.setExpire(hashReference, 900, TimeUnit.SECONDS);

	}

	@Override
	public void deletePatientOrgMapping(String transID) {
		cacheServ.clearCache(hashReference, transID);

	}

	@Override
	public PatientOrgMapping getOnePatientOrgMapping(String transID)
			throws JsonMappingException, JsonProcessingException {

		Object obj = cacheServ.getCache(hashReference, transID);
		PatientOrgMapping patOrgMap = null;
		ObjectMapper mapper = new ObjectMapper();
		if (obj != null) {
			patOrgMap = mapper.readValue(mapper.writeValueAsString(obj), PatientOrgMapping.class);
		}
		return patOrgMap;

	}
}
