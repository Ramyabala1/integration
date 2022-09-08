package com.emanas.middleware.redis.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@Service
public class SendOTPService {

	private final SendOTPDao sendOTPDao;

	@Autowired
	public SendOTPService(SendOTPDao sendOTPDao) {
		this.sendOTPDao = sendOTPDao;
	}

	public void saveOtp(SendOTP otp) {
		sendOTPDao.saveSendOTP(otp);
	}

	public SendOTP getOneAuthenticationData(String otp) throws JsonMappingException, JsonProcessingException {
		return sendOTPDao.getOneSendOTP(otp);
	}

}
