package com.emanas.middleware.policy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.config.ConfigurationUrl;
import com.emanas.middleware.utility.LoadConfig;
import com.emanas.middleware.utility.security.SecurityService;

@Service
public class PolicySet {
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ConfigurationUrl config;

	@Autowired
	SecurityService secServ;

	public String findPolicy(String activity, String token) {

		HttpHeaders headers = secServ.checkSecurity();
		HttpEntity<String> entity = new HttpEntity<String>(activity, headers);

		String url = LoadConfig.getConfigValue("GETPOLICY");
		ResponseEntity<String> response = restTemplate.postForEntity(url, entity, String.class);

		return response.getBody();
	}

}
