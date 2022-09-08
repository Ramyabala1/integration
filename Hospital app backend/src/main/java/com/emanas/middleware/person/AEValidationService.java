package com.emanas.middleware.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.utility.LoadConfig;
import com.emanas.middleware.utility.security.SecurityService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service("AUTHORIZEDENTITY")
@Qualifier("AUTHORIZEDENTITY")
public class AEValidationService implements PersonValidationService {
	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ObjectMapper objStringMapper;

	@Autowired
	SecurityService secServ;

	@Override
	public String fetchPersonRecord(String aeId) {

		if (aeId != null) {

			HttpHeaders headers = secServ.checkSecurity();
			HttpEntity<String> entity = new HttpEntity<String>(null, headers);

			String authDetails = restTemplate.postForObject(LoadConfig.getConfigValue("GETAE") + aeId, entity,
					String.class);

			return authDetails;
		}

		return null;
	}

	@Override
	public String personRecordExists() {
		// TODO Auto-generated method stub
		return null;
	}

}
