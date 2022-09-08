package com.emanas.middleware.hiuLogin;

import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.emanas.middleware.config.ConfigurationUrl;
import com.emanas.middleware.models.AuthenticationRequest;
import com.emanas.middleware.models.HiuUser;
import com.emanas.middleware.models.ResponseConfig;
import com.emanas.middleware.utility.JwtUtil;
import com.emanas.middleware.utility.LoadConfig;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j

public class LoginController {

	Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	RestTemplate restTemplate;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	ConfigurationUrl config;

	@Bean
	private JwtUtil jwtTokenUtil() {
		return new JwtUtil();
	}

	@CrossOrigin(origins = "*", allowedHeaders = "*")
	@RequestMapping(value = "fhir/hiu/authenticate", method = RequestMethod.POST)
	public ResponseConfig createAuthenticationToken(@RequestBody AuthenticationRequest authenticationRequest) {

		String validUserUrl = LoadConfig.getConfigValue("LOGINSERVER") + authenticationRequest.getHiu_username() + "/"
				+ authenticationRequest.getHiu_password() + "/" + authenticationRequest.getHiu_ipaddress();
		String response = restTemplate.getForObject(validUserUrl, String.class);

		if (response.equalsIgnoreCase("Valid")) {
			HiuUser hiu = new HiuUser();
			hiu.setUserName(authenticationRequest.getHiu_username());
			hiu.setPassword(authenticationRequest.getHiu_password());
			final String jwt = jwtTokenUtil().generateToken(hiu);

			hiu.setHiuSessionToken(jwt);

			String sessionUpdateUrl = LoadConfig.getConfigValue("UPDATESESSION");

			// String sessionUpdateUrl =
			// "http://localhost:8082/MHMS_DEV/rest/fhir/updateSessionDetails";
			Boolean issessionUpdated = restTemplate.postForObject(sessionUpdateUrl, hiu, Boolean.class);

			if (issessionUpdated) {
				ResponseConfig responses = new ResponseConfig();
				responses.setCode(Response.Status.OK.getStatusCode());
				responses.setResponse(jwt.toString());
				responses.setMessage("Successful");
				return responses;
			} else {

				ResponseConfig responses = new ResponseConfig();
				responses.setCode(Response.Status.NOT_FOUND.getStatusCode());
				responses.setErrorcode("E1500");
				String message = LoadConfig.getConfigValue("E1500");
				if (message != null) {
					responses.setMessage(message.split("-")[0]);
					responses.setAction(message.split("-")[1]);
				}

				return responses;
			}
		} else {

			ResponseConfig responses = new ResponseConfig();
			responses.setCode(Response.Status.NOT_FOUND.getStatusCode());
			responses.setErrorcode("E0022");
			String message = LoadConfig.getConfigValue("E0022");
			if (message != null) {
				responses.setMessage(message.split("-")[0]);
				responses.setAction(message.split("-")[1]);
			}
			return responses;
		}
	}

}
