package com.emanas.middleware.person;

import java.io.IOException;
import java.util.Date;

import javax.ws.rs.core.Response;
import javax.xml.bind.PropertyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.config.ConfigurationUrl;
import com.emanas.middleware.models.ResponseConfig;
import com.emanas.middleware.patient.ehr.EhrService;
import com.emanas.middleware.utility.LoadConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(value = "Swagger API", description = "Create Person")
@RestController
@Slf4j
public class PersonController {

	@Autowired
	private Environment env;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	PersonAuthenticationService perServ;

	@Autowired
	EhrService ehrServ;

	@Autowired
	ConfigurationUrl config;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/person/initVerification", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig initVerification(@RequestBody String patData) throws IOException, PropertyException {
		String response = perServ.initVerification(patData);

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(patData, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transactionID = null;
		if (node.has("transactionID")) {
			transactionID = node.get("transactionID").asText();
		}
		ResponseConfig responses = new ResponseConfig();
		responses.setTransactionId(transactionID);
		responses.setTimeStamp(new Date());
		responses.setApi(api);
		responses.setVersion(version);

		if (response != null && response.length() != 5 && !response.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setTransactionId(response);
			responses.setMessage("Successful");
			responses.setAction("Please send auth credentials for authentication.");

		} else if (response != null && response.length() == 5 && response.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode(response);
			String message = LoadConfig.getConfigValue(response);
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}
		} else {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setMessage(response);
			responses.setAction("Please Try again");
		}



		return responses;

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/person/registerPatient", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig registerPatient(@RequestBody String responseData) throws IOException, PropertyException {

		String response = perServ.registerPatient(responseData);
		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(responseData, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transactionID = null;
		if (node.has("transactionID")) {
			transactionID = node.get("transactionID").asText();
		}
		ResponseConfig responses = new ResponseConfig();

		responses.setTransactionId(transactionID);
		responses.setTimeStamp(new Date());
		responses.setApi(api);
		responses.setVersion(version);
		if (response != null && response.length() != 5 && !response.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setAction("Please note the consentID");
			responses.setMessage("Consent Generated successfully");
			responses.setResponse(response);
			responses.setConsentID(response);

		} else if (response.length() == 5 && response.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode(response);
			String message = LoadConfig.getConfigValue(response);
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}
		} else {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setMessage(response);
			responses.setAction("Please Try again");
		}

		return responses;

	}


	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/patientapp/getToken", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig getToken() throws IOException, PropertyException {

		ResponseConfig responses = new ResponseConfig();

		return responses;

	}

}
