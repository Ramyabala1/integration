package com.app.patient.log;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.app.patient.auth.AuthenticationService;
import com.app.patient.config.LoadConfig;
import com.app.patient.model.ResponseConfig;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PatientLogService {

	@Autowired
	private Environment env;

	@Autowired
	PatientLogRepo repo;

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	AuthenticationService auth;

	@Autowired
	ObjectMapper objMapper;

	public Boolean saveLog(PatientLog patLog) {
		PatientLog patLog1 = repo.save(patLog);
		if (patLog1 != null) {
			return true;
		}
		return false;
	}

	public ResponseConfig getAccessLogs(String patientId) {

		HttpHeaders headers = new HttpHeaders();
		JsonNode reqNode = null;

		if (patientId != null) {
			ResponseConfig resAuth = auth.getJwtAuthentication();
			String uri = LoadConfig.getConfigValue("PACCESSLOGS") + patientId;

			headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
			if (resAuth != null) {
				headers.add("Authorization", "Bearer " + resAuth.getResponse());
				headers.add("X-Forwarded-Host", env.getProperty("x-forward-host"));
			}

			HttpEntity<String> entity = new HttpEntity<String>(null, headers);

			ResponseEntity<ResponseConfig> response = restTemplate.exchange(uri, HttpMethod.POST, entity,
					ResponseConfig.class);
			ResponseConfig res = response.getBody();

			return res;
		}
		return null;
	}

}
