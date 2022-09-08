package com.emanas.middleware.authorisedEntity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.consent.ConsentDao;
import com.emanas.middleware.consent.ConsentService;
import com.emanas.middleware.models.HiuUser;
import com.emanas.middleware.models.ResponseConfig;
import com.emanas.middleware.person.PersonAuthenticationService;
import com.emanas.middleware.utility.LoadConfig;
import com.emanas.middleware.utility.cache.redis.Cache;
import com.emanas.middleware.utility.security.SecurityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class AEService {

	@Autowired
	Cache cacheServ;

	@Autowired
	PersonAuthenticationService personAuthServ;

	@Autowired
	SecurityService secServ;

	@Autowired
	ObjectMapper objMapper;

	@Autowired
	ConsentDao consentDao;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	PersonAuthenticationService perServ;

	@Autowired
	ConsentService conServ;

	@Autowired
	AEDetailsDao aeDetailsDao;

	public String initAERegistration(String data) {
		String response = personAuthServ.initVerification(data);
		if (response != null && !response.contains("error")) {

			JsonNode node;
			try {
				node = objMapper.readValue(data, JsonNode.class);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "E1001";
			}
			JsonNode patient = node.get("authorizedEntity");

			JsonNode confirmDetails = node.get("confirmDetails");
			AEDetails aeDetails = new AEDetails();
			aeDetails.setConsentID(confirmDetails.get("consentId").asText());
			aeDetails.seteManasID(confirmDetails.get("patientId").asText());
			aeDetails.setPersonID(patient.get("aeId").asText());
			aeDetails.setTransID(response);

			aeDetailsDao.saveAEDetails(aeDetails);

		}

		return response;
	}

	public String aeRegistration(String data) throws JsonMappingException, JsonProcessingException {

		String response;
		response = personAuthServ.registerPatient(data);
		if (response != null && !response.contains("error")) {
			JsonNode node;
			node = objMapper.readValue(data, JsonNode.class);
			String transID = node.get("transactionId").asText();
			AEDetails aeDetails = aeDetailsDao.getAEDetails(transID);

			aeDetails.setTransID(transID);
			AEDetailsDao aeDetailsDao = new AEDetailsDaoImpl();
			// aeDetailsDao.saveAEDetails(aeDetails);
			String res = null;

			HiuUser hiu = secServ.getHIU();
			HttpHeaders headers = secServ.checkSecurity();
			String consentValidate = consentDao.validateConsentForAEMap(aeDetails, hiu.getMheAccessToken());

			if (consentValidate != null && consentValidate.equalsIgnoreCase("Valid")) {

				String req = "{\"patientID\":\"" + aeDetails.geteManasID() + "\",\"aeID\":\"" + aeDetails.getPersonID()
						+ "\"}";

				HttpEntity<String> entity = new HttpEntity<String>(req, headers);
				String url = LoadConfig.getConfigValue("UPDATEPATIENTAUTHENTITY");
				res = restTemplate.postForObject(url, entity, String.class);
				return res;

			} else {
				return consentValidate;
			}
		}
		return response;

	}

	public String getAEPatientMap(String data) throws JsonMappingException, JsonProcessingException {

		HttpHeaders headers = secServ.checkSecurity();

		HttpEntity<String> entity = new HttpEntity<String>(data, headers);

		String response = restTemplate.postForObject(LoadConfig.getConfigValue("GETPATIENTAUTHENTITY"), entity,
				String.class);

		return response;

	}

	public ResponseConfig createAE(String data) throws JsonMappingException, JsonProcessingException {

		HiuUser hiu = secServ.getHIU();
		ObjectMapper mapper = new ObjectMapper();
		ResponseConfig resPatient = new ResponseConfig();
		JsonNode reqst = mapper.readValue(data, JsonNode.class);
		JsonNode node = reqst.get("demographics");
		((ObjectNode) node).put("userUuid", hiu.getUuid());
		((ObjectNode) node).put("userToken", hiu.getMheSessionToken());
		((ObjectNode) node).put("orgUuid", hiu.getOrguuid());

		if (node.has("emergencyContact")) {
			JsonNode emergencyContact = node.get("emergencyContact");
			if (emergencyContact.has("contactName")
					&& emergencyContact.get("contactName").asText().equalsIgnoreCase("")) {
				resPatient.setCode(441);
			}
		}

		String req = mapper.writeValueAsString(node);

		HttpHeaders headers = secServ.checkSecurity();

		HttpEntity<String> entity = new HttpEntity<String>(req, headers);

		resPatient = restTemplate.postForObject(LoadConfig.getConfigValue("STOREAE"), entity, ResponseConfig.class);

		return resPatient;

	}

}
