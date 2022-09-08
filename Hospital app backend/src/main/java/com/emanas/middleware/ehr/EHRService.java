package com.emanas.middleware.ehr;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;

import javax.xml.bind.PropertyException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.Demog.DemogService;
import com.emanas.middleware.Demog.Factory;
import com.emanas.middleware.config.ConfigurationUrl;
import com.emanas.middleware.log.EhrAccessLog;
import com.emanas.middleware.log.EhrAccessLogRepo;
import com.emanas.middleware.models.GetOrgDetails;
import com.emanas.middleware.models.HiuUser;
import com.emanas.middleware.models.Payload;
import com.emanas.middleware.models.ResponseConfig;
import com.emanas.middleware.policy.PolicyEnforcementService;
import com.emanas.middleware.utility.LoadConfig;
import com.emanas.middleware.utility.cache.redis.Cache;
import com.emanas.middleware.utility.security.SecurityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class EHRService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ConfigurationUrl config;

	@Autowired
	DemogService demogServ;

	@Autowired
	Cache cacheServ;

	@Autowired
	Factory authRegistry;

	@Autowired
	PolicyEnforcementService policyEnfServ;

	@Autowired
	CreateEhrService createEhrServ;

	@Autowired
	EhrAccessLogRepo ehrLogRepo;

	@Autowired
	SecurityService secServ;

	public String viewTreatments(String request) throws IOException, PropertyException, ParseException {

		HiuUser hiu = secServ.getHIU();
		ObjectMapper objStringMapper = new ObjectMapper();
		String error = null;
		List<String> recordType = new ArrayList<String>();
		JsonNode node = objStringMapper.readValue(request, JsonNode.class);
		String patientId = node.get("patientId").asText();

		String role = node.get("hiu").get("individualRole").asText();
		String operation = node.get("operation").asText();
		List<String> listCompositions = new ArrayList<String>();

		JsonNode hitypesRequest = node.get("purpose");

		HashMap<String, String> validateAccessRequest = policyEnfServ.validateAccessRequest(request,
				hiu.getMheAccessToken(), true);

		if (validateAccessRequest.containsKey("error")) {
			return validateAccessRequest.get("error");
		}

		if (validateAccessRequest.get("result").equalsIgnoreCase("Valid"))

		{

			JsonNode requestObj = objStringMapper.createObjectNode();
			ObjectNode objectNode = (ObjectNode) requestObj;

			EhrAccessLog ehrLog = new EhrAccessLog();
			ehrLog.setActivity("VIEW");
			ehrLog.setSource(node.get("source").asText());
			if (validateAccessRequest.containsKey("result")) {
				ehrLog.setHealthProfessionalName(validateAccessRequest.get("individualName"));
			}

			ehrLog.setPatientID(patientId);
			ehrLog.setCreatedDate(new Date());
			ehrLog.setHiu(hiu.getUserName());
			ehrLog.setMhpRole(role);
			if (validateAccessRequest.containsKey("from") && validateAccessRequest.containsKey("to")) {

				objectNode.put("from", validateAccessRequest.get("from"));
				objectNode.put("to", validateAccessRequest.get("to"));
				objectNode.put("patientID", patientId);
				objectNode.put("Orguuid", hiu.getOrguuid());
				objectNode.put("mheSession", hiu.getMheSessionToken());

				if (validateAccessRequest.containsKey("hip")) {

					objectNode.put("hip", validateAccessRequest.get("hip"));
					ehrLog.setHip(objectNode.get("hip").asText());

				}

				if (validateAccessRequest.containsKey("recordType")) {

					objectNode.put("recordType", validateAccessRequest.get("recordType"));

				}

				HttpHeaders headers = secServ.checkSecurity();

				HttpEntity<String> entity = new HttpEntity<String>(requestObj.toString(), headers);

				new ParameterizedTypeReference<List<HashMap<String, String>>>() {
				};
				String url = LoadConfig.getConfigValue("GETCOMPOSITION");
				ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
				String resp = null;

				if (response.getStatusCode() == HttpStatus.OK) {

					if (response.getBody() != null) {
						resp = response.getBody();
						if (resp != null && resp.length() != 5 && !resp.substring(0, 1).contains("E")) {
							resp = null;
						}
					}

					if (resp == null) {
						List<HashMap<String, String>> listHash = objStringMapper.readValue(response.getBody(),
								new TypeReference<List<HashMap<String, String>>>() {
								});

						if (listHash.size() > 0) {
							for (final JsonNode reqType : hitypesRequest) {

								recordType.add(reqType.asText());

							}
						}

						for (int i = 0; i < listHash.size(); i++) {

							HashMap<String, String> outRes = listHash.get(i);
							String templateName = outRes.get("Template");
							if (templateName.equalsIgnoreCase("MHMS - ip_bmr.v1") && recordType.contains("IPBMR")) {
								String output = outRes.get("Output");

								String outputjson = "{\"output\":" + output + ",\"template\":\"" + templateName
										+ "\",\"hospitalName\":\"" + hiu.getMheOrgname() + "\"}";

								listCompositions.add(outputjson);

							}
							if (templateName.equalsIgnoreCase("MHMS - discharge.v1") && recordType.contains("IPBMR")) {

								String output = outRes.get("Output");

								String outputjson = "{\"output\":" + output + ",\"template\":\"" + templateName
										+ "\",\"hospitalName\":\"" + hiu.getMheOrgname() + "\"}";

								listCompositions.add(outputjson);

							}
							if ((templateName.equalsIgnoreCase("MHMS - op_bmr.v2")
									|| templateName.equalsIgnoreCase("MHMS - op_bmr.v1"))
									&& recordType.contains("OPBMR")) {

								String output = outRes.get("Output");

								String outputjson = "{\"output\":" + output + ",\"template\":\"" + templateName
										+ "\",\"hospitalName\":\"" + hiu.getMheOrgname() + "\"}";

								listCompositions.add(outputjson);
							}
							if ((templateName.equalsIgnoreCase("MHMS - Restraint monitoring.v0")
									|| templateName.equalsIgnoreCase("MHMS - Restraint monitoring.v1"))
									&& recordType.contains("RESTRAINT")) {
								String output = outRes.get("Output");

								String outputjson = "{\"output\":" + output + ",\"template\":\"" + templateName
										+ "\",\"hospitalName\":\"" + hiu.getMheOrgname() + "\"}";

								listCompositions.add(outputjson);

							}
							if (templateName.equalsIgnoreCase("MHMS - Therapy reporting.v0")
									&& recordType.contains("THERAPY")) {
								String output = outRes.get("Output");

								String outputjson = "{\"output\":" + output + ",\"template\":\"" + templateName
										+ "\",\"hospitalName\":\"" + hiu.getMheOrgname() + "\"}";

								listCompositions.add(outputjson);

							}
							if (templateName.equalsIgnoreCase("MHMS - Psychological assessment.v0")
									&& recordType.contains("ASMENT")) {
								String output = outRes.get("Output");

								String outputjson = "{\"output\":" + output + ",\"template\":\"" + templateName
										+ "\",\"hospitalName\":\"" + hiu.getMheOrgname() + "\"}";

								listCompositions.add(outputjson);

							}

						}
					}
				}
				if (listCompositions.size() > 0) {

					ehrLog.setStatusCode(String.valueOf(response.getStatusCode()));

					ehrLogRepo.save(ehrLog);

					return listCompositions.toString();
				} else if (resp != null && resp.length() == 5 && resp.substring(0, 1).contains("E")) {

					return resp;
				}

				else {
					error = "E1025";
				}

				if (response.getStatusCode() != HttpStatus.OK) {
					error = "E1000";
				}
				ehrLog.setStatusCode(String.valueOf(response.getStatusCode()));

				ehrLogRepo.save(ehrLogRepo);

			} else if (validateAccessRequest.containsKey("episodeId")
					&& validateAccessRequest.containsKey("compositions")) {

				objectNode.put("episodeId", validateAccessRequest.get("episodeId"));
				objectNode.put("compositions", validateAccessRequest.get("compositions"));
				objectNode.put("patientId", patientId);
				objectNode.put("Orguuid", hiu.getOrguuid());
				objectNode.put("mheSession", hiu.getMheSessionToken());
				return getCompDetails(objectNode, ehrLog, validateAccessRequest);

			}

		}

		return error;
	}

	private String getCompDetails(ObjectNode objectNode, EhrAccessLog ehrLog, HashMap<String, String> map) {

		ObjectMapper objStringMapper = new ObjectMapper();
		String error = null;

		JSONArray jsonArr = new JSONArray(map.get("compositions"));

		List<String> listCompositions = new ArrayList<String>();

		Payload payload = new Payload();
		payload.setPatientId(objectNode.get("patientId").asText());
		payload.setMheSession(objectNode.get("mheSession").asText());

		for (int i = 0; i < jsonArr.length(); i++) {

			JSONObject josnObj = (JSONObject) jsonArr.get(i);

			HttpHeaders headers = secServ.checkSecurity();

			payload.setCompositionId(josnObj.get("compositionId").toString());
			payload.setTemplateId(josnObj.get("templateId").toString());

			new ParameterizedTypeReference<List<HashMap<String, String>>>() {
			};

			HttpEntity<String> entity = null;
			try {
				entity = new HttpEntity<String>(objStringMapper.writeValueAsString(payload), headers);
			} catch (JsonProcessingException e1) {

				e1.printStackTrace();
			}

			String url = LoadConfig.getConfigValue("GETCOMPOSITIONDETAILS");
			ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

			if (response.getStatusCode() == HttpStatus.OK) {
				if (response.getBody() == null) {
					return "E1025";
				}
				List<HashMap<String, String>> listHash = null;
				try {
					listHash = objStringMapper.readValue(response.getBody(),
							new TypeReference<List<HashMap<String, String>>>() {
							});
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (listHash.size() > 0) {

					HashMap<String, String> outRes = listHash.get(0);

					String templateName = outRes.get("Template");
					String output = outRes.get("Output");
					String outputjson = "{\"output\":" + output + ",\"template\":\"" + templateName + "\"}";
					listCompositions.add(outputjson);

				}
				ehrLog.setStatusCode(String.valueOf(response.getStatusCode()));
				ehrLogRepo.save(ehrLog);

				if (listCompositions.size() > 0) {

					return listCompositions.toString();
				}

			}

			if (response.getStatusCode() != HttpStatus.OK) {
				error = "E1000";
			}

		}
		return error;

	}

	public String searchOrgName(String orgName) throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		HttpHeaders headers = secServ.checkSecurity();
		HttpEntity<String> entity = new HttpEntity<String>(null, headers);
		String url = LoadConfig.getConfigValue("GETORGNAME") + orgName;
		ResponseEntity<GetOrgDetails[]> response = restTemplate.postForEntity(url, entity, GetOrgDetails[].class);
		if (response.getStatusCode() == HttpStatus.OK) {
			if (response.getBody() == null) {
				return "E8002";
			}

			GetOrgDetails[] getOrg = response.getBody();

			if (getOrg.length > 0) {
				if (getOrg[0].getUuid() != null) {
					getOrg[0].setMheRegId(getMheRegId(getOrg[0].getUuid()));
				}
			}

			if (getOrg.length > 0) {
				return mapper.writeValueAsString(getOrg);
			}
		}

		return "E8002";

	}

	public String getEHRContextByHIP(String eHRContext) throws JsonMappingException, JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		HttpHeaders headers = secServ.checkSecurity();
		HttpEntity<String> entity = new HttpEntity<String>(eHRContext, headers);

		String url = LoadConfig.getConfigValue("GETENCOUNTERBYHIP");
		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			if (response.getBody() == null) {
				return "E1025";
			}

			return response.getBody();
		}

		return "E1025";

	}

	public Boolean getViewPolicy(String request) {

		HiuUser hiu = secServ.getHIU();
		ObjectMapper objStringMapper = new ObjectMapper();
		String error = null;

		HashMap<String, String> validateAccessRequest = policyEnfServ.validateAccessRequest(request,
				hiu.getMheAccessToken(), false);
		if (validateAccessRequest.containsKey("result")
				&& validateAccessRequest.get("result").equalsIgnoreCase("Valid")) {
			return true;
		}
		return false;
	}

	public String getCompositionDetails(String request) throws IOException, PropertyException, ParseException {

		HiuUser hiu = secServ.getHIU();
		ObjectMapper objStringMapper = new ObjectMapper();
		String error = null;
		List<String> recordType = new ArrayList<String>();
		JsonNode node = objStringMapper.readValue(request, JsonNode.class);
		String patientId = node.get("patientId").asText();
		String consentID = node.get("consentId").asText();

		Payload payload = new ObjectMapper().readValue(request, Payload.class);
		payload.setMheSession(hiu.getMheSessionToken());

		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
		format.setTimeZone(TimeZone.getTimeZone("UTC"));
		List<String> purposeTypeList = new ArrayList<String>();

		ObjectMapper mapper = new ObjectMapper();
		ResponseEntity<String> getConsent = restTemplate
				.getForEntity(LoadConfig.getConfigValue("GETCONSENT") + consentID, String.class);
		if (getConsent != null) {
			JsonNode artifact = mapper.readValue(getConsent.getBody(), JsonNode.class);

			JsonNode consentData = artifact.get("consentParameters");
			JsonNode purposeConsent = consentData.get("purpose");
			String permission = null;

			permission = consentData.get("permission").asText();

			JsonNode validity = consentData.get("validity");
			String validityEnddate = validity.get("validityEndDate").asText();
			String patientConsent = null;
			if (artifact.has("patient")) {
				patientConsent = artifact.get("patient").get("patientID").asText();
			} else {
				patientConsent = artifact.get("person").get("personId").asText();
			}

			Date valid = format.parse(validityEnddate);

			Date today = new Date();

			Boolean matchPurpose = true;

			for (final JsonNode purposeType : purposeConsent) {
				purposeTypeList.add(purposeType.asText());
			}

			for (final String purpose : recordType) {
				if (!purposeTypeList.contains(purpose)) {
					matchPurpose = false;
				}
			}

			if (!matchPurpose) {
				return "E1012";
			}

			if (valid.compareTo(today) >= 0) {
				if (permission != null) {
					if (patientId.equalsIgnoreCase(patientConsent)) {

						HttpHeaders headers = secServ.checkSecurity();
//

						HttpEntity<String> entity = new HttpEntity<String>(mapper.writeValueAsString(payload), headers);

						new ParameterizedTypeReference<List<HashMap<String, String>>>() {
						};

						String url = LoadConfig.getConfigValue("GETCOMPOSITIONDETAILS");
						ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

						List<String> listCompositions = new ArrayList<String>();

						if (response.getStatusCode() == HttpStatus.OK) {
							if (response.getBody() == null) {
								return "E1025";
							}
							List<HashMap<String, String>> listHash = mapper.readValue(response.getBody(),
									new TypeReference<List<HashMap<String, String>>>() {
									});

							for (int i = 0; i < listHash.size(); i++) {

								HashMap<String, String> outRes = listHash.get(i);
								String templateName = outRes.get("Template");
								String output = outRes.get("Output");
								String outputjson = "{\"output\":" + output + ",\"template\":\"" + templateName + "\"}";

								listCompositions.add(outputjson);

							}

							if (listCompositions.size() > 0) {

								return listCompositions.toString();
							} else {
								error = "E1025";
							}

						}

						if (response.getStatusCode() != HttpStatus.OK) {
							error = "E1000";
						}

					} else {
						error = "E1000";
					}

				} else {
					error = "E1012";
				}
			} else {
				error = "E1014";
			}
		} else {
			error = "E1002";

		}
		return error;

	}

	public String createHealthRecord(String ehrData)
			throws JsonMappingException, JsonProcessingException, ParseException {

		HiuUser hiu = secServ.getHIU();
		ObjectMapper objStringMapper = new ObjectMapper();
		String error = null;
		List<String> recordType = new ArrayList<String>();
		JsonNode node = objStringMapper.readValue(ehrData, JsonNode.class);
		JsonNode hitypesRequest = node.get("purpose");
		for (final JsonNode reqType : hitypesRequest) {
			recordType.add(reqType.asText());
		}

		HashMap<String, String> validateAccessRequest = policyEnfServ.validateAccessRequest(ehrData,
				hiu.getMheAccessToken(), true);

		if (validateAccessRequest.containsKey("error")) {
			return validateAccessRequest.get("error");
		}

		if (validateAccessRequest.get("result").equalsIgnoreCase("Valid")) {

			if (recordType.contains("RESTRAINT")) {
				return createEhrServ.createRestraintService(ehrData, hiu);

			} else if (recordType.contains("ASMENT")) {
				return createEhrServ.createAssessmentService(ehrData, hiu);

			} else if (recordType.contains("IPBMR")) {

				return createEhrServ.createIPService(ehrData, hiu);
			} else if (recordType.contains("OPBMR")) {
				return createEhrServ.createOPBMRService(ehrData, hiu);
			} else if (recordType.contains("THERAPY")) {
				return createEhrServ.createTherapyService(ehrData, hiu);
			}
		}

		return error;

	}

	public String getHIUDetails() {

		HiuUser hiu = secServ.getHIU();

		String req = "{\"OrgUUID\":\"" + hiu.getOrguuid() + "\",\"UserUUID\":\"" + hiu.getUuid() + "\"}";

		HttpHeaders headers = secServ.checkSecurity();
		HttpEntity<String> entity = new HttpEntity<String>(req, headers);
		String url = LoadConfig.getConfigValue("GETMHEDETAILS");
		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			if (response.getBody() == null) {
				return "E1025";
			}

			return response.getBody();
		} else {
			return null;
		}

	}

	public void saveEhrAccessLog(ResponseConfig response, String ehrData, EhrAccessLog ehrLog) {
		ObjectMapper objStringMapper = new ObjectMapper();

		JsonNode node = null;
		try {
			node = objStringMapper.readValue(ehrData, JsonNode.class);
		} catch (JsonProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		JsonNode ehrDataNode = node.get("ehrData");
		ehrLog.setSource("Hospital App");
		ehrLog.setStatusCode(String.valueOf(response.getCode()));
		ehrLog.setActivity("CREATE");
		ehrLog.setPatientID(node.get("patientId").asText());
		if (ehrDataNode.has("mhpName")) {

			ehrLog.setHealthProfessionalName(ehrDataNode.get("mhpName").asText());
		}
		ehrLog.setMhpId(ehrDataNode.get("mhpId").asText());
		ehrLog.setHiu(ehrDataNode.get("orgId").asText());
		ehrLog.setMhpRole(node.get("hiu").get("individualRole").asText());

		ehrLogRepo.save(ehrLog);

	}

	public String getMheRegId(String orguuId) {

		HiuUser hiu = secServ.getHIU();

		String req = "{\"OrgUUID\":\"" + orguuId + "\"}";

		HttpHeaders headers = secServ.checkSecurity();

		HttpEntity<String> entity = new HttpEntity<String>(req, headers);

		String url = LoadConfig.getConfigValue("GETMHEREGDETAILS");
		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);
		if (response.getStatusCode() == HttpStatus.OK) {
			if (response.getBody() == null) {
				return null;
			}

			return response.getBody();
		} else {
			return null;
		}
	}

}
