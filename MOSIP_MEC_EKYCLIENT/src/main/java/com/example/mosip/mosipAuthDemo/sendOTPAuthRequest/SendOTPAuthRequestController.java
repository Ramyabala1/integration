package com.example.mosip.mosipAuthDemo.sendOTPAuthRequest;

import java.io.IOException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.UnrecoverableKeyException;
import java.security.cert.CertificateException;
import java.util.HashMap;
import java.util.List;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.ws.rs.core.Response;
import javax.xml.bind.PropertyException;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.mosip.mosipAuthDemo.authentication.AuthController;
import com.example.mosip.mosipAuthDemo.createAuth.CreateAuthRequestController;
import com.example.mosip.mosipAuthDemo.createOtpEKycRequest.CreateOtpEkycRequestController;
import com.example.mosip.mosipAuthDemo.createOtpEKycRequest.CreateOtpEkycRequestService;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
@RestController
public class SendOTPAuthRequestController {
	
	
	@Autowired
	private Environment env;
	
	 @Autowired
	   RestTemplate restTemplate;
	 
	 @Autowired
	 CreateAuthRequestController createOtpAuth;
	 
	 @Autowired 
	 AuthController auth;
	 
	 @Autowired
	 CreateOtpEkycRequestService ekycServ;
	
	 
	
	@CrossOrigin(origins = "*")
	   @RequestMapping(value = "/mosip/sendOTPAuthRequest" , method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	   public Response sendOTPAuthRequestController(@RequestBody String jsonReq) throws Exception {
		   
		String token = auth.createAuthToken();
		
		ObjectMapper obj = new ObjectMapper();
		JsonNode jsonObj = obj.readTree(jsonReq);
		String mosipid = jsonObj.get("mosipID").asText();
		String otp = jsonObj.get("otp").asText();
		String trId = jsonObj.get("transactionID").asText();
		
//		List<String> createOtpList = createOtpAuth.createOTPAuthRequest(mosipid,otp);
//		
//		String reqPayload = null;
//		 String signature = null;
//		
//		if(createOtpList!=null && createOtpList.size()>0)
//		{
//			 reqPayload = createOtpList.get(0);
//			  signature = createOtpList.get(1);
//		}else
//		{  return Response.status(Response.Status.BAD_REQUEST).entity("create OTP eKyc Request failed").build();
//			 
//		}
		
		
		HashMap<String, String> req = ekycServ.createOTPekycRequest(trId,mosipid, env.getProperty("partnerId"), otp,false);
		System.out.println(req.get("reqStr"));
		System.out.println(req.get("signature"));
		
		
	   String url = env.getProperty("urlBase") +"idauthentication/v1/auth/" +env.getProperty("mispLicenceKey") + "/" +env.getProperty("partnerId")  + "/" + env.getProperty("partnerAPIKey");
	   
	   
		 HttpHeaders headers = new HttpHeaders();
	     
	     headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);

	     headers.set("signature", req.get("signature"));
	     headers.set("Authorization", token);
	     HttpEntity<String> entity = new HttpEntity<String>(req.get("reqStr"),headers);
	     ObjectMapper mapper = new ObjectMapper();
	     Response objResponse = null;
	     HttpEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);
	     String res = response.getBody();
	    
	     
	     System.out.println("Executed Now sendOTPAuthRequest: "+ System.currentTimeMillis());
	     System.out.println(res);
	     
	     objResponse= Response.status(Response.Status.FOUND).entity(res).build();
	     
	    
//	     JsonNode actualObj = mapper.readTree(res);
//	     Decryptkyv dec = new Decryptkyv();
//	     String decRes = null;
//	     JsonNode resObj = actualObj.get("response");
//	    if(resObj.get("kycStatus").asBoolean()) {
//	     String identity = resObj.get("identity").asText();
//	     decRes= dec.decrypt(identity);
//	    }
//	     
//	    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
//	   
//	   Identity objFromJson = mapper.readValue(decRes,Identity.class);
//       System.out.println(objFromJson); 
//       if(decRes!=null) {
//       objResponse = Response.status(Response.Status.FOUND).entity(objFromJson).build();
//       }
//       else
//       {
//    	   objResponse = Response.status(Response.Status.BAD_REQUEST).entity("OTP does not match").build();
//    	      
//       }
		
		
       return objResponse;
	
	

}
	
}
