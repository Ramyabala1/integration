package com.app.patient.consent;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.patient.auth.AuthenticationService;
import com.app.patient.config.LoadConfig;
import com.app.patient.model.ResponseConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ConsentService {

	Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

	@Autowired
	private Environment env;

	@Autowired
	AuthenticationService auth;

	@Autowired
	ObjectMapper objMapper;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ConsentCache cache;

	@Autowired
	ConsentVerificationSerice serv;

	public ResponseConfig initConsent(String data) {

		JsonNode consentNode = null;
		String consentDetails = null;
		ResponseConfig res = new ResponseConfig();
		try {
			consentNode = objMapper.readValue(data, JsonNode.class);
			JsonNode patient = consentNode.get("person");
			JsonNode consentParameters = consentNode.get("consentParameters");
			String emanasId = patient.get("personId").asText();

			String hip = null;
			if (consentParameters.has("hip")) {
				hip = consentParameters.get("hip").get("estId").asText();
			}
			HttpHeaders headers = new HttpHeaders();
			ResponseConfig resAuth = auth.getJwtAuthentication();
			String uri = LoadConfig.getConfigValue("PINITCONSENT");
			if (resAuth != null) {
				headers.add("Authorization", "Bearer " + resAuth.getResponse());
				headers.add("X-Forwarded-Host", env.getProperty("x-forward-host"));

			}
			HttpEntity<String> entity = new HttpEntity<>(data, headers);
			ResponseEntity<ResponseConfig> response = restTemplate.exchange(uri, HttpMethod.POST, entity,
					ResponseConfig.class);
			res = response.getBody();
			if (res.getCode() == 200) {
				Consent c = new Consent();
				c.setEmanas(emanasId);
				c.setHealth_provider(hip);
				if (consentParameters.has("hiu")) {
					c.setIndividualId(consentParameters.get("hiu").get("individualId").asText());
					c.setIndividual_name(consentParameters.get("hiu").get("individualName").asText());
				}
				if (consentParameters.has("consentId")) {
					c.setConsentId(consentParameters.get("consentId").asText());
				}
				c.setTransactionId(res.getTransactionId());
				cache.saveUser(c);
				return res;
			}
		} catch (JsonProcessingException e) {

			res.setErrorcode("E8001");
			return res;
		}
		return res;
	}

	public ResponseConfig CreateConsent(String Data) {
		JsonNode hnode = null;
		ResponseConfig res = new ResponseConfig();
		try {
			hnode = objMapper.readValue(Data, JsonNode.class);
			String transactionId = hnode.get("transactionId").asText();
			String otp = hnode.get("otp").asText();
			HttpHeaders headers = new HttpHeaders();
			Consent c = cache.getConsent(transactionId);

			if (c.getTransactionId().equalsIgnoreCase(transactionId)) {
				ResponseConfig resAuth = auth.getJwtAuthentication();
				String uri = LoadConfig.getConfigValue("PCREATECONSENT");

				headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
				if (resAuth != null) {
					headers.add("Authorization", "Bearer " + resAuth.getResponse());
					headers.add("X-Forwarded-Host", env.getProperty("x-forward-host"));

				}
				HttpEntity<String> entity = new HttpEntity<>(Data, headers);
				ResponseEntity<ResponseConfig> response = restTemplate.exchange(uri, HttpMethod.POST, entity,
						ResponseConfig.class);
				res = response.getBody();
				if (res.getCode() == 200) {

					Consent consent = new Consent();
					consent.setConsentId(res.getResponse());
					serv.saveConsent(c);
					return res;
				}
			}

		} catch (JsonProcessingException e) {

			res.setErrorcode("E8001");
			return res;
		}
		return res;
	}

	public ResponseConfig getConsent(String data, String type) {
		ResponseConfig resp = new ResponseConfig();
		JsonNode hnode = null;
		try {

			JsonNode node = objMapper.readValue(data, JsonNode.class);
			hnode = objMapper.readValue(data, JsonNode.class);
			if (!hnode.has("patientId")) {
				resp.setErrors("Request does not match");
			}
			HttpHeaders headers = new HttpHeaders();
			String uri = null;
			if (type != null && type.equalsIgnoreCase("active")) {
				uri = LoadConfig.getConfigValue("PGETCONSENT");
			} else if (type != null && type.equalsIgnoreCase("inactive")) {
				uri = LoadConfig.getConfigValue("PGETINACTIVECONSENT");
			} else {
				uri = LoadConfig.getConfigValue("PGETCONSENT");
			}
			ResponseConfig resAuth = auth.getJwtAuthentication();
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
			if (resAuth != null && resAuth.getResponse() != null) {
				headers.add("Authorization", "Bearer " + resAuth.getResponse());
				headers.add("X-Forwarded-Host", env.getProperty("x-forward-host"));
				HttpEntity<String> entity = new HttpEntity<>(data, headers);
				ResponseEntity<ResponseConfig> response = restTemplate.exchange(uri, HttpMethod.POST, entity,
						ResponseConfig.class);
				ResponseConfig res = response.getBody();
				if (res != null) {

					return res;

				}
			} else {

				resp.setErrorcode("E8000");
				String message = LoadConfig.getConfigValue("E8000");
				if (message != null) {
					resp.setMessage(message.split("-")[0]);
					resp.setAction(message.split("-")[1]);
				}
			}

			resp.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			resp.setResponse(null);

		} catch (JsonProcessingException e) {
			resp.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			resp.setErrorcode("E8001");
			String message = LoadConfig.getConfigValue("E8001");
			if (message != null) {
				resp.setMessage(message.split("-")[0]);
				resp.setAction(message.split("-")[1]);
			}

		}

		return resp;
	}

	public ResponseConfig revokeConsent(String data, String action) {
		ResponseConfig resp = new ResponseConfig();
		JsonNode hnode = null;

		try {

			hnode = objMapper.readValue(data, JsonNode.class);

			HttpHeaders headers = new HttpHeaders();

			ResponseConfig resAuth = auth.getJwtAuthentication();
			String uri = null;
			if (action != null && action.equalsIgnoreCase("revoke")) {
				uri = LoadConfig.getConfigValue("PREVOKECONSENT");
			}
			if (action != null && action.equalsIgnoreCase("update")) {
				uri = LoadConfig.getConfigValue("PUPDATECONSENT");
			}

			headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
			if (resAuth != null) {
				headers.add("Authorization", "Bearer " + resAuth.getResponse());
				headers.add("X-Forwarded-Host", env.getProperty("x-forward-host"));
				HttpEntity<String> entity = new HttpEntity<>(data, headers);
				ResponseEntity<ResponseConfig> response = restTemplate.exchange(uri, HttpMethod.POST, entity,
						ResponseConfig.class);
				ResponseConfig res = response.getBody();
				if (res != null) {

					return res;

				}
			} else {

				String message = LoadConfig.getConfigValue("E8000");
				if (message != null) {
					resp.setMessage(message.split("-")[0]);
					resp.setAction(message.split("-")[1]);
				}
			}

			resp.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			resp.setResponse(null);

		} catch (JsonProcessingException e) {
			resp.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			resp.setErrorcode("E8001");
			String message = LoadConfig.getConfigValue("E8001");
			if (message != null) {
				resp.setMessage(message.split("-")[0]);
				resp.setAction(message.split("-")[1]);
			}

		}

		return resp;
	}
}
