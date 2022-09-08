package com.emanas.middleware.ehr;

import java.io.IOException;
import java.text.ParseException;
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

import com.emanas.middleware.log.EhrAccessLog;
import com.emanas.middleware.models.ResponseConfig;
import com.emanas.middleware.utility.LoadConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(value = "Swagger API", description = "EHR")
@RestController
@Slf4j
public class EHRController {

	@Autowired
	EHRService ehrServ;

	@Autowired
	ObjectMapper objStringMapper;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/ehr/viewTreatments", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig viewTreatments(@RequestBody String viewEhrData)
			throws IOException, PropertyException, ParseException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(viewEhrData, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transactionID = null;
		if (node.has("transactionID")) {
			transactionID = node.get("transactionID").asText();
		}
		String response = ehrServ.viewTreatments(viewEhrData);

		JsonNode consent = node.get("hiRequest").get("consent");
		String consentID = consent.get("id").asText();

		ResponseConfig responses = new ResponseConfig();
		responses.setTransactionId(transactionID);
		responses.setTimeStamp(new Date());
		responses.setApi(api);
		responses.setVersion(version);
		if (response != null && response.length() != 5 && !response.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setResponse(response);
			responses.setMessage("Successful");
			responses.setAction("View Health Records");

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
	@RequestMapping(value = "/ehr/createHealthRecord", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig createHealthRecord(@RequestBody String ehrData)
			throws IOException, PropertyException, ParseException {
		EhrAccessLog ehrlog = new EhrAccessLog();
		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(ehrData, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transactionID = null;
		if (node.has("transactionID")) {
			transactionID = node.get("transactionID").asText();
		}
		String response = ehrServ.createHealthRecord(ehrData);

		ResponseConfig responses = new ResponseConfig();
		responses.setTransactionId(transactionID);
		responses.setTimeStamp(new Date());
		responses.setApi(api);
		responses.setVersion(version);

		// responses.setConsentID(consentID);
		if (response != null && response.length() != 5 && !response.substring(0, 1).contains("E")) {

			responses.setCode(Response.Status.OK.getStatusCode());
			// responses.setResponse(response);
			responses.setMessage(response);

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

		ehrServ.saveEhrAccessLog(responses, ehrData, ehrlog);

		return responses;

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/ehr/viewHealthRecord", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig viewHealthRecord(@RequestBody String viewEhrData)
			throws IOException, PropertyException, ParseException {
		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(viewEhrData, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transactionID = null;
		if (node.has("transactionID")) {
			transactionID = node.get("transactionID").asText();
		}
		String response = ehrServ.viewTreatments(viewEhrData);
		ResponseConfig responses = new ResponseConfig();
		responses.setTransactionId(transactionID);
		responses.setTimeStamp(new Date());
		responses.setApi(api);
		responses.setVersion(version);
		if (response != null && response.length() != 5 && !response.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setResponse(response);
			responses.setMessage("Successful");
			responses.setAction("View Health Records");

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
	@RequestMapping(value = "/ehr/searchOrgName/{orgName}", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig searchOrgName(@PathVariable(value = "orgName") String orgName)
			throws IOException, PropertyException, ParseException {
		ObjectMapper objStringMapper = new ObjectMapper();

		String response = ehrServ.searchOrgName(orgName);
		ResponseConfig responses = new ResponseConfig();
		responses.setTimeStamp(new Date());

		if (response != null && response.length() != 5 && !response.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setResponse(response);

			responses.setMessage("List of hospitals displayed");

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
	@RequestMapping(value = "/ehr/getEHRContextByHIP", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig getEHRContextByHIP(@RequestBody String eHRContext)
			throws IOException, PropertyException, ParseException {

		String response = ehrServ.getEHRContextByHIP(eHRContext);
		JsonNode node = objStringMapper.readValue(eHRContext, JsonNode.class);

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
			responses.setResponse(response);
			responses.setMessage("getEHRContextByHIP");

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
	@RequestMapping(value = "/ehr/getViewPolicy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public Boolean getViewPolicy(@RequestBody String eHRContext) throws IOException, PropertyException, ParseException {

		Boolean response = ehrServ.getViewPolicy(eHRContext);

		return response;

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/ehr/getCompositionDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig getCompositionDetails(@RequestBody String compData)
			throws IOException, PropertyException, ParseException {

		String response = ehrServ.getCompositionDetails(compData);

		JsonNode node = objStringMapper.readValue(compData, JsonNode.class);

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
			responses.setResponse(response);
			responses.setMessage("The details");

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
	@RequestMapping(value = "/ehr/getHIUDetails", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig getOrgHIUDetails() throws IOException, PropertyException, ParseException {

		String response = ehrServ.getHIUDetails();

		ResponseConfig responses = new ResponseConfig();

		if (response != null && response.length() != 5 && !response.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setResponse(response);

			responses.setMessage("getHIUDetails");

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
	@RequestMapping(value = "/ehr/getMheRegId/{orgUUID}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig getMheRegId(@PathVariable(value = "orgUUID") String data)
			throws IOException, PropertyException, ParseException {

		String response = ehrServ.getMheRegId(data);

		ResponseConfig responses = new ResponseConfig();

		if (response != null) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setResponse(response);

			responses.setMessage("getMheRegId");

		} else {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode("E8077");
			String message = LoadConfig.getConfigValue("E8077");
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}
		}

		return responses;

	}

}
