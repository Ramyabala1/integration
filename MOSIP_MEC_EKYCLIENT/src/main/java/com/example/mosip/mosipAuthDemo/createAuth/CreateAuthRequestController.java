package com.example.mosip.mosipAuthDemo.createAuth;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.mosip.mosipAuthDemo.createOtpEKycRequest.EkycRequestPayload;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
public class CreateAuthRequestController {


	@Autowired
	private Environment env;
	
	 @Autowired
	   RestTemplate restTemplate;
	
	@CrossOrigin(origins = "*")
	   @RequestMapping(value = "/mosip/createOTPAuthRequest" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public List<String> createOTPAuthRequest(@RequestParam(value="mosipID") String mosipID , @RequestParam(value="otp") String otp ) throws Exception {
		   
		//String vin = "6471974360";
		String vin = mosipID;
		
		String url = "http://localhost:8085/v1/identity/createAuthRequest?id=" + vin ;
		 
		 EkycRequestPayload authPayload = new EkycRequestPayload();
		 authPayload.setOtp(otp);
		 authPayload.setTransactionID("1234567890");
		 
		
		 SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX");
		 formatter.setTimeZone(TimeZone.getTimeZone("UTC"));
		 
		 		 
		 authPayload.setTimestamp(formatter.format(new Date()));
		 
		 ObjectMapper mapper = new ObjectMapper();
		 String strAuthPayload = mapper.writeValueAsString(authPayload);
		 HttpHeaders headers = new HttpHeaders();
	     
	     headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
	       
	     HttpEntity<String> entity = new HttpEntity<String>(strAuthPayload,headers);

	     
	     HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	     
	   //code for jar
		 
//		 Map<String, Object> requestMap = new HashMap<String, Object>();
//		  requestMap.put(strAuthPayload, requestMap)  ;
//		 requestMap.put("TIMESTAMP", formatter.format(new Date()));
//		 
//		 HttpEntity<String> response = new AuthRequestController().createAuthRequest(vin,null,false,false,null,null,null,false,requestMap);
//		 
	     
	     HttpHeaders authHeaders = response.getHeaders();
	     String signature = authHeaders.getFirst("signature");
	     String res = response.getBody();
	     
	     List<String> list = new ArrayList<String>();
	     
	     list.add(res);
	     list.add(signature);
	     
		
		return list;
		 
	   

	}	
	



	
	
}
