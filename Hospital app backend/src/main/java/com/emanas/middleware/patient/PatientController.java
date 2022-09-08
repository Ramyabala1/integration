package com.emanas.middleware.patient;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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
import com.emanas.middleware.models.PatientData;
import com.emanas.middleware.models.ResponseConfig;
import com.emanas.middleware.patient.ehr.EhrService;
import com.emanas.middleware.redis.model.AuthVerify;
import com.emanas.middleware.utility.LoadConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;

@Api(value = "Swagger API", description = "Create Patient")
@RestController
@Slf4j
public class PatientController {

	@Autowired
	private Environment env;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	PatientService patServ;

	@Autowired
	EhrService ehrServ;

	@Autowired
	ConfigurationUrl config;

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/patient/initAuth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig initAuthentication(@RequestBody String reqdata) throws IOException, PropertyException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(reqdata, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();

		PatientData patData = new PatientData();
		if (node.has("mobile")) {
			patData.setMobile(node.get("mobile").asText());
		}
		if (node.has("id")) {
			patData.setId(node.get("id").asText());
		}
		if (node.has("otpChannel")) {
			List<String> otpList = new ArrayList<String>();
			JsonNode otp = node.get("otpChannel");
			if (otp.isArray()) {
				for (JsonNode o : otp) {
					otpList.add(o.asText());
				}
			}
			if (otpList.size() > 0) {
				patData.setOtpChannel(otpList);
			}
		}
		patData.setService(node.get("service").asText());

		String transDetails = patServ.initAuthentication(patData);
		ResponseConfig responses = new ResponseConfig();
		responses.setTimeStamp(new Date());
		responses.setApi(api);
		responses.setVersion(version);
		if (transDetails != null && !transDetails.contains("error")) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setTransactionId(transDetails);
			responses.setMessage("Successful");
			responses.setAction("Please send auth credentials for authentication.");

		} else {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode("E2000");
			if (transDetails.contains("-")) {
				responses.setMessage(transDetails.split("-")[1]);
			}
			responses.setAction("Please Try again");
		}

