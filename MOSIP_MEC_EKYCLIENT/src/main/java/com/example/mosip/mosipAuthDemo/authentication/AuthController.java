package com.example.mosip.mosipAuthDemo.authentication;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.xml.bind.PropertyException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class AuthController {

	@Autowired
	private Environment env;
	
	 @Autowired
	   RestTemplate restTemplate;
	
//	@CrossOrigin(origins = "*")
//	   @RequestMapping(value = "/mosip/authenticate" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public String createAuthToken1() throws IOException, PropertyException {
		   
		
		
		 //String vin = "8950483749";
		
		 
		 String url =  env.getProperty("urlBase") + "v1/authmanager/authenticate/useridPwd";
		 
		 
		 URL obj = new URL(url);
		  HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		
		 AuthRequest authReq = new AuthRequest();
		 authReq.setAppId(env.getProperty("appId"));
		 authReq.setUserName(env.getProperty("userId"));
		 authReq.setPassword(env.getProperty("password"));
		 
		 AuthPayload authPayload = new AuthPayload();
		 authPayload.setRequest(authReq);
		 authPayload.setVersion("1.0");
		 
		 authPayload.setMetadata("{}");
		 authPayload.setId("mosip.authentication.useridPwd");
		
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		 formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		 
		 		 
		 authPayload.setRequesttime(formatter.format(new Date()));
		 
		 ObjectMapper mapper = new ObjectMapper();
		 String strAuthPayload = mapper.writeValueAsString(authPayload);
		 HttpHeaders headers = new HttpHeaders();
	     
	     headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
	       
	     HttpEntity<String> entity = new HttpEntity<String>(strAuthPayload,headers);
//	     String res  ; 
//	     res =  restTemplate.postForObject(
//	    		  url,  entity, String.class);
	     
	     HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	     HttpHeaders authHeaders = response.getHeaders();
	     
	     String res = response.getBody();
	     JsonNode resp = mapper.readValue(response.getBody(), JsonNode.class);
	     String statusMgs = resp.get("response").get("status").asText();
	     if(statusMgs.equalsIgnoreCase("success"))
	     {
	    	 return authHeaders.getFirst(authHeaders.AUTHORIZATION);
	     }else
	     {
	    	 return  null;
	     }
	     
	}
	     @CrossOrigin(origins = "*")
		   @RequestMapping(value = "/mosip/authenticate" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE) 
	     public String createAuthToken() throws IOException, PropertyException {
			   
	 		
	 		
			 //String vin = "8950483749";
			
			 
			 String url =  env.getProperty("urlBase") + "v1/authmanager/authenticate/clientidsecretkey";
			 	
			 
			 URL obj = new URL(url);
			  HttpURLConnection con = (HttpURLConnection) obj.openConnection();
			
			 AuthRequest authReq = new AuthRequest();
			 authReq.setAppId(env.getProperty("appId"));
			 authReq.setClientId(env.getProperty("clientId"));
			 authReq.setSecretKey(env.getProperty("secretKey"));
			 
			 AuthPayload authPayload = new AuthPayload();
			 authPayload.setRequest(authReq);
			 authPayload.setVersion("1.0");
			 
			 authPayload.setMetadata("{}");
			 authPayload.setId("mosip.authentication.useridPwd");
			
			 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
			 formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
			 
			 		 
			 authPayload.setRequesttime(formatter.format(new Date()));
			 
			 ObjectMapper mapper = new ObjectMapper();
			 String strAuthPayload = mapper.writeValueAsString(authPayload);
			 HttpHeaders headers = new HttpHeaders();
		     
		     headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
		       
		     HttpEntity<String> entity = new HttpEntity<String>(strAuthPayload,headers);
//		     String res  ; 
//		     res =  restTemplate.postForObject(
//		    		  url,  entity, String.class);
		     
		     HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
		     HttpHeaders authHeaders = response.getHeaders();
		     
		     String res = response.getBody();
		     JsonNode resp = mapper.readValue(response.getBody(), JsonNode.class);
		     String statusMgs = resp.get("response").get("status").asText();
		     if(statusMgs.equalsIgnoreCase("success"))
		     {
		    	 return authHeaders.getFirst(authHeaders.AUTHORIZATION);
		     }else
		     {
		    	 return  null;
		     }
			    
		 
		 
		    
			 
			
		 
	
}
	
}
