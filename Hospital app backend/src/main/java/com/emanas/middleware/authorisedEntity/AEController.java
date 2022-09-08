package com.emanas.middleware.authorisedEntity;

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
import com.emanas.middleware.patient.PatientService;
import com.emanas.middleware.patient.ehr.EhrService;
import com.emanas.middleware.utility.LoadConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(value = "Swagger API", description = "Create Patient")
@RestController
@Slf4j
public class AEController {

	@Autowired
	private Environment env;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	AEService aeServ;

	@Autowired
	EhrService ehrServ;

	@Autowired
	ConfigurationUrl config;

	@Autowired
	PatientService patServ;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/ae/initAERegistration", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig initAERegistration(@RequestBody String data) throws IOException, PropertyException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(data, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transDetails = aeServ.initAERegistration(data);
		ResponseConfig responses = new ResponseConfig();

		responses.setTimeStamp(new Date());
		responses.setApi(api);
		responses.setVersion(version);
		if (transDetails != null && transDetails.length() != 5 && !transDetails.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setTransactionId(transDetails);
			responses.setMessage("Successful");
			responses.setAction("Please send auth credentials for authentication.");

		} else if (transDetails != null && transDetails.length() == 5 && transDetails.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode(transDetails);
			String message = LoadConfig.getConfigValue(transDetails);
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}
		} else {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setMessage(transDetails);
			responses.setAction("Please Try again");
		}

		return responses;

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/ae/createAE", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig createAERegistration(@RequestBody String data) throws IOException, PropertyException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(data, JsonNode.class);
		String transactionID = null;
		if (node.has("transactionID")) {
			transactionID = node.get("transactionID").asText();
		}
		String api = node.get("api").asText();
		String version = node.get("version").asText();

		ResponseConfig responses = new ResponseConfig();

		if (patServ.getRevisedDemoDetails(data)) {
			responses = aeServ.createAE(data);
			responses.setTransactionId(transactionID);
			responses.setTimeStamp(new Date());
			responses.setApi(api);
			responses.setVersion(version);
			if (responses.getCode() == 201) {
				responses.setCode(Response.Status.OK.getStatusCode());
				responses.setMessage(responses.getMessage());
				responses.setAction("Map Authorised Entity to patient");

			} else if (responses.getCode() == 441) {
				responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
				responses.setErrorcode("E1020");
				String message = LoadConfig.getConfigValue("E1020");
				if (message != null) {
					responses.setMessage(message.split("-")[0]);
					responses.setAction(message.split("-")[1]);
				}
			}

			else {
				responses.setMessage(responses.getMessage());
				responses.setErrorcode("E2022");
				responses.setAction("Please try again");
			}

		} else {
			responses.setCode(Response.Status.CONFLICT.getStatusCode());
			responses.setResponse(null);
			responses.setErrorcode("E1044");
			String message = LoadConfig.getConfigValue("E1044");
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}

		}
		return responses;

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/ae/getAEMap", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig getAEMap(@RequestBody String data) throws IOException, PropertyException {
		ResponseConfig responses = new ResponseConfig();

		String transDetails = aeServ.getAEPatientMap(data);

		if (transDetails != null) {
			responses.setCode(Response.Status.FOUND.getStatusCode());
			responses.setResponse(transDetails);

		} else {
			responses.setCode(Response.Status.CONFLICT.getStatusCode());
			responses.setResponse(null);

			responses.setErrors("AE Patient Mapping not found");
			responses.setErrorcode("E1110");

		}

		return responses;

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/ae/AERegistration", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig aERegistration(@RequestBody String data) throws IOException, PropertyException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(data, JsonNode.class);
		String transactionID = null;
		if (node.has("transactionID")) {
			transactionID = node.get("transactionID").asText();
		}
		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transDetails = aeServ.aeRegistration(data);

		ResponseConfig responses = new ResponseConfig();
		responses.setTransactionId(transactionID);
		responses.setTimeStamp(new Date());
		responses.setApi(api);
		responses.setVersion(version);

		if (transDetails != null && transDetails.length() != 5 && !transDetails.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setResponse(transDetails);

		} else if (transDetails != null && transDetails.length() == 5 && transDetails.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode(transDetails);
			String message = LoadConfig.getConfigValue(transDetails);
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}
		} else {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setMessage(transDetails);
			responses.setAction("Please Try again");
		}

		return responses;

	}

}
