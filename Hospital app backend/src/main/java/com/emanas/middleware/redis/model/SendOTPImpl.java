package com.emanas.middleware.redis.model;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emanas.middleware.utility.cache.redis.Cache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class SendOTPImpl implements SendOTPDao {

	// Cache cacheServ = new Cache(redisTemplate);

	@Autowired
	Cache cacheServ;

	private final String hashReference = "OTP";

//	@Resource(name="redisTemplate")          // 'redisTemplate' is defined as a Bean in AppConfig.java
//    private HashOperations<String, Integer, SendOTP> hashOperations;

	@Override
	public void saveSendOTP(SendOTP otp) {


		cacheServ.saveCache(hashReference, otp.getTransactionID(), otp);

		cacheServ.setExpire(hashReference, 60, TimeUnit.SECONDS);


	}

	@Override
	public void deleteSendOTP(String otp) {
		cacheServ.clearCache(hashReference, otp);


	}

	@Override
	public SendOTP getOneSendOTP(String otp) throws JsonMappingException, JsonProcessingException {

		Object obj = cacheServ.getCache(hashReference, otp);
		SendOTP sOtp = null;
		ObjectMapper mapper = new ObjectMapper();
		if (obj != null) {
			sOtp = mapper.readValue(mapper.writeValueAsString(obj), SendOTP.class);
		}

		return sOtp;

	}

}
