package com.emanas.middleware.Demog;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emanas.middleware.auth.AuthService;
import com.emanas.middleware.models.PatientData;
import com.emanas.middleware.redis.model.AuthVerify;
import com.emanas.middleware.redis.model.AuthenticationData;
import com.emanas.middleware.redis.model.DemogDetails;
import com.emanas.middleware.redis.model.DemogDetailsDao;
import com.emanas.middleware.redis.model.SendOTP;
import com.emanas.middleware.utility.cache.redis.Cache;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service("DEFAULT")
public class DefaultDemogService implements DemogService {

	@Autowired
	Cache cacheServ;

	@Autowired
	Factory authRegistry;

	@Autowired
	DemogDetailsDao demogDetailDao;

	@Override
	public String initAuthentication(PatientData patientData) {

		SendOTP otpData = new SendOTP();

		otpData.setContactVal(patientData.getMobile());
		otpData.setContactType("mobile");
		otpData.setPurpose("CONSENT");

		AuthService service = authRegistry.getAuthService("EMANAS");
		String response = service.sendOTPRequest(otpData);

		return response;
	}

	@Override
	public AuthVerify verifyAndGetDemogDetails(AuthData authData) {

		SendOTP otpData = new SendOTP();

		AuthenticationData ad = new AuthenticationData();
		otpData.setTransactionID(authData.getTransID());
		otpData.setContactVal(authData.getMobile());
		otpData.setContactType("mobile");
		otpData.setPurpose("CONSENT");
		otpData.setOtp(authData.getOtp());

		String response = authRegistry.getAuthService("EMANAS").verifyAuthRequest(otpData);

		AuthVerify auVerify = new AuthVerify();
		auVerify.setStatus(response);
		if (response != null && (!response.contains("error-")) && (response.length() != 5)) {
			auVerify.setTransactionId(authData.getTransID());
		} else if (response.length() == 5) {
			auVerify.setError(response);
			return auVerify;
		} else {
			auVerify.setError(response);
			return auVerify;
		}

		if (response != null && (authData.getDemo() != null && authData.getDemo().equalsIgnoreCase("VerifyOnly"))) {
			DemogDetails demog = new DemogDetails();
			demog.setPhoneNumber(authData.getMobile());
			auVerify.setDemodetail(demog);
		}
		return auVerify;

	}

	@Override
	public DemogDetails getRevisedDemogDetails(String transID) {
		DemogDetails demog = new DemogDetails();
		try {
			demog = demogDetailDao.getOneDemogDetails(transID);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			return null;
		}
		return demog;
	}

}
