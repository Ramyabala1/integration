package com.emanas.middleware.consent;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.authorisedEntity.AEDetails;
import com.emanas.middleware.utility.LoadConfig;
import com.emanas.middleware.utility.cache.redis.Cache;
import com.emanas.middleware.utility.security.SecurityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class ConsentDaoImpl implements ConsentDao {

	@Autowired
	Cache cacheServ;

	@Autowired
	ObjectMapper objStpper;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	SecurityService secServ;

	private final String hashReference = "CONSENT";

	@Override
	public void saveConsent(Consent consent) {
		cacheServ.saveCache(hashReference, consent.getConsentrequestID(), consent);
		cacheServ.setExpire(hashReference, 180, TimeUnit.SECONDS);

	}

	@Override
	public Consent getConsent(String consentReqID) throws JsonMappingException, JsonProcessingException {
		Object obj = cacheServ.getCache(hashReference, consentReqID);
		Consent consentObj = null;
		ObjectMapper mapper = new ObjectMapper();
		if (obj != null) {

			consentObj = mapper.readValue(mapper.writeValueAsString(obj), Consent.class);
		}
		return consentObj;
	}

	@Override
	public void deleteConsent(String consentID) {
		cacheServ.clearCache(hashReference, consentID);

	}

	@Override
	public boolean createConsentrecord(Consent objData) {

		if (objData != null) {
			HttpHeaders headers = secServ.checkSecurity();
			String ObjStr = null;
			try {
				ObjStr = objStpper.writeValueAsString(objData);

				HttpEntity<String> entity = new HttpEntity<String>(ObjStr, headers);

				Boolean isConsentAdded = restTemplate.postForObject(LoadConfig.getConfigValue("INITCONSENT"), entity,
						Boolean.class);

				return isConsentAdded;
			} catch (JsonProcessingException e) {

				e.printStackTrace();
			}
		}
		return false;
	}

	@Override
	public String verifyandCreateConsentObject(Consent objData) {

		if (objData != null) {
			HttpHeaders headers = secServ.checkSecurity();

			String ObjStr = null;
			try {
				ObjStr = objStpper.writeValueAsString(objData);

				HttpEntity<String> entity = new HttpEntity<String>(ObjStr, headers);

				String verifiedConsentResponse = restTemplate.postForObject(LoadConfig.getConfigValue("VERIFYCONSENT"),
						entity, String.class);

				if (!verifiedConsentResponse.contains("Error")) {
					return verifiedConsentResponse;
				} else {
					return "E1004";
				}

			} catch (JsonProcessingException e) {

				e.printStackTrace();
				return "E5000";
			}
		}

		return "E5000";
	}

	@Override
	public String getConsentForPatient(Consent objData, String status) {

		if (objData != null) {
			HttpHeaders headers = secServ.checkSecurity();
			// headers.add( "Authorization", "Bearer "+hiu.getMheAccessToken());
			String ObjStr = null;
			try {
				ObjStr = objStpper.writeValueAsString(objData);

				HttpEntity<String> entity = new HttpEntity<String>(ObjStr, headers);
				String verifiedConsentResponse = null;
				if (status.equalsIgnoreCase("active")) {
					verifiedConsentResponse = restTemplate.getForObject(
							LoadConfig.getConfigValue("GETACTIVECONSENT") + objData.geteManasId(), String.class);
				} else if (status.equalsIgnoreCase("inactive")) {
					verifiedConsentResponse = restTemplate.getForObject(
							LoadConfig.getConfigValue("GETINACTIVECONSENT") + objData.geteManasId(), String.class);
				}
				if (!verifiedConsentResponse.contains("Error")) {
					return verifiedConsentResponse;
				} else {
					return "E1011";
				}

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		return null;
	}

	@Override
	public String getInActiveConsentForPatient(Consent objData) {
		if (objData != null) {
			HttpHeaders headers = secServ.checkSecurity();
			String ObjStr = null;
			try {
				ObjStr = objStpper.writeValueAsString(objData);
				HttpEntity<String> entity = new HttpEntity<String>(ObjStr, headers);

				String verifiedConsentResponse = restTemplate.getForObject(
						LoadConfig.getConfigValue("GETINACTIVECONSENT") + objData.geteManasId(), String.class);

				if (!verifiedConsentResponse.contains("Error")) {
					return verifiedConsentResponse;
				} else {
					return "E1011";
				}

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return null;
			}
		}

		return null;
	}

	@Override
	public Boolean revokeConsent(Consent consentObj) {

		if (consentObj != null) {
			HttpHeaders headers = secServ.checkSecurity();

			String ObjStr = null;
			try {
				ObjStr = objStpper.writeValueAsString(consentObj.getConsentID());

				HttpEntity<String> entity = new HttpEntity<String>(consentObj.getConsentID(), headers);

				Boolean revokedConsentResponse = restTemplate.postForObject(LoadConfig.getConfigValue("REVOKECONSENT"),
						entity, Boolean.class);

				if (revokedConsentResponse) {

					return revokedConsentResponse;
				}

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}

		return false;
	}

	@Override
	public Boolean updateConsent(Consent consentObj) {

		if (consentObj != null) {
			HttpHeaders headers = secServ.checkSecurity();

			String ObjStr = null;
			try {
				consentObj.setConsentrequestID(consentObj.getConsentID());

				JsonNode consentNode1 = new ObjectMapper().readValue(consentObj.getRequest(), JsonNode.class);
				JsonNode reqNode;
				if (consentNode1.has("patient") && consentNode1.get("patient").has("serviceParameters")) {
					ObjectNode node = (ObjectNode) new ObjectMapper().readTree(consentObj.getRequest());
					((ObjectNode) node.get("patient")).remove("serviceParameters");
					consentNode1 = new ObjectMapper().readTree(node.toString());
				}

				if (consentNode1.has("person") && consentNode1.get("person").has("serviceParameters")) {
					ObjectNode node = (ObjectNode) new ObjectMapper().readTree(consentObj.getRequest());
					((ObjectNode) node.get("person")).remove("serviceParameters");
					consentNode1 = new ObjectMapper().readTree(node.toString());
				}

				ObjStr = objStpper.writeValueAsString(consentObj);
				HttpEntity<String> entity = new HttpEntity<String>(ObjStr, headers);

				Boolean updatedConsentResponse = restTemplate.postForObject(LoadConfig.getConfigValue("UPDATECONSENT"),
						entity, Boolean.class);

				if (updatedConsentResponse) {
					return updatedConsentResponse;
				}

			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}

		return false;
	}

	@Override
	public HashMap<String, String> validateConsent(String ehrData, String token) {
		HashMap<String, String> map = new HashMap<>();

		ObjectMapper objStringMapper = new ObjectMapper();
		List<String> recordType = new ArrayList<String>();
		List<String> compList = new ArrayList<String>();
		JsonNode node = null;
		try {
			node = objStringMapper.readValue(ehrData, JsonNode.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block

			map.put("error", "E1001");

		}
		String patientId = node.get("patientId").asText();
		String consentID = node.get("consentId").asText();
		String role = node.get("hiu").get("individualRole").asText();
		String source = null;
		if (node.has("source")) {
			source = node.get("source").asText();
		}

		JsonNode hitypesRequest = null;

		String operation = null;
		if (node.has("operation")) {
			operation = node.get("operation").asText();
		}
		JsonNode hresource = null;
		JsonNode compositions = null;

		if (operation.equalsIgnoreCase("VIEW") || operation.equalsIgnoreCase("CREATE")) {

			if (node.has("hresource")) {
				hresource = node.get("hresource");

				if (hresource.has("resourceType") && hresource.get("resourceType").asText().equalsIgnoreCase("name")) {

					compositions = hresource.get("compositions");
					if (compositions.isArray()) {
						for (final JsonNode objNode : compositions) {
							compList.add(objNode.get("compositionId").asText());


						}
					}

					if (hresource.has("episodeId") && compositions != null) {
						map.put("episodeId", hresource.get("episodeId").asText());
						map.put("compositions", compositions.toString());
					}

				}
			}
			if (node.has("purpose")) {
				hitypesRequest = node.get("purpose");
			}

			String from = "";
			String to = "";

			if (role == null) {
				map.put("error", "E1008");

			}

			for (final JsonNode reqType : hitypesRequest) {
				recordType.add(reqType.asText());
			}

			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
			format.setTimeZone(TimeZone.getTimeZone("UTC"));

			List<String> purposeTypeList = new ArrayList<String>();
			ObjectMapper mapper = new ObjectMapper();
			ResponseEntity<String> getConsent = restTemplate
					.getForEntity(LoadConfig.getConfigValue("GETCONSENT") + consentID, String.class);

			if (getConsent.getStatusCode().equals(HttpStatus.OK)) {
				JsonNode artifact = null;
				try {
					artifact = mapper.readValue(getConsent.getBody(), JsonNode.class);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					map.put("error", "E1001");

				}

				JsonNode consentData = artifact.get("consentParameters");
				JsonNode purposeConsent = consentData.get("purpose");
				String permission = null;

				permission = consentData.get("permission").asText();

				JsonNode validity = consentData.get("validity");
				String validityEndDate = validity.get("validityEndDate").asText();

				String patientConsent = null;

				if (artifact.has("patient")) {
					patientConsent = artifact.get("patient").get("patientId").asText();
				}
				if (artifact.has("person")) {
					JsonNode person = artifact.get("person");

					if (person.has("personType") &&

							!person.get("personType").asText().equalsIgnoreCase("AUTHORIZEDENTITY")) {
						patientConsent = artifact.get("person").get("personId").asText();
					} else if (person.has("personType")
							&& person.get("personType").asText().equalsIgnoreCase("AUTHORIZEDENTITY")) {
						patientConsent = consentData.get("patientId").asText();
					}
				}

				String hip = consentData.get("hip").get("estId").asText();
				Date valid = null;
				Date today = new Date();

				if (consentData.has("hiu") && consentData.get("hiu").has("individualName")) {
					map.put("individualName", consentData.get("hiu").get("individualName").asText());
				}

				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

				Date todayWithoutTime = null;
				Date validWithoutTime = null;
				try {
					valid = format.parse(validityEndDate);
					todayWithoutTime = formatter.parse(formatter.format(today));
					validWithoutTime = formatter.parse(formatter.format(valid));
				} catch (ParseException e) {
					// TODO Auto-generated catch block

					map.put("error", "E1001");

				}

				Boolean matchPurpose = true;

				for (final JsonNode purposeType : purposeConsent) {
					purposeTypeList.add(purposeType.asText());
				}

				for (final String purpose : recordType) {
					if (!purposeTypeList.contains(purpose)) {
						matchPurpose = false;
					}
				}

				if (!matchPurpose) {

					map.put("error", "E1012");

				}

				if (validWithoutTime != null && validWithoutTime.compareTo(todayWithoutTime) >= 0) {
					if (role != null && permission != null && patientId.equalsIgnoreCase(patientConsent)) {

						if (!recordType.contains("ADVANCEDIRECTIVE")) {
							JsonNode consentDateRange = consentData.get("hresource");
							String consentfrom = "";
							String consentto = "";
							List<String> conCompList = new ArrayList<String>();

							if (consentDateRange.has("resourceType")
									&& consentDateRange.get("resourceType").asText().equalsIgnoreCase("name")
									&& !map.containsKey("episodeId") && !map.containsKey("compositions"))

							{

								if (operation.equalsIgnoreCase("VIEW")) {

									if (consentDateRange.has("episodeId")) {
										if (consentDateRange.has("compositions")) {
											compositions = consentDateRange.get("compositions");
											if (compositions.isArray()) {
												for (final JsonNode objNode : compositions) {

													map.put("episodeId", consentDateRange.get("episodeId").asText());
													map.put("compositions", compositions.toString());
												}
											}
										} else {

											SimpleDateFormat dateFormat = new SimpleDateFormat(
													"yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
											format.setTimeZone(TimeZone.getTimeZone("UTC"));
											Date date = new Date();

											// Convert Date to Calendar
											Calendar cTo = Calendar.getInstance();
											cTo.setTime(date);
											cTo.add(Calendar.YEAR, 2);

											Date dateTo = cTo.getTime();

											Calendar cFrom = Calendar.getInstance();
											cFrom.setTime(date);
											cFrom.add(Calendar.YEAR, -2);
											Date dateFrom = cFrom.getTime();

											map.put("to", dateFormat.format(dateTo));
											map.put("from", dateFormat.format(dateFrom));
											map.put("hip", hip);
											if (recordType.size() > 0) {
												String typeString = String.join(",", recordType);
												map.put("recordType", typeString);
											}

										}
									} else if (consentDateRange.has("compositionId")) {
										compList.add(consentDateRange.get("compositionId").asText());
										map.put("episodeId", consentDateRange.get("episodeId").asText());
										map.put("compositions", compList.toString());
									}

								}

							} else if (consentDateRange != null && consentDateRange.has("resourceType")
									&& consentDateRange.get("resourceType").asText().equalsIgnoreCase("period")) {
								consentfrom = consentDateRange.get("from").asText();
								consentto = consentDateRange.get("to").asText();
								from = consentfrom;
								to = consentto;
								map.put("to", to);
								map.put("from", from);
								map.put("hip", hip);
								if (recordType.size() > 0) {
									String typeString = String.join(",", recordType);
									map.put("recordType", typeString);
								}

							}
							if (!consentfrom.equalsIgnoreCase("") && !consentto.equalsIgnoreCase("")) {
								try {
									format.parse(consentfrom);
									format.parse(consentto);
								} catch (ParseException e) {
									map.put("error", "E1001");

								}

							}
						}

						map.put("result", "Valid");

					} else {
						map.put("error", "E1010");

					}

				} else {
					map.put("error", "E1012");

				}

			} else {
				map.put("error", "E1014");

			}
		} else {

			map.put("error", "E1002");

		}
		return map;

	}

	@Override
	public String validateConsentForAEMap(AEDetails aeDetails, String token) {

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));

		ObjectMapper mapper = new ObjectMapper();
		ResponseEntity<String> getConsent = restTemplate
				.getForEntity(LoadConfig.getConfigValue("GETCONSENT") + aeDetails.getConsentID(), String.class);

		if (getConsent != null) {
			JsonNode artifact = null;
			try {
				artifact = mapper.readValue(getConsent.getBody(), JsonNode.class);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block

				return "E1001";

			}

			JsonNode aeData = artifact.get("aeDetails");
			String aeId = aeData.get("aeId").asText();

			JsonNode consentData = artifact.get("consentParameters");

			JsonNode validity = consentData.get("validity");
			String validityEnddate = validity.get("validityEndDate").asText();
			String patientConsent = artifact.get("patient").get("patientId").asText();

			Date valid = null;
			Date today = new Date();

			DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

			Date todayWithoutTime = null;
			Date validWithoutTime = null;
			try {
				valid = format.parse(validityEnddate);
				todayWithoutTime = formatter.parse(formatter.format(today));
				validWithoutTime = formatter.parse(formatter.format(valid));
			} catch (ParseException e) {
				// TODO Auto-generated catch block

				return "E1001";

			}

			if (validWithoutTime != null && validWithoutTime.compareTo(todayWithoutTime) >= 0) {

				if (aeDetails.geteManasID().equalsIgnoreCase(patientConsent)
						&& aeId.equalsIgnoreCase(aeDetails.getPersonID())) {
					return "Valid";
				}
			} else {
				return "E1014";
			}

		} else {
			return "E1002";

		}

		return null;

	}

}