		return responses;

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/patient/verifyAndGetDemogDetails", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig verifyAndGetDemogDetails(@RequestBody String verifyData)
			throws IOException, PropertyException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(verifyData, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		AuthVerify demoDet = patServ.verifyAndGetBaseDetails(verifyData);
		ResponseConfig responses = new ResponseConfig();
		String transactionID = null;
		responses.setTimeStamp(new Date());

		responses.setApi(api);
		responses.setVersion(version);
		if (node.has("transactionID")) {
			transactionID = node.get("transactionID").asText();
		}
		responses.setTransactionId(transactionID);

		if (demoDet.getDemodetail() != null || demoDet.getStatus().equalsIgnoreCase("Verified")) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setResponse(objStringMapper.writeValueAsString(demoDet));
			responses.setTransactionId(demoDet.getTransactionId());
			responses.setMessage("Successful");
			responses.setAction("Please send patient demo details for registration.");

		} else if (demoDet.getError() != null && demoDet.getError().contains("-")) {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode("E2001");
			responses.setMessage(demoDet.getError().split("-")[1]);
			responses.setAction("Please try after sometime");
		} else if (demoDet.getError() != null && demoDet.getError().length() == 5) {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode(demoDet.getError());
			String message = LoadConfig.getConfigValue(demoDet.getError());
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}
		}

		return responses;

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/patient/createPatient", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig createPatient(@RequestBody String patientData) throws IOException, PropertyException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(patientData, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transactionID = null;
		if (node.has("transactionID")) {
			transactionID = node.get("transactionID").asText();
		}

		ResponseConfig responses = new ResponseConfig();

		if (patServ.getRevisedDemoDetails(patientData)) {
			responses = ehrServ.createPatient(patientData);
			responses.setTimeStamp(new Date());
			responses.setApi(api);
			responses.setVersion(version);
			responses.setTransactionId(transactionID);
			if (responses.getCode() == 201) {
				responses.setCode(Response.Status.OK.getStatusCode());
				responses.setMessage(responses.getMessage());
				responses.setAction("Please map organisation to patient");

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
	@RequestMapping(value = "/patient/initRegisterPatientIntoEstablishment", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig initRegisterPatientIntoEstablishment(@RequestBody String patData)
			throws IOException, PropertyException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(patData, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transDetails = patServ.initPatRegInEst(patData);
		ResponseConfig responses = new ResponseConfig();
		responses.setTimeStamp(new Date());
		responses.setApi(api);
		responses.setVersion(version);
		if (transDetails != null && !transDetails.contains("error")) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setTransactionId(transDetails);
			responses.setMessage("Successful");
			responses.setAction("Please send auth credentials for authentication.");

		} else if (transDetails.contains("error") && transDetails.contains("-")) {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode("E2000");
			if (transDetails.contains("-")) {
				responses.setMessage(transDetails.split("-")[1]);
			}
			responses.setAction("Please Try again");
		} else if (transDetails.contains("error") && transDetails.length() == 5) {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode(transDetails);
			String message = LoadConfig.getConfigValue(transDetails);
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}
		}
		return responses;

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/patient/registerPatientToEstablish", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig registerPatientToEstablish(@RequestBody String registerData)
			throws IOException, PropertyException {

		ObjectMapper objMapper = new ObjectMapper();
		JsonNode node = objMapper.readValue(registerData, JsonNode.class);
		String demoDet = patServ.registerPatientToEstablish(registerData);

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
		if (demoDet != null || demoDet.equalsIgnoreCase("Patient Mapped")) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setMessage(demoDet);
		} else if (demoDet != null && demoDet.contains("-") && demoDet.contains("error")) {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode("E2001");
			responses.setMessage(demoDet.split("-")[1]);
			responses.setAction("Please try after sometime");
		} else if (demoDet != null && demoDet.length() == 5 && demoDet.substring(0, 1).contains("E")) {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode(demoDet);
			String message = LoadConfig.getConfigValue(demoDet);
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}
		}

		return responses;

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/patient/initAssignAuthEntity", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig initAssignAuthEntity(@RequestBody String patData)
			throws IOException, PropertyException, ParseException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(patData, JsonNode.class);
		String transactionID = null;
		if (node.has("transactionID")) {
			transactionID = node.get("transactionID").asText();
		}
		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transDetails = patServ.initAssignAuthEntity(patData);
		ResponseConfig responses = new ResponseConfig();
		responses.setTimeStamp(new Date());
		responses.setApi(api);
		responses.setVersion(version);
		responses.setTransactionId(transDetails);
		if (transDetails != null && !transDetails.contains("error")) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setMessage("Successful");
			responses.setAction("Please send auth credentials for authentication");

		} else if (transDetails.contains("error") && transDetails.contains("-")) {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode("E2000");
			if (transDetails.contains("-")) {
				responses.setMessage(transDetails.split("-")[1]);
			}
			responses.setAction("Please Try again");
		} else if (transDetails.contains("error") && transDetails.length() == 5) {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode(transDetails);
			String message = LoadConfig.getConfigValue(transDetails);
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}
		}
		return responses;

	}

	@CrossOrigin(origins = "*")
	@RequestMapping(value = "/patient/assignAuthEntity", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseConfig assignAuthEntity(@RequestBody String patData)
			throws IOException, PropertyException, ParseException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(patData, JsonNode.class);
		String transactionID = null;
		if (node.has("transactionID")) {
			transactionID = node.get("transactionID").asText();
		}
		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transDetails = patServ.assignAuthEntity(patData);
		ResponseConfig responses = new ResponseConfig();
		responses.setTimeStamp(new Date());

		responses.setApi(api);
		responses.setVersion(version);
		responses.setTransactionId(transactionID);
		if (transDetails != null && !transDetails.contains("error")) {
			responses.setResponse(transDetails);
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setMessage("Patient AE mapped successfully");

		} else if (transDetails.contains("error") && transDetails.contains("-")) {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode("E2000");
			if (transDetails.contains("-")) {
				responses.setMessage(transDetails.split("-")[1]);
			}
			responses.setAction("Please Try again");
		} else if (transDetails.contains("error") && transDetails.length() == 5) {
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrorcode(transDetails);
			String message = LoadConfig.getConfigValue(transDetails);
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}
		}
		return responses;

	}

}
