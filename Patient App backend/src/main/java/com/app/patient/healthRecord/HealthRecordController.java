package com.app.patient.healthRecord;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.app.patient.composition.Composition;
import com.app.patient.composition.CompositionService;
import com.app.patient.config.LoadConfig;
import com.app.patient.model.ResponseConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class HealthRecordController {

	@Autowired
	HealthRecordServiceImpl serv;

	@Autowired
	CompositionService compServ;

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(path = "/search/{orgName}", method = RequestMethod.POST)
	public ResponseConfig find(@PathVariable String orgName) throws IOException {

//		String api = node.get("api").asText();
//		String version = node.get("version").asText();
//		String transactionID = null ;
//		if(node.has("transactionID"))
//		{
//			transactionID= node.get("transactionID").asText();
//		}
		ResponseConfig resp = serv.find(orgName);
//		resp.setTransactionId(transactionID);
		resp.setTimeStamp(new Date());
//		resp.setApi(api);
//		resp.setVersion(version);
		return resp;

	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(path = "/getEHRContextByHIP", method = RequestMethod.POST)
	public ResponseConfig getEHRContextByHIP(@RequestBody String reqData) throws IOException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(reqData, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();

		ResponseConfig resp = serv.getEHRContext(reqData);

		resp.setTimeStamp(new Date());
		resp.setApi(api);
		resp.setVersion(version);
		return resp;

	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(path = "/initAddEHRContextByHIP", method = RequestMethod.POST)
	public ResponseConfig initAddEHRContextByHIP(@RequestBody String healthData) throws IOException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(healthData, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transactionID = null;
		if (node.has("transactionId")) {
			transactionID = node.get("transactionId").asText();
		}
		ResponseConfig resp = serv.initAddEHRContextByHIP(healthData);
		resp.setTransactionId(transactionID);
		resp.setTimeStamp(new Date());
		resp.setApi(api);
		resp.setVersion(version);
		return resp;

	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(path = "/addEHRContextByHIP", method = RequestMethod.POST)
	public ResponseConfig addEHRContextByHIP(@RequestBody String healthData) throws IOException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(healthData, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transactionID = null;
		if (node.has("transactionId")) {
			transactionID = node.get("transactionId").asText();
		}
		ResponseConfig resp = serv.addEHRContextByHIP(healthData);
		resp.setTransactionId(transactionID);
		resp.setTimeStamp(new Date());
		resp.setApi(api);
		resp.setVersion(version);
		return resp;

	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(path = "/getCompositionDetails", method = RequestMethod.POST)
	public ResponseConfig getCompositionDetails(@RequestBody String record) throws IOException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode node = objStringMapper.readValue(record, JsonNode.class);

		String api = node.get("api").asText();
		String version = node.get("version").asText();
		String transactionID = null;
		if (node.has("transactionId")) {
			transactionID = node.get("transactionId").asText();
		}
		ResponseConfig res = serv.getCompositionDetails(record);
		res.setTransactionId(transactionID);
		res.setTimeStamp(new Date());
		res.setApi(api);
		res.setVersion(version);
		return res;

	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(path = "/getPatientHospitalMap", method = RequestMethod.POST)
	public ResponseConfig getPatientHospitalMap(@RequestBody String record) throws IOException {

		List<HealthRecord> res = serv.getPatientHospitalMap(record);
		JSONArray arr = new JSONArray(res);
		ResponseConfig responses = new ResponseConfig();

		if (res != null && arr.length() > 0) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setResponse(arr.toString());

			responses.setMessage("Listed the records");
		} else {
			responses.setErrorcode("E8004");
			String message = LoadConfig.getConfigValue("E8004");
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setResponse(null);

		}

		return responses;

	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(path = "/getCompositionList", method = RequestMethod.POST)
	public ResponseConfig getCompositionList(@RequestBody String record) throws IOException {

		List<Composition> res = serv.getCompositionList(record);
		JSONArray arr = new JSONArray(res);
		ResponseConfig responses = new ResponseConfig();

		if (res != null && arr.length() > 0) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setResponse(arr.toString());

			responses.setMessage("getting composition");
		} else {

			responses.setErrorcode("E8006");
			String message = LoadConfig.getConfigValue("E8006");
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}
			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setResponse(null);

		}

		return responses;

	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(path = "/viewHealthRecord", method = RequestMethod.POST)
	public ResponseConfig viewHealthRecord(@RequestBody String record) throws IOException {

		ResponseConfig res = serv.viewHealthRecord(record);

		return res;

	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(path = "/getMheRegId/{orgUUID}", method = RequestMethod.GET)
	public ResponseConfig getMheRegId(@PathVariable(value = "orgUUID") String orgUUID) throws IOException {

		ResponseConfig res = serv.getMheRegId(orgUUID);
		return res;

	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(path = "/unlinkPatient", method = RequestMethod.POST)
	public ResponseConfig unlinkPatient(@RequestBody String record) throws IOException {

		Boolean res = serv.unlinkPatient(record);
		ResponseConfig responses = new ResponseConfig();

		if (res != null && res) {
			responses.setCode(Response.Status.OK.getStatusCode());
			responses.setMessage("Patient unlinked successfully");

		} else {

			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setErrors("Patient could not be unlinked");
			responses.setResponse(null);

		}

		return responses;

	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(path = "healthRecord/getCompList", method = RequestMethod.POST)
	public ResponseConfig getCompList(@RequestBody String record) throws IOException {

		ObjectMapper objStringMapper = new ObjectMapper();
		JsonNode rnode = objStringMapper.readValue(record, JsonNode.class);
		String episodeId = rnode.get("episodeId").asText();
		List<Composition> compList = compServ.getCompositionList(episodeId);
		JSONArray arr = new JSONArray(compList);
		ResponseConfig responses = new ResponseConfig();

		if (arr != null && arr.length() > 0) {

			responses.setResponse(arr.toString(0));
			responses.setCode(Response.Status.OK.getStatusCode());

		} else {

			responses.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			responses.setMessage("no Record");
			responses.setResponse(null);

		}

		return responses;

	}

}
