package com.app.patient.person;

import java.util.Date;

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
import com.app.patient.user.Person;
import com.app.patient.user.UserCacheService;
import com.app.patient.user.UserRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class PersonService {

	Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

	@Autowired
	private Environment env;

	@Autowired
	AuthenticationService auth;
	@Autowired
	ObjectMapper objMapper;

	@Autowired
	UserCacheService usrCacheSrv;
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	UserRepository usrRepository;

	@Autowired
	PersonVerificationService pvserv;

	@Autowired
	PersonValidationFactory pvfac;

	public ResponseConfig initRegistration(String patData) {

		JsonNode patientNode = null;
		String patientDetails = null;
		ResponseConfig responses = new ResponseConfig();
		try {
			patientNode = objMapper.readValue(patData, JsonNode.class);
			JsonNode patient = patientNode.get("person");
			String login = patientNode.get("login").asText();
			String password = patientNode.get("password").asText();
			String eManasId = patient.get("patientId").asText();
			String service = patient.get("authService").asText();
			String type = patient.get("type").asText();
			String aeID = null;
			if (type.equalsIgnoreCase("authorized_entity")) {

				if (patient.has("aeId")) {
					aeID = patient.get("aeId").asText();
				}

				pvserv = pvfac.getPersonVerificationService(type);
				if (pvserv.checkIfUserExists(aeID, login, password)) {
					
					responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
					responses.setErrorcode("E8044");
					String message = LoadConfig.getConfigValue("E8045");
					if (message != null) {
						responses.setMessage(message.split("-")[0]);
						responses.setAction(message.split("-")[1]);
					}
					
					return responses; 
				} else {
					if (!checkAEMap(aeID, eManasId)) {
						
						responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
						responses.setErrorcode("E8045");
						String message = LoadConfig.getConfigValue("E8045");
						if (message != null) {
							responses.setMessage(message.split("-")[0]);
							responses.setAction(message.split("-")[1]);
						}
						
						return responses; 
					}
				}

			} else if (type.equalsIgnoreCase("patient")) {
				pvserv = pvfac.getPersonVerificationService(type);

				System.out.println("");
				if (pvserv.checkIfUserExists(eManasId, login, password)) {
					
					responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
					responses.setErrorcode("E8044");
					String message = LoadConfig.getConfigValue("E8044");
					if (message != null) {
						responses.setMessage(message.split("-")[0]);
						responses.setAction(message.split("-")[1]);
					}
					
					return responses; 
				}
			}

			HttpHeaders headers = new HttpHeaders();

			ObjectNode node = (ObjectNode) patientNode;
			node.remove("login");
			node.remove("password");
			ResponseConfig resAuth = auth.getJwtAuthentication();
			String uri = LoadConfig.getConfigValue("PINITVERIFICATION");

			System.out.println("patientNode"+patientNode);
			System.out.println("patData"+patData);
			
			headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
			if (resAuth != null) {
				headers.add("Authorization", "Bearer " + resAuth.getResponse());
				headers.add("X-Forwarded-Host", env.getProperty("x-forward-host"));
			}
			HttpEntity<String> entity = new HttpEntity<>(patData, headers);
			ResponseEntity<ResponseConfig> response = restTemplate.exchange(uri, HttpMethod.POST, entity,
					ResponseConfig.class);
			
			System.out.println("response"+response);
			
			ResponseConfig res = response.getBody();
		
			System.out.println("resres" + res);
			if (res.getCode() == 200) {

				Person usr = new Person();

				if (type.equalsIgnoreCase("authorized_entity")) {
					usr.setaeId(aeID);
					usr.setType(type);
				}
				usr.setEmanas(eManasId);
				usr.setLogin(login);
				usr.setPassword(password);
				usr.setCreationTime(new Date());
				usr.setTransctionId(res.getTransactionId());
				usrCacheSrv.saveUser(usr);
				
				
				return res;

			} else if (res.getCode() == 400) {
				return res;
			}

		} catch (JsonProcessingException e) {
			
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode("E8001");
			String message = LoadConfig.getConfigValue("E8001");
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}
			
			return responses; 
		}

		
		return null;
	}

	private Boolean checkAEMap(String aeID, String eManasId) {

		JsonNode reqNode = null;

		HttpHeaders headers = new HttpHeaders();
		ResponseConfig resAuth = auth.getJwtAuthentication();
	
		String uri = LoadConfig.getConfigValue("PAEMAP");
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		if (resAuth != null) {
			headers.add("Authorization", "Bearer " + resAuth.getResponse());
			headers.add("X-Forwarded-Host", env.getProperty("x-forward-host"));
		} else {
			return false;
		}
		String req = "{\"patientID\":\"" + eManasId + "\",\"aeID\":\"" + aeID + "\"}";

		HttpEntity<String> entity = new HttpEntity<>(req, headers);
		ResponseEntity<ResponseConfig> response = restTemplate.exchange(uri, HttpMethod.POST, entity,
				ResponseConfig.class);
		ResponseConfig res = response.getBody();
		if (res.getCode() == 302) {

			return true;

		} else if (res.getCode() == 400) {
			return false;
		}
		return false;

	}

	public String registerPatient(String reqData) {

		JsonNode reqNode = null;

		try {
			reqNode = objMapper.readValue(reqData, JsonNode.class);

			String transactionId = reqNode.get("transactionId").asText();
			String password = reqNode.get("otp").get("value").asText();
			HttpHeaders headers = new HttpHeaders();
			Person usr = usrCacheSrv.getUser(transactionId);
			if (usr != null && usr.getTransctionId().equalsIgnoreCase(transactionId)) {
				ResponseConfig resAuth = auth.getJwtAuthentication();
				String uri = LoadConfig.getConfigValue("PREGISTERPATIENT");

				headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
				if (resAuth != null) {
					headers.add("Authorization", "Bearer " + resAuth.getResponse());
					headers.add("X-Forwarded-Host", env.getProperty("x-forward-host"));
				}
				HttpEntity<String> entity = new HttpEntity<>(reqData, headers);
				ResponseEntity<ResponseConfig> response = restTemplate.exchange(uri, HttpMethod.POST, entity,
						ResponseConfig.class);
				ResponseConfig res = response.getBody();
				if (res.getCode() == 200) {

					if (registerUser(usr)) {
						return "Login successfully created";
					} else {
						return "E8055";
					}

				} else if (res.getCode() == 400) {
					return res.getErrorcode();
				}

			} else {
				return "E8065";
			}
		} catch (JsonProcessingException e) {
			return "E8001";
		}
		return null;
	}

	public Boolean registerUser(Person u) {

		Person usr = usrRepository.save(u);
		if (usr != null) {
			return true;
		}
		return false;

	}
}
