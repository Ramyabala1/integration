package com.emanas.middleware.patient;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.Demog.AuthData;
import com.emanas.middleware.Demog.DemogService;
import com.emanas.middleware.Demog.DemogServiceType;
import com.emanas.middleware.Demog.Factory;
import com.emanas.middleware.auth.AuthService;
import com.emanas.middleware.consent.Consent;
import com.emanas.middleware.consent.ConsentDao;
import com.emanas.middleware.consent.ConsentService;
import com.emanas.middleware.models.HiuUser;
import com.emanas.middleware.models.Patient;
import com.emanas.middleware.models.PatientData;
import com.emanas.middleware.person.Person;
import com.emanas.middleware.person.PersonAuthenticationService;
import com.emanas.middleware.person.PersonValidationService;
import com.emanas.middleware.redis.model.AuthVerify;
import com.emanas.middleware.redis.model.DemogDetails;
import com.emanas.middleware.redis.model.PatientOrgMappingDao;
import com.emanas.middleware.redis.model.SendOTP;
import com.emanas.middleware.utility.LoadConfig;
import com.emanas.middleware.utility.cache.redis.Cache;
import com.emanas.middleware.utility.security.SecurityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PatientService {

	@Autowired
	DemogService demogServ;

	@Autowired
	Cache cacheServ;

	@Autowired
	Factory authRegistry;

	@Autowired
	PersonValidationService personServ;

	@Autowired
	ObjectMapper objMapper;

	@Autowired
	AuthService authServ;

	@Autowired
	ConsentDao consentDao;

	@Autowired
	PatientOrgMappingDao patOrgDao;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	PersonAuthenticationService perServ;

	@Autowired
	ConsentService conServ;

	@Autowired
	SecurityService secServ;

	public String initAuthentication(PatientData patData) {
		DemogServiceType demoType = Enum.valueOf(DemogServiceType.class, patData.getService());
		demogServ = authRegistry.getService(patData.getService());
		String transData = demogServ.initAuthentication(patData);
		return transData;
	}

	public String triggerOTP() {
		return null;

	}

	public AuthVerify verifyAndGetBaseDetails(String data) throws JsonMappingException, JsonProcessingException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(data, JsonNode.class);
		String service = node.get("service").asText();
		String otp = node.get("otp").asText();
		String transactionId = node.get("transactionId").asText();
		String timestamp = node.get("timestamp").asText();
		String id = null;
		if (node.has("idnumber")) {
			id = node.get("idnumber").asText();
		}
		String mobile = null;
		if (node.has("mobile")) {
			mobile = node.get("mobile").asText();
		}

		demogServ = authRegistry.getService(service);
		AuthData au = new AuthData();
		au.setId(id);
		au.setTransID(transactionId);
		au.setOtp(otp);
		au.setMobile(mobile);
		au.setDemo("VerifyOnly");

		AuthVerify demoDetails = demogServ.verifyAndGetDemogDetails(au);
		return demoDetails;
	}

	public Boolean getRevisedDemoDetails(String data) throws JsonMappingException, JsonProcessingException {
		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode datanode = objStringMapper.readValue(data, JsonNode.class);
		JsonNode node = datanode.get("demographics");
		String idType = null;
		String service = null;

		if (node.has("idType")) {
			idType = node.get("idType").asText();
		}

		if (idType.equalsIgnoreCase("None")) {
			service = "DEFAULT";
		} else if (idType.equalsIgnoreCase("AB-ARK ID")) {
			service = "ABARK";
		} else if (idType.equalsIgnoreCase("MOSIP ID")) {
			service = "MOSIP";
		} else if (idType.equalsIgnoreCase("eHospital UHID")) {
			service = "EHOSPITAL";
		}

		demogServ = authRegistry.getService(service);
		if (idType != null && datanode.has("transactionId")) {
			DemogDetails demoDetails = demogServ.getRevisedDemogDetails(datanode.get("transactionId").asText());

			if (demoDetails != null) {
				if (demoDetails.getPhoneNumber() != null && node.has("phoneNumber")) {

					if (!demoDetails.getPhoneNumber().equalsIgnoreCase(node.get("phoneNumber").asText())) {
						return false;
					}

				}
				if (demoDetails.getGivenName() != null && node.has("givenName")) {
					if (!demoDetails.getGivenName().equalsIgnoreCase(node.get("givenName").asText())) {
						return false;
					}

				}
				if (demoDetails.getMiddleName() != null && node.has("middleName")) {
					if (!demoDetails.getMiddleName().equalsIgnoreCase(node.get("middleName").asText())) {
						return false;
					}

				}
				if (demoDetails.getEmail() != null && node.has("email")) {
					if (!demoDetails.getEmail().equalsIgnoreCase(node.get("email").asText())) {
						return false;
					}

				}

				JsonNode address = node.get("address");

				if (demoDetails.getCity() != null && address.has("city")) {
					if (!demoDetails.getCity().equalsIgnoreCase(node.get("city").asText())) {
						return false;
					}

				}
				if (demoDetails.getState() != null && address.has("state")) {
					if (!demoDetails.getState().equalsIgnoreCase(node.get("state").asText())) {
						return false;
					}

				}
				if (demoDetails.getPinCode() != null && address.has("pinCode")) {
					if (!demoDetails.getPinCode().equalsIgnoreCase(node.get("pinCode").asText())) {
						return false;
					}

				}
			}

		}
		return true;
	}

	public String initVerification(String patData) {
		SendOTP sendOTP = new SendOTP();
		JsonNode patientNode = null;
		String patientDetails = null;
		try {
			patientNode = objMapper.readValue(patData, JsonNode.class);
			JsonNode patient = patientNode.get("patient");

			String eManasId = patient.get("emanasId").asText();
			String service = patient.get("authService").asText();
			JsonNode serviceParam = null;
			if (eManasId != null) {
				personServ = authRegistry.getPersonService("PATIENT");
				patientDetails = personServ.fetchPersonRecord(eManasId);
				Patient patObj = null;
				if (patientDetails != null) {
					patObj = objMapper.readValue(patientDetails, Patient.class);
					if (patObj != null) {
						patObj = objMapper.readValue(patientDetails, Patient.class);
						if (service.equalsIgnoreCase("MOSIP")) {
							authServ = authRegistry.getAuthService("MOS");
							if (patient.has("serviceParameters")) {
								serviceParam = patient.get("serviceParameters");
								List<String> contactParam = new ArrayList<String>();
								final JsonNode arrNode = serviceParam.get("otpChannel");
								if (arrNode.isArray()) {
									for (final JsonNode objNode : arrNode) {
										contactParam.add(objNode.asText());
									}
								}

								sendOTP.setContactVal(serviceParam.get("individualId").asText());
								sendOTP.setOtpChannel(contactParam);
							}
						} else {
							authServ = authRegistry.getAuthService("EMANAS");
							sendOTP.setContactVal(patObj.getPhoneNumber());
							sendOTP.setContactType("mobile");
							sendOTP.setPurpose("CONSENT");
						}

						String response = authServ.sendOTPRequest(sendOTP);
						if (response != null && !response.contains("error")) {

							Consent objData = new Consent();
							objData.setDateCreated(new Date());

							objData.setAuthorisationType("SMSOTP");

							objData.setConsentrequestID(response);
							objData.setService(service);
							objData.seteManasId(eManasId);
							objData.setAuthtransactionId(response);

							if (service.equalsIgnoreCase("MOSIP")) {

								for (int i = 0; i < patObj.getIdproof().length; i++) {
									if (patObj.getIdproof()[i].getType().equalsIgnoreCase("MOSIP ID")) {
										objData.setMosipAuthToken(patObj.getIdproof()[i].getIdNumber());
										objData.setMosipID(serviceParam.get("individualId").asText());
									}
								}

							} else {
								objData.setPhoneNumber(patObj.getPhoneNumber());
							}

							consentDao.saveConsent(objData);
							return response;

						} else if (response != null && response.contains("error")) {
							return response;
						}

						else {
							return "error-Service Down";
						}

					} else {
						return "errorE1001-Could not parse patient";
					}
				} else {
					return "errorE1000-Patient not found";
				}
			} else {
				return "errorE1000-Patient ID not found";
			}
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			return "errorE1001-Could not parse patient";
		}

	}

	public String registerPatient(String patData) throws JsonMappingException, JsonProcessingException {
		try {
			JsonNode consentNode = null;

			consentNode = objMapper.readValue(patData, JsonNode.class);
			SendOTP sendOtp = new SendOTP();

			String transactionId = consentNode.get("transactionId").asText();
			String otp = consentNode.get("otp").get("value").asText();
			Consent consentObj = consentDao.getConsent(transactionId);
			if (consentObj != null && consentObj.getConsentrequestID().equalsIgnoreCase(transactionId)) {

				if (consentObj.getService().equalsIgnoreCase("MOSIP")) {
					authServ = authRegistry.getAuthService("MOS");
					sendOtp.setContactVal(consentObj.getMosipID());
				} else {
					authServ = authRegistry.getAuthService("EMANAS");
					sendOtp.setContactType("mobile");
					sendOtp.setContactVal(consentObj.getPhoneNumber());
				}

				sendOtp.setOtp(otp);
				sendOtp.setPurpose("CONSENT");
				sendOtp.setTransactionID(consentObj.getAuthtransactionId());

				String response = authServ.verifyAuthRequest(sendOtp);

				if (response != null && response.contains("error")) {
					return response;
				} else if (response != null) {
					if (response.equalsIgnoreCase(consentObj.getMosipAuthToken())
							|| response.equalsIgnoreCase("OTP Verified")) {

						return "Verified";
					} else {
						return "errorE1003-Please verify the UID entered";
					}
				} else {
					return "errorE1002-Not a valid consent to authorise";
				}

			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String initPatRegInEst(String req) {

		String transDetails = perServ.initVerification(req);

		return transDetails;

	}

	public String registerPatientToEstablish(String data) throws JsonMappingException, JsonProcessingException {

		String response = perServ.verifyAuthRequest(data);
		if ((response != null && !response.contains("error"))
				|| (response != null && response.length() != 5 && !response.substring(0, 1).contains("E"))) {
			Person person = objMapper.readValue(response, Person.class);

			HttpHeaders headers = secServ.checkSecurity();
			HiuUser hiu = secServ.getHIU();
			hiu.setHospitalName(person.getMhpId());
			hiu.setUserName(person.geteManasId());
			String str = objMapper.writeValueAsString(hiu);
			HttpEntity<String> entity1 = new HttpEntity<String>(str, headers);
			Boolean orgmap = restTemplate.postForObject(LoadConfig.getConfigValue("ORGMAPPING"), entity1,
					Boolean.class);

			if (orgmap) {
				return "Patient Mapped";
			} else {

				return "E1523";
			}

		} else if ((response != null && response.length() != 5 && !response.substring(0, 1).contains("E"))) {
			return response;
		}

		return response;
	}

	public String initAssignAuthEntity(String data)
			throws JsonMappingException, JsonProcessingException, ParseException {

		String response = conServ.initConsent(data);

		return response;

	}

	public String assignAuthEntity(String data) throws JsonMappingException, JsonProcessingException, ParseException {

		JsonNode consentNode = null;
		String res = null;
		consentNode = objMapper.readValue(data, JsonNode.class);
		String response = conServ.createConsent(data);
		if (response != null && !response.contains("error")) {
			String transactionId = consentNode.get("transactionId").asText();

			Consent consentObj = consentDao.getConsent(transactionId);
			if (consentObj != null && consentObj.getConsentrequestID().equalsIgnoreCase(transactionId)) {

				if (consentObj.getRequest() != null) {

					JsonNode requestNode = objMapper.readValue(consentObj.getRequest(), JsonNode.class);
					JsonNode personNode = requestNode.get("aeDetails");
					String personID = personNode.get("aeId").asText();
					String emanasID = personNode.get("patientId").asText();

					HttpHeaders headers = secServ.checkSecurity();

					String req = "{\"patientID\":\"" + emanasID + "\",\"aeID\":\"" + personID + "\"}";

					HttpEntity<String> entity = new HttpEntity<String>(req, headers);

					String url = LoadConfig.getConfigValue("MAPPATIENTAUTHENTITY");
					res = restTemplate.postForObject(url, entity, String.class);

					if (res != null && !res.contains("error")) {
						return response;
					}

				}

			} else {
				return "E1002";
			}

		} else {
			return response;
		}
		return res;

	}

}
