package com.emanas.middleware.patient.ehr;

import java.io.IOException;

import javax.xml.bind.PropertyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.Demog.DemogService;
import com.emanas.middleware.Demog.Factory;
import com.emanas.middleware.config.ConfigurationUrl;
import com.emanas.middleware.models.HiuUser;
import com.emanas.middleware.models.ResponseConfig;
import com.emanas.middleware.utility.LoadConfig;
import com.emanas.middleware.utility.cache.redis.Cache;
import com.emanas.middleware.utility.security.SecurityService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@Service
public class EhrService {

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
	SecurityService secServ;

	public ResponseConfig createPatient(@RequestBody String request) throws IOException, PropertyException {

		HiuUser hiu = secServ.getHIU();
		ObjectMapper mapper = new ObjectMapper();
		ResponseConfig resPatient = new ResponseConfig();
		JsonNode reqst = mapper.readValue(request, JsonNode.class);
		JsonNode node = reqst.get("demographics");
		((ObjectNode) node).put("userUuid", hiu.getUuid());
		((ObjectNode) node).put("userToken", hiu.getMheSessionToken());
		((ObjectNode) node).put("orgUuid", hiu.getOrguuid());

		if (node.has("emergencyContact")) {
			JsonNode emergencyContact = node.get("emergencyContact");
			if (emergencyContact.has("contactName")
					&& emergencyContact.get("contactName").asText().equalsIgnoreCase("")) {
				resPatient.setCode(441);
			}
		}
		String req = mapper.writeValueAsString(node);

		HttpHeaders headers = secServ.checkSecurity();



		HttpEntity<String> entity = new HttpEntity<String>(req, headers);
		resPatient = restTemplate.postForObject(LoadConfig.getConfigValue("CREATEPATIENT"), entity,
				ResponseConfig.class);

		return resPatient;

	}

}
