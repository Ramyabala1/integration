package com.app.patient.auth;

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

import com.app.patient.config.LoadConfig;
import com.app.patient.model.ResponseConfig;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class AuthenticationService {
	
	Logger logger = LoggerFactory.getLogger(AuthenticationService.class);
	
	  
	 
	  @Autowired
	  private Environment env;
	
		public ResponseConfig getJwtAuthentication() {
			ObjectMapper mapper = new ObjectMapper();
			RestTemplate restTemplate = new RestTemplate();
			HttpHeaders headers = new HttpHeaders();

			String uri = LoadConfig.getConfigValue("PAUTHENTICATE");

			JSONObject request = new JSONObject();
			request.put("hiu_password", env.getProperty("emanas.password"));
			request.put("hiu_username", env.getProperty("emanas.username"));

			headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
			HttpEntity<String> entity = new HttpEntity<>(request.toString(), headers);
			ResponseEntity<ResponseConfig> response = restTemplate.exchange(uri, HttpMethod.POST, entity,
					ResponseConfig.class);
			ResponseConfig res = response.getBody();
			if (res.getMessage().equalsIgnoreCase("Successful")) {
				return res;
			} else
				return res;

		}

}
