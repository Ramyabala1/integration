package com.emanas.middleware.consent;

import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

import javax.ws.rs.core.Response;
import javax.xml.bind.PropertyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.emanas.middleware.models.ResponseConfig;
import com.emanas.middleware.utility.LoadConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(value = "Swagger API", description = "Create Consent")
@RestController
@Slf4j

public class ConsentController {
	@Autowired
	ConsentService conServ;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/consent/initConsent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig initConsent(@RequestBody String consentData)
			throws IOException, PropertyException, ParseException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(consentData, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String response = conServ.initConsent(consentData);
		ResponseConfig responses = new ResponseConfig();

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
	@RequestMapping(value = "/consent/createConsent", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig createConsent(@RequestBody String authConsent) throws IOException, PropertyException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(authConsent, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transactionID = null;
		if (node.has("transactionID")) {
			transactionID = node.get("transactionID").asText();
		}
		String response = conServ.createConsent(authConsent);

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
	@RequestMapping(value = "/consent/getConsentForPatient", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig getConsent(@RequestBody String reqData) throws IOException, PropertyException {

		String response = conServ.getConsent(reqData);

		ResponseConfig responses = new ResponseConfig();

		if (response != null && response.length() != 5 && !response.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.OK.getStatusCode());

			responses.setMessage("patient consent");
			responses.setResponse(response);

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
	@RequestMapping(value = "/consent/getInActiveConsentForPatient", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig getInActiveConsentForPatient(@RequestBody String reqData)
			throws IOException, PropertyException {

		String response = conServ.getInActiveConsent(reqData);

		ObjectMapper objMapper = new ObjectMapper();
		JsonNode node = objMapper.readValue(reqData, JsonNode.class);
		String transactionID = null;
		if (node.has("transactionID")) {
			transactionID = node.get("transactionID").asText();
		}

		ResponseConfig responses = new ResponseConfig();
		String api = node.get("api").asText();
		String version = node.get("version").asText();
		responses.setTimeStamp(new Date());
		responses.setApi(api);
		responses.setTransactionId(transactionID);
		responses.setVersion(version);

		if (response != null && response.length() != 5 && !response.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.OK.getStatusCode());

			responses.setMessage("patient consent");
			responses.setResponse(response);

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
	@RequestMapping(value = "/consent/revokeConsentRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig revokeConsentRequest(@RequestBody String reqData) throws IOException, PropertyException {

		ObjectMapper objMapper = new ObjectMapper();
		JsonNode node = objMapper.readValue(reqData, JsonNode.class);
		String response = conServ.revokeConsent(reqData);

		String transactionID = null;
		if (node.has("transactionID")) {
			transactionID = node.get("transactionID").asText();
		}

		ResponseConfig responses = new ResponseConfig();
		String api = node.get("api").asText();
		String version = node.get("version").asText();
		responses.setTimeStamp(new Date());
		responses.setApi(api);
		responses.setTransactionId(transactionID);
		responses.setVersion(version);

		if (response != null && response.length() != 5 && !response.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setAction("Please note the consent id");
			responses.setMessage("Consent revoked successfully");
			responses.setResponse(response);

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
	@RequestMapping(value = "/consent/updateConsentRequest", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig updateConsentRequest(@RequestBody String reqData) throws IOException, PropertyException {
		ObjectMapper objMapper = new ObjectMapper();
		JsonNode node = objMapper.readValue(reqData, JsonNode.class);
		String response = conServ.updateConsent(reqData);

		String transactionID = null;
		if (node.has("transactionID")) {
			transactionID = node.get("transactionID").asText();
		}

		ResponseConfig responses = new ResponseConfig();
		String api = node.get("api").asText();
		String version = node.get("version").asText();
		responses.setTimeStamp(new Date());
		responses.setApi(api);
		responses.setTransactionId(transactionID);
		responses.setVersion(version);

		if (response != null && response.length() != 5 && !response.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setAction("Please noe the consent id");
			responses.setMessage("Consent updated successfully");
			responses.setResponse(response);

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

}
