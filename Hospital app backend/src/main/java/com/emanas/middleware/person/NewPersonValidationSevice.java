package com.emanas.middleware.person;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.models.HiuUser;
import com.emanas.middleware.utility.LoadConfig;
import com.emanas.middleware.utility.security.SecurityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
@Qualifier("NEWPERSON")
public class NewPersonValidationSevice implements PersonValidationService {

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	ObjectMapper objStringMapper;

	@Autowired
	SecurityService secServ;

	@Override
	public String fetchPersonRecord(String aeID) {

		if (aeID != null) {
			HttpHeaders headers = secServ.checkSecurity();
			HiuUser hiu = secServ.getHIU();

			String hiuStr = null;
			try {
				hiuStr = objStringMapper.writeValueAsString(hiu);
			} catch (JsonProcessingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			HttpEntity<String> entity = new HttpEntity<String>(hiuStr, headers);

			String patientDetails = restTemplate
					.postForObject(LoadConfig.getConfigValue("GETDETAILS_FORPATIENT") + aeID, entity, String.class);

			return patientDetails;
		}

		return null;
	}

	@Override
	public String personRecordExists() {

		return null;
	}

}
