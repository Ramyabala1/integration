package com.emanas.middleware.Demog;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.auth.AuthService;
import com.emanas.middleware.auth.Pid;
import com.emanas.middleware.config.ConfigurationUrl;
import com.emanas.middleware.models.PatientData;
import com.emanas.middleware.models.PostalCode;
import com.emanas.middleware.redis.model.AuthVerify;
import com.emanas.middleware.redis.model.DemogDetails;
import com.emanas.middleware.redis.model.DemogDetailsDao;
import com.emanas.middleware.redis.model.SendOTP;
import com.emanas.middleware.redis.model.SendOTPDao;
import com.emanas.middleware.utility.LoadConfig;
import com.emanas.middleware.utility.cache.redis.Cache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@SuppressWarnings("serial")
@Service("MOSIP")
public class MosipDemogService implements DemogService {

	@Autowired
	Cache cacheServ;

	@Autowired
	SendOTPDao sendOtpDao;

	@Autowired
	DemogDetailsDao demogDetailDao;

	@Autowired
	Factory authRegistry;

	@Autowired
	ConfigurationUrl configUri;

	ConfigurationUrl configUrl = new ConfigurationUrl();

	Logger logger = LoggerFactory.getLogger(MosipDemogService.class);

	public String sendDemogRequest(Pid pid) {
		return "Create and send request for eManas Authentication";
	}

	// @Override
	@Override
	public String initAuthentication(PatientData patientData) {
		// TODO Auto-generated method stub

		SendOTP otpData = new SendOTP();

		otpData.setContactVal(patientData.getId());
		otpData.setOtpChannel(patientData.getOtpChannel());

		AuthService service = authRegistry.getAuthService("MOS");
		String response = service.sendOTPRequest(otpData);

		if (response != null) {

			return response;
		}
		//
		return null;
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

	@Override
	public AuthVerify verifyAndGetDemogDetails(AuthData authData) {

		AuthVerify auVerify = new AuthVerify();
		SendOTP sendotp = new SendOTP();
		sendotp.setTransactionID(authData.getTransID());
		sendotp.setPurpose(null);
		String cacheVerified = authRegistry.getAuthService("MOS").verifyAuthRequest(sendotp);

		if (cacheVerified != null && cacheVerified.equalsIgnoreCase("Verified")) {

			SendOTP sOTP = new SendOTP();

			try {
				sOTP = sendOtpDao.getOneSendOTP(authData.getTransID());
			} catch (JsonProcessingException e1) {
				// TODO Auto-generated catch block
				auVerify.setError("Data not available");
				return auVerify;
			}
			String tokenId = null;
			if (sOTP != null) {
				tokenId = sOTP.getTokenID();
			}

			ObjectMapper mapper = new ObjectMapper();
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();
			String uri = LoadConfig.getConfigValue("MOSIP_API_URL") + "/sendOTPekycRequest";

			String request = "{\"mosipID\": " + authData.getId() + ", \"transactionID\": " + tokenId + ", \"otp\": "
					+ authData.getOtp() + "}";

			headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(request, headers);
			ResponseEntity<String> response = restTemplate.exchange(uri, HttpMethod.POST, entity, String.class);
			String res = response.getBody();
			JsonNode resp = null;
			try {
				resp = mapper.readValue(response.getBody(), JsonNode.class);
				if (resp.has("entity")) {
					JsonNode jsonEntity = resp.get("entity");
					auVerify.setTransactionId(authData.getTransID());
					if (jsonEntity.has("errorMessage")) {
						auVerify.setError(jsonEntity.get("errorMessage").toString());

					} else {
						DemogDetails demoDet = new DemogDetails();
						if (jsonEntity.get("name_eng").asText().contains("-")) {
							String[] arrOfname = jsonEntity.get("name_eng").asText().split("-", 2);
							if (arrOfname.length > 1) {
								demoDet.setGivenName(arrOfname[0]);
								demoDet.setMiddleName(arrOfname[1]);
							}
						} else if (jsonEntity.get("name_eng").asText().contains(" ")) {
							String[] arrOfname = jsonEntity.get("name_eng").asText().split(" ", 2);
							if (arrOfname.length > 1) {
								demoDet.setGivenName(arrOfname[0]);
								demoDet.setMiddleName(arrOfname[1]);
							} else {
								demoDet.setGivenName(arrOfname[0]);
							}
						} else {
							demoDet.setGivenName(jsonEntity.get("name_eng").asText());
							demoDet.setMiddleName("-");
						}

						try {

							String uri1 = LoadConfig.getConfigValue("POSTALCODE_URL") + "/"
									+ jsonEntity.get("postalCode").asText();

							ResponseEntity<List<PostalCode>> response1 = restTemplate.exchange(uri1, HttpMethod.GET,
									null, new ParameterizedTypeReference<List<PostalCode>>() {
									});
							List<PostalCode> resPostalCode = response1.getBody();

							if (resPostalCode.size() > 0) {
								demoDet.setState(resPostalCode.get(0).getStateName());
								demoDet.setCity(resPostalCode.get(0).getCityName());
								demoDet.setDistrict(resPostalCode.get(0).getDistrict());
								demoDet.setPinCode(jsonEntity.get("postalCode").asText());
								demoDet.setAddress1(jsonEntity.get("fullAddress_eng").asText());
								demoDet.setDateOfBirth(jsonEntity.get("dob").asText());
								demoDet.setEmail(jsonEntity.get("emailId").asText());
								demoDet.setGenderName(jsonEntity.get("gender_eng").asText());
								demoDet.setPhoneNumber(jsonEntity.get("phoneNumber").asText());
								demoDet.setAuthToken(jsonEntity.get("authToken").asText());
								auVerify.setDemodetail(demoDet);
								auVerify.setStatus("Verified");
								demogDetailDao.saveDemogDetails(authData.getTransID(), demoDet);

							} else {
								auVerify.setError("Not a valid PostalCode");
							}

						} catch (Exception e) {
							auVerify.setError("Not a valid PostalCode");
							return auVerify;
						}

					}

				}
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} else {
			auVerify.setError("Not a valid transaction ID");
		}

		return auVerify;

	}

}
