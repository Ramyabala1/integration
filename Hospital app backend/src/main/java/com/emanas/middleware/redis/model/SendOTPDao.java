package com.emanas.middleware.redis.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

public interface SendOTPDao {

	void saveSendOTP(SendOTP otp);

	SendOTP getOneSendOTP(String otp) throws JsonMappingException, JsonProcessingException;

	void deleteSendOTP(String tokenid);

}
