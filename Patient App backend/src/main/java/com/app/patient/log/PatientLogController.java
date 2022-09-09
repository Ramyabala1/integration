package com.app.patient.log;

import java.util.Date;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.app.patient.model.ResponseConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class PatientLogController {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	PatientLogService patLogServ;

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/log/addLog", method = RequestMethod.POST)
	public ResponseConfig initRegistration(@RequestBody PatientLog logData) {

		Boolean res = patLogServ.saveLog(logData);
		ResponseConfig responses = new ResponseConfig();

		if (res != null && res) {
			responses.setCode(Response.Status.OK.getStatusCode());

		} else {

			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setResponse(null);

		}

		return responses;

	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "/log/getAccessHistory", method = RequestMethod.POST)
	public ResponseConfig initRegistration(@RequestBody String logData)
			throws JsonMappingException, JsonProcessingException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(logData, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transactionID = null;
		if (node.has("transactionId")) {
			transactionID = node.get("transactionId").asText();
		}
		String patientID = node.get("patientId").asText();
		ResponseConfig res = patLogServ.getAccessLogs(patientID);
		res.setTransactionId(transactionID);
		res.setTimeStamp(new Date());
		res.setApi(api);
		res.setVersion(version);
		return res;
	}

}