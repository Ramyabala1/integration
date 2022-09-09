package com.app.patient.healthRecord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.Response;

import org.json.JSONArray;
import org.json.JSONObject;
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
import com.app.patient.composition.Composition;
import com.app.patient.composition.CompositionService;
import com.app.patient.config.LoadConfig;
import com.app.patient.model.ResponseConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class HealthRecordServiceImpl {

	Logger logger = LoggerFactory.getLogger(AuthenticationService.class);

	@Autowired
	private Environment env;

	@Autowired
	AuthenticationService auth;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ObjectMapper objMapper;

	@Autowired
	HealthCache cache;

	@Autowired
	HealthRecordService serv;

	@Autowired
	CompositionService compServ;

	@Autowired
	HealthRecordDao healthDao;

	public ResponseConfig find(String orgName) {

		HttpHeaders headers = new HttpHeaders();
		ResponseConfig resAuth = auth.getJwtAuthentication();
		String uri = LoadConfig.getConfigValue("SEARCHORGNAME") + orgName;
		if (resAuth != null && resAuth.getMessage().equalsIgnoreCase("Successful")) {
			headers.add("Authorization", "Bearer " + resAuth.getResponse());
			headers.add("X-Forwarded-Host", env.getProperty("x-forward-host"));
			HttpEntity<String> entity = new HttpEntity<>(headers);
			ResponseEntity<ResponseConfig> response = restTemplate.exchange(uri, HttpMethod.POST, entity,
					ResponseConfig.class);
			ResponseConfig res = response.getBody();
			return res;
		} else {

			resAuth.setErrorcode("E8000");
			String message = LoadConfig.getConfigValue("E8000");
			if (message != null) {
				resAuth.setMessage(message.split("-")[0]);
				resAuth.setAction(message.split("-")[1]);
			}
			resAuth.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			resAuth.setResponse(null);
			return resAuth;
		}

	}

	public ResponseConfig getEHRContext(String reqData) throws JsonMappingException, JsonProcessingException {
		JsonNode reqNode = null;

		List<Composition> compList = new ArrayList<Composition>();
		reqNode = objMapper.readValue(reqData, JsonNode.class);
		String orgUUID = reqNode.get("hip").get("orgUUID").asText();
		String patientId = reqNode.get("patientId").asText();
		String hospitalname = reqNode.get("hip").get("estName").asText();
		ResponseConfig resCheck = new ResponseConfig();
		List<HealthRecord> healthList = serv.getHealth(patientId);

		if (healthList != null && healthList.size() > 0) {
			for (int i = 0; i < healthList.size(); i++) {
				if (healthList.get(i).getIsLinked() && healthList.get(i).getOrgUUID().equalsIgnoreCase(orgUUID)) {
					resCheck.setErrors("E8003");

					String message = LoadConfig.getConfigValue("E8003");
					if (message != null) {
						resCheck.setMessage(message.split("-")[0]);
						resCheck.setAction(message.split("-")[1]);
					}
					resCheck.setCode(Response.Status.BAD_REQUEST.getStatusCode());
					resCheck.setResponse(null);
					return resCheck;

				}
			}
		}

		HttpHeaders headers = new HttpHeaders();
		ResponseConfig resAuth = auth.getJwtAuthentication();
		String uri = LoadConfig.getConfigValue("GETEHRCONTEXTBYHIP");
		headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		if (resAuth != null && resAuth.getMessage().equalsIgnoreCase("Successful")) {
			headers.add("Authorization", "Bearer " + resAuth.getResponse());
			headers.add("X-Forwarded-Host", env.getProperty("x-forward-host"));
			HealthRecord health = new HealthRecord();
			HttpEntity<String> entity = new HttpEntity<>(reqData, headers);
			ResponseEntity<ResponseConfig> response = restTemplate.exchange(uri, HttpMethod.POST, entity,
					ResponseConfig.class);
			ResponseConfig res = response.getBody();
			if (res.getCode() == 200 && !res.getResponse().equalsIgnoreCase("No encounters present")) {
				JSONObject resOut = new JSONObject(res.getResponse());
				if (resOut.has("children")) {
					String id = UUID.randomUUID().toString();
					health.setEmanas(patientId);
					health.setOrgUUID(orgUUID);
					health.setHealthcareEstablishment(hospitalname);
					health.setTransactionId(id);
					health.setEpisodeId(resOut.getString("uuid"));
					health.setHealthData(res.getResponse());
					cache.saveHealth(health);
					res.setTransactionId(id);
					return res;

				} else {
					String id = UUID.randomUUID().toString();
					health.setEmanas(patientId);
					health.setEpisodeId(resOut.getString("uuid"));
					health.setOrgUUID(orgUUID);
					health.setHealthcareEstablishment(hospitalname);
					health.setTransactionId(id);
					health.setHealthData(res.getResponse());
					cache.saveHealth(health);
					res.setTransactionId(id);

					String message = LoadConfig.getConfigValue("E8085");
					if (message != null) {
						res.setMessage(message.split("-")[0]);
						res.setAction(message.split("-")[1]);
					}

					return res;

				}

			} else {
				resAuth.setErrorcode("E8048");
				String message = LoadConfig.getConfigValue("E8048");
				if (message != null) {
					resAuth.setMessage(message.split("-")[0]);
					resAuth.setAction(message.split("-")[1]);
				}
				resAuth.setCode(Response.Status.BAD_REQUEST.getStatusCode());
				resAuth.setResponse(null);
				return resAuth;
			}

		} else {

			resAuth.setErrorcode("E8000");
			String message = LoadConfig.getConfigValue("E8000");
			if (message != null) {
				resAuth.setMessage(message.split("-")[0]);
				resAuth.setAction(message.split("-")[1]);
			}
			resAuth.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			resAuth.setResponse(null);
			return resAuth;

		}

	}

	public ResponseConfig initAddEHRContextByHIP(String healthData) {
		JsonNode healthnode = null;

		ResponseConfig res = new ResponseConfig();
		try {
			healthnode = objMapper.readValue(healthData, JsonNode.class);
			String transId = healthnode.get("transactionId").asText();
			HttpHeaders headers = new HttpHeaders();

			ResponseConfig resAuth = auth.getJwtAuthentication();
			String uri = LoadConfig.getConfigValue("PINITCONSENT");
			if (resAuth != null && resAuth.getMessage().equalsIgnoreCase("Successful")) {
				headers.add("Authorization", "Bearer " + resAuth.getResponse());
				headers.add("X-Forwarded-Host", env.getProperty("x-forward-host"));
				HealthRecord h = cache.gethealth(transId);

				if (h != null && transId.equalsIgnoreCase(h.getTransactionId())) {

					HttpEntity<String> entity = new HttpEntity<>(healthData, headers);
					ResponseEntity<ResponseConfig> response = restTemplate.exchange(uri, HttpMethod.POST, entity,
							ResponseConfig.class);
					res = response.getBody();
					if (res.getCode() == 200) {
						cache.deleteHealth(transId);
						h.setConsentTransactionID(res.getTransactionId());
						cache.saveHealth(h);
					}

				} else {

					resAuth.setErrorcode("E8004");
					String message = LoadConfig.getConfigValue("E8004");
					if (message != null) {
						resAuth.setMessage(message.split("-")[0]);
						resAuth.setAction(message.split("-")[1]);
					}
					resAuth.setCode(Response.Status.BAD_REQUEST.getStatusCode());
					resAuth.setResponse(null);
					return resAuth;
				}

			} else {

				resAuth.setErrorcode("E8000");
				String message = LoadConfig.getConfigValue("E8000");
				if (message != null) {
					resAuth.setMessage(message.split("-")[0]);
					resAuth.setAction(message.split("-")[1]);
				}
				resAuth.setCode(Response.Status.BAD_REQUEST.getStatusCode());
				resAuth.setResponse(null);
				return resAuth;
			}

		} catch (JsonProcessingException e) {
			res.setErrorcode("E8001");
			String message = LoadConfig.getConfigValue("E8001");
			if (message != null) {
				res.setMessage(message.split("-")[0]);
				res.setAction(message.split("-")[1]);
			}
			res.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			res.setResponse(null);
			return res;

		}
		return res;

	}

	public ResponseConfig addEHRContextByHIP(String hData) {
		JsonNode hnode = null;
		ResponseConfig res = new ResponseConfig();
		try {
			hnode = objMapper.readValue(hData, JsonNode.class);
			String transactionId = hnode.get("transactionId").asText();
			String otp = hnode.get("otp").asText();
			String api = hnode.get("api").asText();
			String version = hnode.get("version").asText();

			HttpHeaders headers = new HttpHeaders();
			HealthRecord h = cache.gethealth(transactionId);
			if (h.getTransactionId().equalsIgnoreCase(transactionId)) {

				String req = "{\"transactionId\":\"" + h.getConsentTransactionID() + "\",\"otp\":\"" + otp
						+ "\",\"api\":\"" + api + "\",\"version\":\"" + version + "\"}";

				ResponseConfig resAuth = auth.getJwtAuthentication();
				String uri = LoadConfig.getConfigValue("PCREATECONSENT");

				headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
				if (resAuth != null) {
					headers.add("Authorization", "Bearer " + resAuth.getResponse());
					headers.add("X-Forwarded-Host", env.getProperty("x-forward-host"));

				} else {
					res.setErrorcode("E8000");
					String message = LoadConfig.getConfigValue("E8000");
					if (message != null) {
						res.setMessage(message.split("-")[0]);
						res.setAction(message.split("-")[1]);
					}
					res.setCode(Response.Status.BAD_REQUEST.getStatusCode());
					res.setResponse(null);
					return resAuth;
				}
				List<Composition> compList = new ArrayList<Composition>();
				HttpEntity<String> entity = new HttpEntity<>(req, headers);

				ResponseEntity<ResponseConfig> response = restTemplate.exchange(uri, HttpMethod.POST, entity,
						ResponseConfig.class);
				res = response.getBody();

				if (res.getCode() == 200) {
					if (h.getHealthData() != null) {

						JSONObject resOut = new JSONObject(h.getHealthData());

						h.setHealthProfessionalName(resOut.getString("mhpname"));
						h.setEpisodeId(resOut.getString("uuid"));
						h.setCreateDate(new Date());
						h.setTransactionId(h.getTransactionId());
						h.setConsentId(res.getResponse());
						h.setIsLinked(true);
						JSONObject assmentItem = null;
						JSONObject dischargeItem = null;

						JSONObject itemData = null;
						if (resOut.has("children")) {
							JSONArray compositionArray = (JSONArray) resOut.get("children");
							h.setHealthData(compositionArray.toString());

							for (int i = 0; i < compositionArray.length(); i++)

							{
								JSONObject responseObject1 = (JSONObject) compositionArray.get(i);
								String name = responseObject1.getString("name");
								if (name.contains("IP")) {
									if (responseObject1.has("children")) {
										JSONArray comArray = (JSONArray) responseObject1.get("children");

										for (int c = 0; c < comArray.length(); c++) {
											JSONObject rJson = (JSONObject) comArray.get(c);
											String itemName = rJson.getString("name");

											if (itemName.contains("Assessment")) {

												responseObject1 = (JSONObject) comArray.get(c);
												JSONArray assItem = (JSONArray) responseObject1.get("item");
												assmentItem = assItem.getJSONObject(0);

												Composition com = new Composition();
												com.setCompositionId(assmentItem.getString("compositionid"));
												com.setTemplateId(assmentItem.getString("templateId"));
												com.setHealthRecord(h);
												com.setHealthProfessionalName(responseObject1.getString("mhpname"));
												com.setName(itemName);
												compList.add(com);
											}

											else if (itemName.contains("Discharge")) {

												responseObject1 = (JSONObject) comArray.get(c);
												JSONArray disItem = (JSONArray) responseObject1.get("item");
												dischargeItem = disItem.getJSONObject(0);

												Composition com = new Composition();
												com.setCompositionId(dischargeItem.getString("compositionid"));
												com.setTemplateId(dischargeItem.getString("templateId"));
												com.setHealth(h);
												com.setName(itemName);
												com.setHealthProfessionalName(responseObject1.getString("mhpname"));
												compList.add(com);

											}
										}
									}

								} else {
									JSONArray compositionArray1 = (JSONArray) responseObject1.get("item");
									Object[] item = { compositionArray1 };
									itemData = (JSONObject) compositionArray1.get(0);
									Composition c = new Composition();
									c.setCompositionId(itemData.getString("compositionid"));
									c.setTemplateId(itemData.getString("templateId"));
									c.setHealth(h);
									c.setName(name);
									c.setHealthProfessionalName(responseObject1.getString("mhpname"));
									compList.add(c);
								}
							}

						}
					}
					serv.saveHealth(h);

					if (compList.size() > 0) {
						for (int i = 0; i < compList.size(); i++) {

							compServ.saveComposition(compList.get(i));

						}
					}
					return res;
				} else {
					return res;
				}

			} else {
				res.setErrorcode("E8005");
				String message = LoadConfig.getConfigValue("E8005");
				if (message != null) {
					res.setMessage(message.split("-")[0]);
					res.setAction(message.split("-")[1]);
				}
				res.setCode(Response.Status.BAD_REQUEST.getStatusCode());
				res.setResponse(null);
				return res;
			}

		} catch (JsonProcessingException e) {
			res.setErrorcode("E8001");
			String message = LoadConfig.getConfigValue("E8001");
			if (message != null) {
				res.setMessage(message.split("-")[0]);
				res.setAction(message.split("-")[1]);
			}
			res.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			res.setResponse(null);
			return res;

		}
	}

	public ResponseConfig getCompositionDetails(String record) throws JsonMappingException, JsonProcessingException {
		JsonNode rnode = null;
		rnode = objMapper.readValue(record, JsonNode.class);
		HttpHeaders headers = new HttpHeaders();
		ResponseConfig resAuth = auth.getJwtAuthentication();
		String uri = LoadConfig.getConfigValue("PGETCOMPDETAILS");
		if (resAuth != null) {
			headers.add("Authorization", "Bearer " + resAuth.getResponse());
			headers.add("X-Forwarded-Host", env.getProperty("x-forward-host"));
		} else {
			resAuth.setErrorcode("E8000");
			String message = LoadConfig.getConfigValue("E8000");
			if (message != null) {
				resAuth.setMessage(message.split("-")[0]);
				resAuth.setAction(message.split("-")[1]);
			}
			resAuth.setCode(Response.Status.BAD_REQUEST.getStatusCode());
			resAuth.setResponse(null);
			return resAuth;
		}
		HttpEntity<String> entity = new HttpEntity<>(record, headers);
		ResponseEntity<ResponseConfig> response = restTemplate.exchange(uri, HttpMethod.POST, entity,
				ResponseConfig.class);
		ResponseConfig res = response.getBody();
		return res;
	}

	public List<HealthRecord> getPatientHospitalMap(String record)
			throws JsonMappingException, JsonProcessingException {

		JsonNode rnode = null;
		rnode = objMapper.readValue(record, JsonNode.class);
		String patientID = rnode.get("patientId").asText();
		List<HealthRecord> getHealthList = serv.getHealth(patientID);
		return getHealthList;
	}

	public List<Composition> getCompositionList(String record) throws JsonMappingException, JsonProcessingException {
		JsonNode rnode = null;
		rnode = objMapper.readValue(record, JsonNode.class);
		String patientID = rnode.get("patientId").asText();
		String vid = rnode.get("virtualfolderId").asText();

		List<HealthRecord> healthList = serv.getHealth(patientID);

		if (healthList != null && healthList.size() > 0) {
			for (int i = 0; i < healthList.size(); i++) {
				if (healthList.get(i).getEpisodeId().equalsIgnoreCase(vid)) {
					HealthRecord h = healthList.get(i);

					return compServ.getComposition(h);

				}
			}
		}

		return null;
	}

	public ResponseConfig viewHealthRecord(String record) {

		ResponseConfig responses = new ResponseConfig();

		JsonNode rnode = null;
		try {
			rnode = objMapper.readValue(record, JsonNode.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		List<Composition> compList = new ArrayList<Composition>();
		List<Composition> addComp = new ArrayList<Composition>();
		final JsonNode compositions = rnode.get("compositions");
		if (compositions.isArray()) {
			for (final JsonNode objNode : compositions) {
				try {
					compList.add(objMapper.treeToValue(objNode, Composition.class));
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

		if (compList.size() > 0) {
			for (int i = 0; i < compList.size(); i++) {
				Composition c = compList.get(i);

				Composition comp = compServ.getCompositionById(c.getCompositionId());

				if (comp != null && comp.getCompositionId() != null) {
					c.setTemplateId(comp.getTemplateId());

					addComp.add(c);
				}

			}

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

		if (addComp.size() > 0) {
			JSONObject hresObj = new JSONObject();
			hresObj.put("compositions", addComp);
			hresObj.put("episodeId", rnode.get("episodeId").asText());
			hresObj.put("resourceType", "name");

			JSONObject hiuobject = new JSONObject();
			hiuobject.put("individualRole", "Psychiatrist");

			JSONObject object = new JSONObject();
			object.put("hresource", hresObj);
			object.put("hiu", hiuobject);
			object.put("source", "PatientApp");
			object.put("operation", "VIEW");
			object.put("patientId", rnode.get("patientId").asText());
			object.put("api", rnode.get("api"));
			object.put("version", rnode.get("version"));

			HealthRecord h = healthDao.getHealth(rnode.get("episodeId").asText());

			if (h != null) {

				object.put("consentId", h.getConsentId());
			}

			object.put("userId", "");
			object.put("role", "Physiatrist");
			object.put("timestamp", new Date().toString());
			List<String> strPurpose = new ArrayList<String>();
			strPurpose.add("OPBMR");
			strPurpose.add("IPBMR");
			strPurpose.add("THERAPY");
			strPurpose.add("ASMENT");
			strPurpose.add("RESTRAINT");
			object.put("purpose", strPurpose);

			HttpHeaders headers = new HttpHeaders();
			ResponseConfig resAuth = auth.getJwtAuthentication();
			String uri = LoadConfig.getConfigValue("PVIEWHEALTHRECORD");
			if (resAuth != null) {
				headers.add("Authorization", "Bearer " + resAuth.getResponse());
				headers.add("X-Forwarded-Host", env.getProperty("x-forward-host"));
				HttpEntity<String> entity = new HttpEntity<>(object.toString(), headers);
				ResponseEntity<ResponseConfig> response = restTemplate.exchange(uri, HttpMethod.POST, entity,
						ResponseConfig.class);
				ResponseConfig res = response.getBody();

				return res;
			} else {
				resAuth.setErrorcode("E8000");
				String message = LoadConfig.getConfigValue("E8000");
				if (message != null) {
					resAuth.setMessage(message.split("-")[0]);
					resAuth.setAction(message.split("-")[1]);
				}
				resAuth.setCode(Response.Status.BAD_REQUEST.getStatusCode());
				resAuth.setResponse(null);
				return resAuth;
			}
		}
		return responses;
	}

	public Boolean unlinkPatient(String reqData) {
		JsonNode reqNode = null;

		try {
			reqNode = objMapper.readValue(reqData, JsonNode.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String orgUUID = reqNode.get("orgUUID").asText();
		String patientId = reqNode.get("patientId").asText();
		List<HealthRecord> healthList = serv.getHealth(patientId);

		if (healthList != null && healthList.size() > 0) {
			for (int i = 0; i < healthList.size(); i++) {
				if (healthList.get(i).getIsLinked() && healthList.get(i).getOrgUUID().equalsIgnoreCase(orgUUID)) {

					healthList.get(i).setIsLinked(false);
					serv.saveHealth(healthList.get(i));
					return true;

				}
			}
		}

		return false;
	}

	public ResponseConfig getMheRegId(String orgUUID) {
		ResponseConfig res = new ResponseConfig();
		HttpHeaders headers = new HttpHeaders();

		ResponseConfig resAuth = auth.getJwtAuthentication();
		String uri = LoadConfig.getConfigValue("PGETMHEREGID") + orgUUID;
		if (resAuth != null && resAuth.getMessage().equalsIgnoreCase("Successful")) {
			headers.add("Authorization", "Bearer " + resAuth.getResponse());
			headers.add("X-Forwarded-Host", env.getProperty("x-forward-host"));

			ResponseEntity<ResponseConfig> response = restTemplate.exchange(uri, HttpMethod.GET, null,
					ResponseConfig.class);
			res = response.getBody();
			return res;

		}
		return res;

	}
}
