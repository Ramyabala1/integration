package com.emanas.middleware.consent;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.emanas.middleware.Demog.Factory;
import com.emanas.middleware.auth.AuthFactory;
import com.emanas.middleware.auth.AuthService;
import com.emanas.middleware.models.AuthorisedEntity;
import com.emanas.middleware.models.Patient;
import com.emanas.middleware.person.PersonValidationService;
import com.emanas.middleware.redis.model.SendOTP;
import com.emanas.middleware.utility.cache.redis.Cache;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class ConsentService {

	@Autowired
	PersonValidationService personServ;

	@Autowired
	Cache cacheServ;

	@Autowired
	Factory authRegistry;

	@Autowired
	ObjectMapper objMapper;

	@Autowired
	AuthService authServ;

	@Autowired
	ConsentDao consentDao;

	@Autowired
	AuthFactory authfac;

	public String initConsent(String consentData) throws ParseException
	{
		 SendOTP sendOTP = new SendOTP();
		 JsonNode consentNode = null;
		 String eManasId = null;
		 String service = null;
		 JsonNode patientObj= null;
			JsonNode serviceParam = null;
			String personType = null;
			String patientDetails = null;
				try {
					consentNode = objMapper.readValue(consentData, JsonNode.class);
					JsonNode consentObj =  consentNode.get("consentParameters");

					if(consentNode.has("patient")) {
					patientObj =  consentNode.get("patient");
					eManasId = patientObj.get("patientId").asText();
					 service = patientObj.get("authService").asText();
					 personType = "PATIENT";
					}


					if(eManasId!=null || consentNode.has("person"))
					{
						if(eManasId==null ) {

						patientObj  = consentNode.get("person");

						if(patientObj.has("personId") && patientObj.has("personType")) {
						eManasId = patientObj.get("personId").asText();
						personType = patientObj.get("personType").asText().toUpperCase();
						service = patientObj.get("authService").asText();
						}
						}


						personServ = authRegistry.getPersonService(personType);
						patientDetails = personServ.fetchPersonRecord(eManasId);
						AuthorisedEntity auth = new AuthorisedEntity();
						Patient patObj = null;
						String phone = null;
						if(patientDetails!=null && !patientDetails.equalsIgnoreCase("Patient Not Found"))
						{

							if(personType.equalsIgnoreCase("AUTHORIZEDENTITY")) {

							    auth = objMapper.readValue(patientDetails,AuthorisedEntity.class);
							    phone = auth.getPhoneNumber();


							}else
							{
								patObj = objMapper.readValue(patientDetails,Patient.class);
								phone= patObj.getPhoneNumber();
							}

							if (consentNode.has("aeDetails")) {
								String aeDetails = null;
								JsonNode aeDetNode = consentNode.get("aeDetails");

								String aeId = aeDetNode.get("aeId").asText();

								personServ = authRegistry.getPersonService("AUTHORIZEDENTITY");
								aeDetails = personServ.fetchPersonRecord(aeId);
								AuthorisedEntity authEntity = objMapper.readValue(aeDetails,
										AuthorisedEntity.class);

								if (patObj != null && authEntity != null && patObj.getIdproof().length > 0
										&& authEntity.getIdNumber() != null) {
									for (int i = 0; i < patObj.getIdproof().length; i++) {
										if (patObj.getIdproof()[i].getType().equalsIgnoreCase("MOSIP ID")) {
											if (authEntity.getIdNumber()
													.equalsIgnoreCase(patObj.getIdproof()[i].getIdNumber())) {

												return "E1489";
											}

										}
									}
								}

							}

							 if(patObj!=null || auth!=null)
								{


									if (service.equalsIgnoreCase("MOSIP")) {
										authServ = authRegistry.getAuthService("MOS");
										if (patientObj.has("serviceParameters")) {
										   serviceParam = patientObj.get("serviceParameters");
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
										sendOTP.setContactVal(phone);
										sendOTP.setContactType("mobile");
										sendOTP.setPurpose("CONSENT");
									}

									String response = authServ.sendOTPRequest(sendOTP);
									if(response!=null && !response.contains("error"))
									{
										Consent objData  = new Consent();
										String tokenID = UUID.randomUUID().toString();

										if(consentObj.has("consentId"))
										{
											objData.setConsentID(consentObj.get("consentId").asText());
											objData.setConsentrequestID(tokenID);
											objData.setRequest(objMapper.writeValueAsString(consentNode));


										}else
										{

											objData.setConsentrequestID(tokenID);
											objData.setRequest(objMapper.writeValueAsString(consentNode));
											objData.setDateCreated(new Date());
											objData.setRefId(RandomStringUtils.randomAlphanumeric(4).toUpperCase());
											objData.setStatus("Generated");

										}
												objData.setService(service);
												objData.seteManasId(eManasId);
												objData.setAuthtransactionId(response);
												objData.setAuthorisationType("SMSOTP");
												if(service.equalsIgnoreCase("MOSIP"))
													{

													    if(personType.equalsIgnoreCase("AUTHORIZEDENTITY")) {
													    	objData.setMosipAuthToken(auth.getIdNumber());
															objData.setMosipID(serviceParam.get("individualId").asText());
													    }else
													    {
														for(int i=0; i<patObj.getIdproof().length; i++)
														{
															if(patObj.getIdproof()[i].getType().equalsIgnoreCase("MOSIP ID"))
																	{
																		objData.setMosipAuthToken(patObj.getIdproof()[i].getIdNumber());
																		objData.setMosipID(serviceParam.get("individualId").asText());
																	}
														}
													    }

													}else
													{
														objData.setPhoneNumber(phone);
													}


											consentDao.saveConsent(objData);
											return tokenID;



									}else if(response!=null && response.contains("error")) {
										return response;
									}

										else
									{
										return "E1020";
									}

								}else
								{
									return "E1001";
								}
						}else
						{
							return "E1000";
						}
					}else
					{
						return "E1000";
					}
				} catch (JsonProcessingException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				return null;

	}



	public String createConsent(String authConsent) {
	try {
		JsonNode consentNode = null;

			consentNode = objMapper.readValue(authConsent, JsonNode.class);
			SendOTP sendOtp = new SendOTP();

		String transactionId =  consentNode.get("transactionId").asText();
		String otp =  consentNode.get("otp").asText();
		Consent consentObj = consentDao.getConsent(transactionId);
		if(consentObj!=null && consentObj.getConsentrequestID().equalsIgnoreCase(transactionId))
		{

			if (consentObj.getService().equalsIgnoreCase("MOSIP")) {
				authServ = authRegistry.getAuthService("MOS");
				sendOtp.setContactVal(consentObj.getMosipID());
			}else
			{
				authServ = authRegistry.getAuthService("EMANAS");
				sendOtp.setContactType("mobile");
				sendOtp.setContactVal(consentObj.getPhoneNumber());
			}

			sendOtp.setOtp(otp);
			sendOtp.setPurpose("CONSENT");
			sendOtp.setTransactionID(consentObj.getAuthtransactionId());

			String response = authServ.verifyAuthRequest(sendOtp);

			if(response!=null && response.contains("error"))
			{
				return response;
			}else if(response!=null)
			{
				if(response.equalsIgnoreCase(consentObj.getMosipAuthToken()) || response.equalsIgnoreCase("OTP Verified"))
					{
						consentObj.setOtp(otp);

						JsonNode consentNode1 = objMapper.readValue(consentObj.getRequest(), JsonNode.class);
						JsonNode reqNode;
						if(consentNode1.has("patient") && consentNode1.get("patient").has("serviceParameters") &&
								consentNode1.get("patient").has("authService"))
						{
							ObjectNode node = (ObjectNode) new ObjectMapper().readTree(consentObj.getRequest());
							((ObjectNode) node.get("patient")).remove("serviceParameters");

							consentNode1 = new ObjectMapper().readTree(node.toString());
						}

						if(consentNode1.has("person") && consentNode1.get("person").has("serviceParameters") &&
								consentNode1.get("person").has("authService"))
						{
							ObjectNode node = (ObjectNode) new ObjectMapper().readTree(consentObj.getRequest());
							((ObjectNode) node.get("person")).remove("serviceParameters");

							consentNode1 = new ObjectMapper().readTree(node.toString());
						}

						String req = new ObjectMapper().writeValueAsString(consentNode1);

						consentObj.setRequest(req);

						String verifiedConsentID = consentDao.verifyandCreateConsentObject(consentObj);
						if(verifiedConsentID!=null && verifiedConsentID.length()!=5)
						{
							return verifiedConsentID;
						}else
						{
							return "E1255";
						}
					}
				else {
					return "E1003";
				}
			}
		}
		else
		{
			return "E1002";
		}


		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "E5000";
		}
		return "E5000";
	}

	public HashMap<String,String> findConsent(String reqData,String token)
	{

		HashMap<String,String> validConsent = consentDao.validateConsent(reqData,token);
		return validConsent;
	}



	public String getConsent(String reqData) throws JsonMappingException, JsonProcessingException {

			JsonNode reqNode = null;

			reqNode = objMapper.readValue(reqData, JsonNode.class);

			String isactiveTag = reqNode.get("status").asText();

			String patientId =  reqNode.get("patientId").asText();

			Consent c = new Consent();
			c.seteManasId(patientId);
			String consentData =consentDao.getConsentForPatient(c,isactiveTag);
			return consentData;
}


	public String getInActiveConsent(String reqData) throws JsonMappingException, JsonProcessingException {

		JsonNode reqNode = null;

		reqNode = objMapper.readValue(reqData, JsonNode.class);
		String patientId =  reqNode.get("patientId").asText();
		Consent c = new Consent();
		c.seteManasId(patientId);
		String consentData =consentDao.getInActiveConsentForPatient(c);
		return consentData;
}



	public String updateConsent(String reqData) {

		try {

			JsonNode consentNode = null;
			consentNode = objMapper.readValue(reqData, JsonNode.class);
			SendOTP sendOtp = new SendOTP();

			String transactionId =  consentNode.get("transactionId").asText();
			String otp =  consentNode.get("otp").asText();
			Consent consentObj = consentDao.getConsent(transactionId);
			if(consentObj!=null && consentObj.getConsentrequestID().equalsIgnoreCase(transactionId))
			{

				if (consentObj.getService().equalsIgnoreCase("MOSIP")) {
					authServ = authRegistry.getAuthService("MOS");
					sendOtp.setContactVal(consentObj.getMosipID());
				}else
				{
					authServ = authRegistry.getAuthService("EMANAS");
					sendOtp.setContactType("mobile");
					sendOtp.setContactVal(consentObj.getPhoneNumber());
				}

				sendOtp.setOtp(otp);
				sendOtp.setPurpose("CONSENT");
				sendOtp.setTransactionID(consentObj.getAuthtransactionId());

				String response = authServ.verifyAuthRequest(sendOtp);

				if(response!=null && response.contains("error"))
				{
					return response;
				}else if(response!=null)
				{
					if(response.equalsIgnoreCase(consentObj.getMosipAuthToken()) || response.equalsIgnoreCase("OTP Verified"))
						{
							consentObj.setOtp(otp);

							JsonNode consentNode1 = objMapper.readValue(consentObj.getRequest(), JsonNode.class);

							if(consentNode1.has("patient") && consentNode1.get("patient").has("serviceParameters"))
							{
								ObjectNode node = (ObjectNode) new ObjectMapper().readTree(consentObj.getRequest());
								((ObjectNode) node.get("patient")).remove("serviceParameters");
								((ObjectNode) node.get("consentParameters")).remove("consentId");
								consentNode1 = new ObjectMapper().readTree(node.toString());
							}

							if(consentNode1.has("person") && consentNode1.get("person").has("serviceParameters"))
							{
								ObjectNode node = (ObjectNode) new ObjectMapper().readTree(consentObj.getRequest());
								((ObjectNode) node.get("person")).remove("serviceParameters");
								((ObjectNode) node.get("consentParameters")).remove("consentId");
								consentNode1 = new ObjectMapper().readTree(node.toString());
							}

							String req = new ObjectMapper().writeValueAsString(consentNode1);

							consentObj.setRequest(req);

							Boolean verifiedConsentID = consentDao.updateConsent(consentObj);
							if(verifiedConsentID)
							{
								return consentObj.getConsentID();
							}else
							{
								return "E1006";
							}
						}
					else {
						return "E1003";
					}
				}else if(consentObj.getService().equalsIgnoreCase("EMANAS"))
				{

				}
			}
			else
			{
				return "E1002";
			}


			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;

		}





	public String revokeConsent(String reqData) {
		try {
			JsonNode consentNode = null;

				consentNode = objMapper.readValue(reqData, JsonNode.class);
				SendOTP sendOtp = new SendOTP();

			String transactionId =  consentNode.get("transactionId").asText();
			String otp =  consentNode.get("otp").asText();
			Consent consentObj = consentDao.getConsent(transactionId);
			if(consentObj!=null && consentObj.getConsentrequestID().equalsIgnoreCase(transactionId))
			{

				if (consentObj.getService().equalsIgnoreCase("MOSIP")) {
					authServ = authRegistry.getAuthService("MOS");
					sendOtp.setContactVal(consentObj.getMosipID());
				}else
				{
					authServ = authRegistry.getAuthService("EMANAS");
					sendOtp.setContactType("mobile");
					sendOtp.setContactVal(consentObj.getPhoneNumber());
				}

				sendOtp.setOtp(otp);
				sendOtp.setPurpose("CONSENT");
				sendOtp.setTransactionID(consentObj.getAuthtransactionId());

				String response = authServ.verifyAuthRequest(sendOtp);

				if(response!=null && response.contains("error"))
				{
					return response;
				}else if(response!=null)
				{
					if(response.equalsIgnoreCase(consentObj.getMosipAuthToken()) || response.equalsIgnoreCase("OTP Verified"))
						{
							consentObj.setOtp(otp);

							JsonNode consentNode1 = objMapper.readValue(consentObj.getRequest(), JsonNode.class);

							if(consentNode1.has("patient") && consentNode1.get("patient").has("serviceParameters"))
							{
								ObjectNode node = (ObjectNode) new ObjectMapper().readTree(consentObj.getRequest());
								((ObjectNode) node.get("patient")).remove("serviceParameters");
								consentNode1 = new ObjectMapper().readTree(node.toString());
							}

							if(consentNode1.has("person") && consentNode1.get("person").has("serviceParameters"))
							{
								ObjectNode node = (ObjectNode) new ObjectMapper().readTree(consentObj.getRequest());
								((ObjectNode) node.get("person")).remove("serviceParameters");
								consentNode1 = new ObjectMapper().readTree(node.toString());
							}


							String req = new ObjectMapper().writeValueAsString(consentNode1);

							consentObj.setRequest(req);

							Boolean verifiedConsentID = consentDao.revokeConsent(consentObj);
							if(verifiedConsentID)
							{
								return consentObj.getConsentID();
							}else
							{
								return "E1006";
							}
						}
					else {
						return "E1003";
					}
				}else if(consentObj.getService().equalsIgnoreCase("EMANAS"))
				{

				}
			}
			else
			{
				return "E1002";
			}


			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}


}
