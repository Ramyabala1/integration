package com.emanas.middleware.person;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emanas.middleware.Demog.Factory;
import com.emanas.middleware.auth.AuthService;
import com.emanas.middleware.models.AuthorisedEntity;
import com.emanas.middleware.models.Patient;
import com.emanas.middleware.redis.model.SendOTP;
import com.emanas.middleware.utility.cache.redis.Cache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PersonAuthenticationService {

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
	PersonDao personDao;

	public String initVerification(String patData) {
		SendOTP sendOTP = new SendOTP();
		JsonNode patientNode = null;
		String patientDetails = null;
		String aeID = null;
		JsonNode patient = null;
		String eManasId = null;
		try {
			patientNode = objMapper.readValue(patData, JsonNode.class);

			if (patientNode.has("patient")) {
				patient = patientNode.get("patient");
				eManasId = patient.get("patientId").asText();
			} else if (patientNode.has("authorizedEntity")) {
				patient = patientNode.get("authorizedEntity");
				eManasId = patient.get("aeId").asText();
			} else if (patientNode.has("person")) {
				patient = patientNode.get("person");
				eManasId = patient.get("patientId").asText();
			}

			String service = patient.get("authService").asText();
			String mhpId = null;
			String estId = null;


			if (patientNode.has("establishment")) {
				JsonNode establishment = patientNode.get("establishment");
				mhpId = establishment.get("userId").asText();
				estId = establishment.get("establishmentId").asText();

			}

			JsonNode serviceParam = null;
			if (eManasId != null) {

				if (patientNode.has("patient")) {
					personServ = authRegistry.getPersonService("PATIENT");
					patientDetails = personServ.fetchPersonRecord(eManasId);
				} else if (patientNode.has("authorizedEntity")) {
					personServ = authRegistry.getPersonService("AUTHORIZEDENTITY");
					patientDetails = personServ.fetchPersonRecord(eManasId);
				} else if (patientNode.has("person")) {
					JsonNode person = patientNode.get("person");
					String type = person.get("type").asText();
					if (type.equalsIgnoreCase("authorized_entity")) {
						aeID = person.get("aeId").asText();
						personServ = authRegistry.getPersonService("AUTHORIZEDENTITY");
						patientDetails = personServ.fetchPersonRecord(aeID);
					} else if (type.equalsIgnoreCase("patient")) {
						personServ = authRegistry.getPersonService("PATIENT");
						patientDetails = personServ.fetchPersonRecord(eManasId);
					} else {
						personServ = authRegistry.getPersonService(type);
						patientDetails = personServ.fetchPersonRecord(eManasId);
					}
				}
				Patient patObj = null;
				AuthorisedEntity authObj = null;
				if (patientDetails != null && !patientDetails.contains("not")) {
					if (patientDetails.contains("KA-AE")) {
						authObj = objMapper.readValue(patientDetails, AuthorisedEntity.class);
					} else {
						patObj = objMapper.readValue(patientDetails, Patient.class);
					}

					if (patObj != null || authObj != null) {

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
							if (patObj != null) {
								sendOTP.setContactVal(patObj.getPhoneNumber());
							} else if (authObj != null) {
								sendOTP.setContactVal(authObj.getPhoneNumber());
							}

							sendOTP.setContactType("mobile");
							sendOTP.setPurpose("CONSENT");
						}


						String response = authServ.sendOTPRequest(sendOTP);


						if (response != null && !response.contains("error")) {

							Person objData = new Person();
							objData.setDateCreated(new Date());

							objData.setAuthorisationType("SMSOTP");

							objData.setRequestID(response);
							objData.setService(service);
							objData.seteManasId(eManasId);
							objData.setAuthtransactionId(response);



							if (service.equalsIgnoreCase("MOSIP")) {

								if (patObj != null) {
									for (int i = 0; i < patObj.getIdproof().length; i++) {
										if (patObj.getIdproof()[i].getType().equalsIgnoreCase("MOSIP ID")) {
											objData.setMosipAuthToken(patObj.getIdproof()[i].getIdNumber());
											objData.setMosipID(serviceParam.get("individualId").asText());
										}
									}
								}



								if (authObj != null && authObj.getIdType().equalsIgnoreCase("MOSIP ID")) {
									objData.setMosipAuthToken(authObj.getIdNumber());
									objData.setMosipID(serviceParam.get("individualId").asText());
								}

							} else {
								if (patObj != null) {
									objData.setPhoneNumber(patObj.getPhoneNumber());
								} else if (authObj != null) {
									objData.setPhoneNumber(authObj.getPhoneNumber());
								}

							}

							if (mhpId != null && estId != null) {
								objData.setMhpId(mhpId);
								objData.setEstablishmentID(estId);
							}



							personDao.savePerson(objData);

							return response;

						} else if (response != null && response.contains("error")) {
							return response;
						}

						else {
							return "E1005";
						}

					} else {
						return "E1001";
					}
				} else {
					return "E1000";
				}
			} else {
				return "E1000";
			}
		} catch (JsonProcessingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			return "E1000";
		}

	}

	public String registerPatient(String patData) throws JsonMappingException, JsonProcessingException {
		try {
			JsonNode personNode = null;

			personNode = objMapper.readValue(patData, JsonNode.class);
			SendOTP sendOtp = new SendOTP();

			String transactionId = personNode.get("transactionId").asText();

			String otp = null;
			if (personNode.get("otp").has("value")) {
				otp = personNode.get("otp").get("value").asText();
			} else {
				otp = personNode.get("otp").asText();
			}
			Person personObj = personDao.getPerson(transactionId);

			if (personObj != null && personObj.getRequestID().equalsIgnoreCase(transactionId)) {

				if (personObj.getService().equalsIgnoreCase("MOSIP")) {
					authServ = authRegistry.getAuthService("MOS");
					sendOtp.setContactVal(personObj.getMosipID());
				} else {
					authServ = authRegistry.getAuthService("EMANAS");
					sendOtp.setContactType("mobile");
					sendOtp.setContactVal(personObj.getPhoneNumber());
				}

				sendOtp.setOtp(otp);
				sendOtp.setPurpose("CONSENT");
				sendOtp.setTransactionID(personObj.getAuthtransactionId());

				String response = authServ.verifyAuthRequest(sendOtp);

				if (response != null && response.contains("error")) {
					return response;
				} else if (response != null) {
					if (response.equalsIgnoreCase(personObj.getMosipAuthToken())
							|| response.equalsIgnoreCase("OTP Verified")) {

						return "Verified";
					} else {
						return "E1003";
					}
				} else {
					return "E1002";
				}

			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public String verifyAuthRequest(String patData) throws JsonMappingException, JsonProcessingException {
		try {
			JsonNode personNode = null;

			personNode = objMapper.readValue(patData, JsonNode.class);
			SendOTP sendOtp = new SendOTP();

			String transactionId = personNode.get("transactionId").asText();
			String otp = personNode.get("otp").asText();
			Person personObj = personDao.getPerson(transactionId);
			if (personObj != null && personObj.getRequestID().equalsIgnoreCase(transactionId)) {

				if (personObj.getService().equalsIgnoreCase("MOSIP")) {
					authServ = authRegistry.getAuthService("MOS");
					sendOtp.setContactVal(personObj.getMosipID());
				} else {
					authServ = authRegistry.getAuthService("EMANAS");
					sendOtp.setContactType("mobile");
					sendOtp.setContactVal(personObj.getPhoneNumber());
				}

				sendOtp.setOtp(otp);
				sendOtp.setPurpose("CONSENT");
				sendOtp.setTransactionID(personObj.getAuthtransactionId());

				String response = authServ.verifyAuthRequest(sendOtp);

				if (response != null && response.contains("error")) {
					return response;
				} else if (response != null) {
					if (response.equalsIgnoreCase(personObj.getMosipAuthToken())
							|| response.equalsIgnoreCase("OTP Verified")) {

						return objMapper.writeValueAsString(personObj);

					} else {
						return "E1003";
					}
				} else {
					return "E1002";
				}

			}
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "E1001";
		}
		return null;
	}

}
