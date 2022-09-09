package com.app.patient.consent;

import java.io.IOException;
import java.util.Date;

import javax.ws.rs.core.Response;
import javax.xml.bind.PropertyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.patient.config.LoadConfig;
import com.app.patient.model.ResponseConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class ConsentController {

	@Autowired
	ConsentService serv;

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(path = "/initConsent", method = RequestMethod.POST)
	public ResponseConfig initConsent(@RequestBody String Data) throws IOException {
		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(Data, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();

		ResponseConfig responses = serv.initConsent(Data);
		responses.setTimeStamp(new Date());
		responses.setApi(api);
		responses.setVersion(version);

		if (responses != null && !responses.getErrorcode().equalsIgnoreCase("E8001"))

		{
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setErrorcode(responses.getErrorcode());
			String message = LoadConfig.getConfigValue(responses.getErrorcode());
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}
		}

		return responses;

	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(path = "/createConsent", method = RequestMethod.POST)
	public ResponseConfig createConsent(@RequestBody String Data) throws IOException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(Data, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transactionID = null;
		if (node.has("transactionId")) {
			transactionID = node.get("transactionId").asText();
		}
		ResponseConfig responses = serv.CreateConsent(Data);
		responses.setTimeStamp(new Date());
		responses.setApi(api);
		responses.setVersion(version);
		responses.setTransactionId(transactionID);
		if (responses != null && !responses.getErrorcode().equalsIgnoreCase("E8001"))

		{
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setErrorcode(responses.getErrorcode());
			String message = LoadConfig.getConfigValue(responses.getErrorcode());
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}
		}

		return responses;

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/consent/getConsentForPatient/{type}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig getConsent(@RequestBody String reqData, @PathVariable(value = "type") String type)
			throws IOException, PropertyException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(reqData, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transactionID = null;
		if (node.has("transactionId")) {
			transactionID = node.get("transactionId").asText();
		}
		ResponseConfig resp = serv.getConsent(reqData, type);
		resp.setTransactionId(transactionID);
		resp.setTimeStamp(new Date());
		resp.setApi(api);
		resp.setVersion(version);
		return resp;

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/consent/manageConsentRequest/{action}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig manageConsentRequest(@RequestBody String reqData,
			@PathVariable(value = "action") String action) throws IOException, PropertyException {
		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(reqData, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transactionID = null;
		if (node.has("transactionID")) {
			transactionID = node.get("transactionID").asText();
		}
		ResponseConfig resp = serv.revokeConsent(reqData, action);
		resp.setTransactionId(transactionID);
		resp.setTimeStamp(new Date());
		resp.setApi(api);
		resp.setVersion(version);
		return resp;

	}

}
