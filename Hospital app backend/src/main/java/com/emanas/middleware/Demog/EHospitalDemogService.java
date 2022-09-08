package com.emanas.middleware.Demog;

import com.emanas.middleware.auth.AuthFactory;
import com.emanas.middleware.auth.AuthService;
import com.emanas.middleware.auth.Pid;
import com.emanas.middleware.models.PatientData;
import com.emanas.middleware.redis.model.AuthVerify;
import com.emanas.middleware.redis.model.AuthenticationData;
import com.emanas.middleware.redis.model.DemogDetails;
import com.emanas.middleware.redis.model.SendOTP;

public class EHospitalDemogService implements DemogService {

	public String sendDemogRequest(Pid pid) {
		return "Create and send request for eManas Authentication";
	}

	@Override
	public String initAuthentication(PatientData patientData) {
		// TODO Auto-generated method stub

		DemogServiceType authService = DemogServiceType.EHOSPITAL;

		SendOTP otpData = new SendOTP();

		otpData.setContactVal(patientData.getMobile());
		otpData.setContactType("mobile");
		otpData.setPurpose("CONSENT");

		AuthService service = AuthFactory.selectAuthService(authService);
		String response = service.sendOTPRequest(otpData);
//		String response = AuthFactory.selectAuthService(authService).sendOTPRequest(otpData);

//		System.out.println(response);

		if (response != null) {
			TransactionID demogTransID = new TransactionID();

			// save transID with patientData in cache
			AuthenticationData ad = new AuthenticationData();
			ad.setPatientData(patientData);
			ad.setAuthTransID(response);
//			authenticationDataService.saveAuth(demogTransID.getTransID(), ad);
//			cacheServ.saveCache("AuthData", demogTransID.getTransID(), ad);

			return demogTransID.getTransID();
		}

		return null;
	}

	@Override
	public AuthVerify verifyAndGetDemogDetails(AuthData authData) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public DemogDetails getRevisedDemogDetails(String transID) {
		// TODO Auto-generated method stub
		return null;
	}
}
