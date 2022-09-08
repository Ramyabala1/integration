package com.emanas.middleware.Demog;

import java.util.HashMap;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.auth.AuthFactory;
import com.emanas.middleware.auth.AuthService;
import com.emanas.middleware.models.HiuUser;
import com.emanas.middleware.models.PatientData;
import com.emanas.middleware.redis.model.AuthVerify;
import com.emanas.middleware.redis.model.AuthenticationData;
import com.emanas.middleware.redis.model.DemogDetails;
import com.emanas.middleware.redis.model.DemogDetailsDao;
import com.emanas.middleware.redis.model.DemogDetailsImpl;
import com.emanas.middleware.redis.model.SendOTP;
import com.emanas.middleware.utility.security.SecurityService;
import com.fasterxml.jackson.core.JsonProcessingException;

@Service("ABARK")
@Primary
public class AbArkDemogService implements DemogService {

	@Autowired
	SecurityService secServ;

	static HashMap<String, Object> map = new HashMap<String, Object>();

	DemogDetailsDao demogD = new DemogDetailsImpl();

	@Override
	public String initAuthentication(PatientData patientData) {

		DemogServiceType authService = DemogServiceType.ABARK;

		SendOTP otpData = new SendOTP();

		otpData.setContactVal(patientData.getMobile());
		otpData.setContactType("mobile");
		otpData.setPurpose("CONSENT");

		AuthService service = AuthFactory.selectAuthService(authService);
		String response = service.sendOTPRequest(otpData);

		if (response != null) {
			TransactionID demogTransID = new TransactionID();

			AuthenticationData ad = new AuthenticationData();
			ad.setPatientData(patientData);
			ad.setAuthTransID(response);

			map.put(demogTransID.getTransID(), ad);

			return response;
		}

		return null;
	}

	@Override
	public AuthVerify verifyAndGetDemogDetails(AuthData authData) {

		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		HiuUser hiu = (HiuUser) authentication.getPrincipal();

		SendOTP otpData = new SendOTP();

		Object obj = map.get(authData.getTransID());
		AuthenticationData ad = (AuthenticationData) obj;

		otpData.setTransactionID(authData.getTransID());
		otpData.setContactVal(authData.getMobile());
		otpData.setContactType("mobile");
		otpData.setPurpose("CONSENT");
		otpData.setOtp(authData.getOtp());

		DemogServiceType authService = DemogServiceType.ABARK;
		String responseID = AuthFactory.selectAuthService(authService).verifyAuthRequest(otpData);

		AuthVerify auVerify = new AuthVerify();
		auVerify.setStatus(responseID);
		auVerify.setTransactionId(authData.getTransID());
		if (responseID != null) {


			String uri = "http://3.111.8.53:8080/MHMS_DEV/rest/getABARKData/{abId}";
			RestTemplate restTemplate = new RestTemplate();

			HttpHeaders headers = secServ.checkSecurity();

			HttpEntity<String> entity = new HttpEntity<String>(headers);

			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.GET, entity, String.class,
					authData.getId());

			DemogDetails demo = new DemogDetails();


			JSONObject jObject = new JSONObject(response.getBody());

			demo.setIdType("AB-ARK ID");
			demo.setGivenName(jObject.getString("ename"));
			demo.setAddress1(jObject.getString("eaddress"));

			String[] careTaker = jObject.getString("ecareoff").split(": ");
			demo.setContactName(careTaker[1]);
			demo.setDateOfBirth(jObject.getString("dob"));

			String gender = jObject.getString("gender");
			if (gender.equals("M")) {
				demo.setPrefix("Mr.");
				demo.setGenderCode("M");
				demo.setGenderName("Male");
			} else if (gender.equals("F")) {
				demo.setPrefix("Ms.");
				demo.setGenderCode("F");
				demo.setGenderName("Female");
			}

			demo.setCity(jObject.getString("evillage"));
			demo.setState(jObject.getString("estate"));
			demo.setDistrict(jObject.getString("edistrict"));
			demo.setPinCode(jObject.getString("epincode"));
			demo.setPostalCode(jObject.getString("epincode"));
			demo.setEmail(jObject.getString("email"));

			auVerify.setDemodetail(demo);

		}

		return auVerify;
	}

	@Override
	public DemogDetails getRevisedDemogDetails(String transID) {
		DemogDetails demo = null;
		try {
			demo = demogD.getOneDemogDetails(transID);
		} catch (JsonProcessingException e) {

			e.printStackTrace();
		}

		return demo;
	}

}
