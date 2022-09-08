package com.emanas.middleware.auth;

import com.emanas.middleware.redis.model.SendOTP;


public interface AuthService {
	String sendOTPRequest(SendOTP otpData);
	String verifyAuthRequest(SendOTP otpData);

}
